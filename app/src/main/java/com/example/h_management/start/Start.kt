package com.example.h_management

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.h_management.navigation.Screen
import com.example.h_management.start.Start_Vm


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun User_Card(name :String, Res_id:Int, flag : Boolean,viewModel: Start_Vm){



    Card(elevation = 12.dp,
        modifier = Modifier
            .wrapContentSize()
            .padding(20.dp),
        onClick = {
            viewModel.card_click(flag)
        },
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.secondary){

        Column(modifier = Modifier.padding(12.dp)) {
            Image(painter = painterResource(id = Res_id), contentDescription = "",
                modifier = Modifier.clip(shape = CircleShape))
            Text(text = name, fontSize = 30.sp)
        }
    }
}
@Composable
fun Start_View(modifier: Modifier = Modifier,navHostController: NavHostController,viewModel: Start_Vm){

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(modifier = Modifier.padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,)
        {
            Image(painter = painterResource(id = R.drawable.img_1), contentDescription = "",
                alignment = Alignment.Center, modifier = Modifier.size(300.dp))

            Spacer(modifier = Modifier.height(100.dp))

            Row(modifier = Modifier.padding(10.dp)){
                User_Card(name = "Doctor", Res_id = R.drawable.img,true,viewModel)
                User_Card(name = "Nurse", Res_id = R.drawable.img_2,false,viewModel)
            }
        }
    }

}

