package uk.ac.tees.mad.jj.ui.homescreen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import uk.ac.tees.mad.jj.R
import uk.ac.tees.mad.jj.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.jj.ui.theme.poppinsFam


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(
    authViewmodel: AuthViewmodel,
    navController: NavHostController
){
    val currentUser by authViewmodel.currentUser.collectAsState()

    LaunchedEffect(currentUser){
        authViewmodel.fetchCurrentUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Spacer(modifier = Modifier.weight(4F))
        GlideImage(
            modifier = Modifier
                .height(300.dp)
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                .border(
                    1.dp,
                    Color.DarkGray,
                    shape = CircleShape
                )
                .padding(1.dp)
                .clip(CircleShape),
            model = currentUser?.profilePicture,
            contentDescription = "",
            failure = placeholder(painter = painterResource(R.drawable.avatar))
        )

        Spacer(modifier = Modifier.weight(1F))

        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.DarkGray,
                containerColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, Color.Black),
            onClick = {

            }
        ) {
            Text(
                text = "Edit Profile",
                fontFamily = poppinsFam,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.weight(1F))

        currentUser?.name?.let {name->
            Text(
                text = name,
                fontFamily = poppinsFam,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.weight(1F))

        currentUser?.username?.let {username->
            Text(
                text = username,
                fontFamily = poppinsFam,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.weight(1F))

        currentUser?.email?.let {email->
            Text(
                text = email,
                fontFamily = poppinsFam,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.weight(10f))
    }
}