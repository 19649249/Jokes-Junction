package uk.ac.tees.mad.jj.ui.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Spacer(modifier = Modifier.weight(10f))
        Image(
            modifier = Modifier
                .height(200.dp)
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                .padding(1.dp)
                .clip(CircleShape),
            painter = painterResource(R.drawable.joke),
            contentDescription = "App Logo"
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Joke Junction",
            fontSize = 25.sp,
            fontFamily = ubuntuFamily
        )
        Spacer(modifier = Modifier.weight(10f))
    }
}