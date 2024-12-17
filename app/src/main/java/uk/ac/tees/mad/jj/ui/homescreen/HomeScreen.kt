package uk.ac.tees.mad.jj.ui.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.jj.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.jj.jokes.viemodel.JokesViewModel
import uk.ac.tees.mad.jj.model.JokesInfoItem
import uk.ac.tees.mad.jj.ui.theme.interFam
import uk.ac.tees.mad.jj.ui.theme.poppinsFam


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authViewmodel: AuthViewmodel,
    navController: NavHostController,
    jokesViewModel: JokesViewModel
){

    val jokesList by jokesViewModel.jokesList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Jokes Junction",
                        fontFamily = interFam,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            authViewmodel.fetchCurrentUser()
                            navController.navigate("profile_screen")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    }
                }
            )
        }
    ){innerpadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            LazyColumn {
                items(jokesList){jokes->
                    JokesItemTile(jokes)
                }
            }
        }
    }
}


@Composable
fun JokesItemTile(jokes: JokesInfoItem){
    Card {
        Text(
            text = jokes.setup,
            fontSize = 17.sp,
            fontFamily = poppinsFam,
        )

        Text(
            text = jokes.punchline,
            fontSize = 17.sp,
            fontFamily = poppinsFam,
        )
        Text(
            text = jokes.type,
            fontSize = 14.sp,
            fontFamily = poppinsFam
        )
    }
}
