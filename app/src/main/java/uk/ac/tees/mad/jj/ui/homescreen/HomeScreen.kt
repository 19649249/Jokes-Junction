package uk.ac.tees.mad.jj.ui.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.jj.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.jj.ui.theme.poppinsFam


@Composable
fun HomeScreen(
    authViewmodel: AuthViewmodel,
    navController: NavHostController
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "This is home screen",
            fontSize = 18.sp,
            fontFamily = poppinsFam
        )
        Button(onClick ={
            authViewmodel.logOut()
            navController.navigate("auth_graph"){
                popUpTo(navController.graph.startDestinationId){
                    inclusive=true
                }
            }
        }
        ){
            Text(
                text = "Logout",
                fontSize = 18.sp,
                fontFamily = poppinsFam
            )
        }
    }
}