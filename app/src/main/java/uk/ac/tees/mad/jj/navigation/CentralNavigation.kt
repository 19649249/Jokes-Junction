package uk.ac.tees.mad.jj.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import uk.ac.tees.mad.jj.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.jj.jokes.viemodel.JokesViewModel
import uk.ac.tees.mad.jj.ui.authentication.CustomSplash
import uk.ac.tees.mad.jj.ui.authentication.LogInScreen
import uk.ac.tees.mad.jj.ui.authentication.SignUpScreen
import uk.ac.tees.mad.jj.ui.homescreen.EditProfileScreen
import uk.ac.tees.mad.jj.ui.homescreen.HomeScreen
import uk.ac.tees.mad.jj.ui.homescreen.ProfileScreen


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun CentralNavigation(
    authViewmodel: AuthViewmodel,
    navController: NavHostController,
    jokesViewModel: JokesViewModel
){
    NavHost(
        navController = navController,
        startDestination = "splash_graph"
    ) {
        navigation(
            startDestination = "splash_screen",
            route = "splash_graph"
        ){
            composable("splash_screen") {
                CustomSplash(authViewmodel, navController)
            }
        }

        navigation(
            startDestination = "login_screen",
            route = "auth_graph"
        ){
            composable("login_screen") {
                LogInScreen(authViewmodel, navController)
            }

            composable("signup_screen") {
                SignUpScreen(authViewmodel, navController)
            }
        }

        navigation(
            startDestination = "home_screen",
            route = "home_graph"
        ){
            composable("home_screen") {
                HomeScreen(authViewmodel, navController,jokesViewModel)
            }

            composable("profile_screen"){
                ProfileScreen(authViewmodel, navController)
            }

            composable("edit_profile_screen"){
                EditProfileScreen(authViewmodel, navController)
            }
        }
    }
}