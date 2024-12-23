package uk.ac.tees.mad.jj.authentication.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import com.google.rpc.context.AttributeContext.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.jj.authentication.model.UserInfo
import uk.ac.tees.mad.jj.authentication.response.AuthState
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class AuthViewmodel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
):ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _currentUser = MutableStateFlow<UserInfo?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn(){
        val currUser = auth.currentUser
        _isLoggedIn.value = currUser!=null
    }

    fun LoginUser(email: String, password: String){
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _authState.value = AuthState.Success
                }
                .addOnFailureListener{
                    _authState.value = AuthState.Failure(it.message.toString())
                }
        }
    }

    fun RegisterUser(
        name: String,
        username: String,
        email: String,
        password: String
    ){
      viewModelScope.launch {
          _authState.value = AuthState.Loading
          auth.createUserWithEmailAndPassword(email, password)
              .addOnSuccessListener {
                  val currUser = auth.currentUser
                  if (currUser != null) {
                      val userId = currUser.uid

                      val newUser = UserInfo(
                          name = name,
                          username = username,
                          email = email,
                          profilePicture = "",
                      )

                      firestore.collection("users")
                          .document(userId)
                          .set(newUser)
                          .addOnSuccessListener {
                              _authState.value = AuthState.Success
                              Log.i("Register User: ", "User Registered Successfully")
                          }
                          .addOnFailureListener {
                              _authState.value = AuthState.Failure(it.message.toString())
                              Log.i("Register User: ", "User not registered in Firestore")
                          }
                  }
              }
              .addOnFailureListener{
                  Log.i("Register User: ", "User not registered in Firebase Auth.")
                  _authState.value = AuthState.Failure(it.message.toString())
              }
      }
    }

    fun logOut(){
        auth.signOut()
        _authState.value = AuthState.Idle
    }

    fun updateAuthState(){
        _authState.value = AuthState.Idle
    }

    fun fetchCurrentUser(){
        val currUser = auth.currentUser
        _authState.value = AuthState.Loading
        if (currUser!=null){
            Log.i("The current User name: ", currUser.displayName.toString())
            val userId = currUser.uid

            firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener {user->
                    if (user.exists()){
                        val userInfo = UserInfo(
                            user.getString("name") ?: "",
                            user.getString("username") ?: "",
                            user.getString("email") ?: "",
                            user.getString("profilePicture") ?: ""
                        )

                        _currentUser.value = userInfo
                        _authState.value = AuthState.Success
                    }else{
                        _authState.value = AuthState.Failure("The User is not found!")
                    }
                }
                .addOnFailureListener {
                    logOut()
                    _authState.value = AuthState.Failure(it.message.toString())
                    Log.i("The User:" , it.message.toString())
                }
        }
    }

    fun updateNameAndUsername(name: String, username: String){
        _authState.value = AuthState.Loading
        val currUser = auth.currentUser
        if (currUser != null){
            _authState.value = AuthState.Loading
            val userId = currUser.uid

            val updatedUser = hashMapOf(
                "name" to name,
                "username" to username
            )

            firestore.collection("users")
                .document(userId)
                .update(updatedUser as Map<String, Any>)
                .addOnSuccessListener {
                    fetchCurrentUser()
                    Log.i("User Update:", "The user is updated successfully")
                    _authState.value = AuthState.Success
                }
                .addOnFailureListener {
                    Log.i("User Update:", "The user is updated successfully")
                    _authState.value = AuthState.Failure(it.message.toString())
                }
        }
    }

    fun updateProfileImage(uri: Uri){
        val currentUser = auth.currentUser
        if (currentUser!=null){
            val userId = currentUser.uid
            val imageRef = storage.reference.child("users/${userId}/profile.jpg")

            imageRef.putFile(uri)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener {
                        val imageLink = it.toString()

                        val userData = hashMapOf(
                            "profilePicture" to imageLink)
                        firestore.collection("users")
                            .document(userId)
                            .update(userData as Map<String, Any>)
                            .addOnSuccessListener {
                                fetchCurrentUser()
                            }
                    }
                }
                .addOnFailureListener{
                    Log.i("Error Encountered: ", "Unable to update the profile picture.")
                }
        }else{
            Log.i("Error update:", "Current User is null")
        }
    }

}

