package com.example.h_management.main_screen.user_profile_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.h_management.R
import kotlinx.coroutines.launch

@Composable
fun User_Profile_View(
//    modifier: Modifier,
//    viewModel: user_profile_Vm
//    modal:user_profile_modal
){
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val context = LocalContext.current
            val coroutine = rememberCoroutineScope()
//            AsyncImage(
//                model = ImageRequest
//                    .Builder(context = context)
//                    .data(modal.image_url)
//                    .crossfade(true)
//                    .build(),
//                contentDescription = modal.name)
            Image(
                painter = painterResource(id = R.drawable.default_patient_image),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )


            Row(
                modifier = Modifier.padding(20.dp)
            ) {

                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Name  "
                    )
                    Text(
                        text = "Email  "
                    )
                    Text(
                        text = "Contact No.  "
                    )

                }



                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Tilakraj"
                    )
                    Text(
                        text = "Tilakraj"
                    )

                    Text(
                        text = "2121212121"
                    )

                }
            }

            Button(
                onClick = {},
            ) {
                Text(text = "Sign Out")
            }
        }



}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun User_Profile(
    viewModel: user_profile_Vm,
){


    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val coroutine = rememberCoroutineScope()
        val context = LocalContext.current

        coroutine.launch { viewModel.getData() }
        val list = rememberSaveable {viewModel.map}

        AsyncImage(
                model = ImageRequest
                    .Builder(context = context)
                    .data(list.get("image_url"))
                    .crossfade(true)
                    .build(),
                contentDescription = ""
        )


        Row(
            modifier = Modifier.padding(20.dp)
        ) {

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Name  "
                )
                Text(
                    text = "Email  "
                )
                Text(
                    text = "Contact No.  "
                )

            }


            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = list["name"].toString()
                )
                Text(
                    text = list["email"].toString()
                )
                Text(
                    text = list["phone_no"].toString()
                )

            }
        }

        Button(
            onClick = { viewModel.Sign_Out() },
        ) {
            Text(text = "Sign Out")
        }
    }



}

@Preview
@Composable
fun Preview(){
    Surface(modifier = Modifier.fillMaxSize()) {
        User_Profile_View()
    }
}