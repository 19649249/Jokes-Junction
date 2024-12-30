package uk.ac.tees.mad.jj.ui.homescreen

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.jj.favourites.entity.FavouriteJokeInfo
import uk.ac.tees.mad.jj.jokes.viemodel.JokesViewModel
import uk.ac.tees.mad.jj.ui.theme.interFam
import uk.ac.tees.mad.jj.ui.theme.poppinsFam


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteJokesScreen(
    navController: NavHostController,
    jokesViewModel: JokesViewModel
){
    val favouriteJokes by jokesViewModel.favJokeList.collectAsState()


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
                }
            )
        }
    ){innerpadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .background(
                    brush = Brush.linearGradient(
                        0.2f to Color(0xFFF7A6D0),
                        0.4f to Color(0xFF94bbe9),
                        1.0f to Color(0xFFeeaeca)
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){

            Text(
                text = "FAVOURITES!!",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = poppinsFam
            )
            if(favouriteJokes.isNotEmpty()){
                LazyColumn {
                    items(favouriteJokes.size){index->
                        FavouriteJokesTile(favouriteJokes[index], jokesViewModel)
                        if (index >= favouriteJokes.size-4){
                            jokesViewModel.fetchJokesApi()
                        }
                    }
                }
            }else{
                Button(

                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9EB1F4),
                        contentColor = Color.DarkGray
                    ),
                    border = BorderStroke(1.dp, Color.Black),
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        text = "Explore Jokes Now!!",
                        fontSize = 15.sp,
                        fontFamily = poppinsFam,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FavouriteJokesTile(
    favJokes: FavouriteJokeInfo,
    jokesViewModel: JokesViewModel
){
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }


    Card(
        modifier = Modifier
            .fillMaxSize(0.95f)
            .padding(horizontal = 0.dp, vertical = 10.dp),
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            Row {
                Text(
                    text = "Setup:",
                    fontSize = 18.sp,
                    fontFamily = poppinsFam,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = favJokes.setup,
                    fontSize = 18.sp,
                    fontFamily = poppinsFam,
                )
            }

            Row {
                Text(
                    text = "Punchline:",
                    fontSize = 18.sp,
                    fontFamily = poppinsFam,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = favJokes.punchline,
                    fontSize = 18.sp,
                    fontFamily = poppinsFam,
                )
            }

            Row {
                Text(
                    text = "Type:",
                    fontSize = 15.sp,
                    fontFamily = poppinsFam,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = favJokes.type,
                    fontSize = 15.sp,
                    fontFamily = poppinsFam
                )
            }

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                maxItemsInEachRow = 2,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                AssistChip(onClick = {
                    jokesViewModel.deleteFavourite(favJokes)
                    Toast.makeText(context, "The Joke is deleted from the Favourites!", Toast.LENGTH_SHORT).show()
                },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color.White,
                        leadingIconContentColor = Color.Red
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Favorite,
                            contentDescription ="Add to favourite")
                    },
                    label = {
                        Text(
                            fontFamily = poppinsFam,
                            text = "Remove Favourite",
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    }
                )

                AssistChip(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "type/text"
                        intent.putExtra(Intent.EXTRA_TEXT, favJokes.setup+"\n"+favJokes.punchline)
                        launcher.launch(Intent.createChooser(intent, "Share via"))
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color.White,
                        leadingIconContentColor = Color.Black
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Share,
                            contentDescription ="Add to favourite")
                    },
                    label = {
                        Text(
                            fontFamily = poppinsFam,
                            text = "Share With Friends",
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    }
                )
            }
        }
    }
}