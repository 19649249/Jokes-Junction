package uk.ac.tees.mad.jj.ui.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import uk.ac.tees.mad.jj.R
import uk.ac.tees.mad.jj.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.jj.ui.theme.ubuntuFamily


@Composable
fun CustomSplash(
    authViewmodel: AuthViewmodel,
    navController: NavHostController
){

    val isLoggedIn by authViewmodel.isLoggedIn.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column {
            Image(
                painter = painterResource(R.drawable.joke),
                contentDescription = "App Logo"
            )
            Text(
                text = "Joke Junction",
                fontSize = 25.sp,
                fontFamily = ubuntuFamily
            )
        }

        LaunchedEffect(Unit) {
            delay(3000L)
            if (isLoggedIn){
                navController.navigate("home_graph"){
                    popUpTo(navController.graph.startDestinationId){
                        inclusive=true
                    }
                }
            }else{
                navController.navigate("auth_graph"){
                    popUpTo(navController.graph.startDestinationId){
                        inclusive=true
                    }
                }
            }
        }

    }
}