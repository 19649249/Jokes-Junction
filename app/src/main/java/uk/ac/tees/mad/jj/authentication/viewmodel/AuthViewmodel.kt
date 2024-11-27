package uk.ac.tees.mad.jj.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.jj.authentication.model.UserInfo
import uk.ac.tees.mad.jj.authentication.response.AuthState
import javax.inject.Inject


@HiltViewModel
class AuthViewmodel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
):ViewModel() {

    private var _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private var _isLoggedIn = MutableStateFlow<Boolean>(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn(){
        val currUser = auth.currentUser
        if (currUser!=null){
            _isLoggedIn.value = true
        }else{
            _isLoggedIn.value = false
        }
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
}