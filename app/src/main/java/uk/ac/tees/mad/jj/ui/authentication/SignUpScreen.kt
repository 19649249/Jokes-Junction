package uk.ac.tees.mad.jj.ui.authentication

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.jj.authentication.response.AuthState
import uk.ac.tees.mad.jj.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.jj.ui.theme.authFam
import uk.ac.tees.mad.jj.ui.theme.heading
import uk.ac.tees.mad.jj.ui.theme.poppinsFam


@Composable
fun SignUpScreen(
    authViewmodel: AuthViewmodel,
    navController: NavHostController
){
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisi by remember { mutableStateOf(false) }

    val authState by authViewmodel.authState.collectAsState()

    when(authState){
        is AuthState.Success->{
            navController.navigate("home_graph"){
                popUpTo(navController.graph.startDestinationId){
                    inclusive=true
                }
            }
        }
        is AuthState.Failure->{
            Toast.makeText(context, (authState as AuthState.Failure).message.toString(), Toast.LENGTH_LONG).show()
            email = ""
            password = ""
            authViewmodel.updateAuthState()
        }
        is AuthState.Loading->{

        }
        is AuthState.Idle->{

        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFAF64FF)
            )
    ){

        Column(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "Joke Junction",
                fontSize = 40.sp,
                fontFamily = heading,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Spacer(modifier = Modifier.weight(0.4f))

            Text(
                text = "Create a new account!",
                fontSize = 25.sp,
                fontFamily = authFam,
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(0.1f))

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.85f),
                text = "Name",
                fontSize = 15.sp,
                fontFamily = poppinsFam
            )
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(15.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Name"
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter fullname",
                        fontSize = 15.sp,
                        fontFamily = poppinsFam
                    )
                }
            )

            Spacer(modifier = Modifier.weight(0.1f))

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.85f),
                text = "Username",
                fontSize = 15.sp,
                fontFamily = poppinsFam
            )
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(15.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Username"
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter username",
                        fontSize = 15.sp,
                        fontFamily = poppinsFam
                    )
                }
            )

            Spacer(modifier = Modifier.weight(0.1f))

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.85f),
                text = "Email",
                fontSize = 15.sp,
                fontFamily = poppinsFam
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(15.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "Email"
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter email",
                        fontSize = 15.sp,
                        fontFamily = poppinsFam
                    )
                }
            )

            Spacer(modifier = Modifier.weight(0.1f))

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.85f),
                text = "Password",
                fontSize = 15.sp,
                fontFamily = poppinsFam
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(15.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Key,
                        contentDescription = "Password"
                    )
                },
                trailingIcon = {
                    val visiIcon = if(passwordVisi){
                        Icons.Outlined.Visibility
                    }else{
                        Icons.Outlined.VisibilityOff
                    }

                    IconButton(
                        onClick = {passwordVisi = !passwordVisi}
                    ) {
                        Icon(
                            imageVector = visiIcon,
                            contentDescription = "passwordVisibility"
                        )
                    }
                },
                visualTransformation = if (passwordVisi) VisualTransformation.None else PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        text = "Enter Password",
                        fontSize = 15.sp,
                        fontFamily = poppinsFam
                    )
                }
            )

            Spacer(modifier = Modifier.weight(0.1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.88f),
                onClick = {
                    if (
                        name.isNotEmpty() &&
                        username.isNotEmpty() &&
                        email.isNotEmpty() &&
                        password.isNotEmpty()
                    ){
                        authViewmodel.RegisterUser(name, username, email, password)
                        navController.navigate("home_graph"){
                            popUpTo(navController.graph.startDestinationId){
                                inclusive=true
                            }
                        }

                    }else{
                        Toast.makeText(context, "No field should be left empty", Toast.LENGTH_LONG).show()
                    }
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(
                    text = "Register",
                    fontSize = 15.sp,
                    fontFamily = poppinsFam,
                )
            }

            Spacer(modifier = Modifier.weight(0.09f))

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.88f),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.Black),
                onClick = {
                    navController.navigate("login_screen")
                }
            ) {
                Text(
                    text = "Already registered to Joke Junction?",
                    fontSize = 15.sp,
                    fontFamily = poppinsFam
                )
            }

            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}