package uk.ac.tees.mad.jj.ui.homescreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import uk.ac.tees.mad.jj.R
import uk.ac.tees.mad.jj.authentication.response.AuthState
import uk.ac.tees.mad.jj.authentication.viewmodel.AuthViewmodel
import uk.ac.tees.mad.jj.ui.theme.poppinsFam


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EditProfileScreen(
    authViewmodel: AuthViewmodel,
    navController: NavHostController
){

    val currentUser by authViewmodel.currentUser.collectAsState()
    var updatedName by remember { mutableStateOf(currentUser?.name ?: "") }
    var updatedUsername by remember { mutableStateOf(currentUser?.username ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Spacer(modifier = Modifier.weight(3f))
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

        Spacer(modifier = Modifier.weight(1f))

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
                text = "Update Profile Picture",
                fontFamily = poppinsFam,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = "Edit Account Details",
            fontFamily = poppinsFam,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier.fillMaxWidth(0.85f),
            text = "Name",
            fontFamily = poppinsFam,
            fontSize = 18.sp
        )
        Card(
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ){
            OutlinedTextField(
                value = updatedName,
                onValueChange = {
                    updatedName = it
                },
                modifier = Modifier.fillMaxWidth(0.88f),
                shape = RoundedCornerShape(15.dp),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Name"
                    )
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier.fillMaxWidth(0.85f),
            text = "Username",
            fontFamily = poppinsFam,
            fontSize = 18.sp
        )
        Card(
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ) {
            OutlinedTextField(
                value = updatedUsername,
                onValueChange = {
                    updatedUsername = it
                },
                modifier = Modifier.fillMaxWidth(0.88f),
                shape = RoundedCornerShape(15.dp),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Username"
                    )
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.DarkGray,
                containerColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, Color.Black),
            onClick = {
                authViewmodel.updateNameAndUsername(updatedName, updatedUsername)
                navController.popBackStack()
            }
        ) {
            Text(
                text = "Update Profile",
                fontFamily = poppinsFam,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.weight(10f))

    }
}
