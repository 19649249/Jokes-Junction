package uk.ac.tees.mad.jj.ui.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.jj.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.jj.ui.theme.poppinsFam


@Composable
fun ProfileScreen(
    authViewmodel: AuthViewmodel,
    navController: NavHostController
){
    val currentUser = authViewmodel.currentUser.collectAsState()

    LaunchedEffect(currentUser){
        authViewmodel.fetchCurrentUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = currentUser.value.toString(),
            fontFamily = poppinsFam,
            fontSize = 18.sp
        )
    }
}