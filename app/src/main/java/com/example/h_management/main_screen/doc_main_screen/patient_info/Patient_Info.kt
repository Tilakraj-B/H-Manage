package com.example.h_management.main_screen.doc_main_screen.patient_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.h_management.R




@Composable
fun Patient_Info_Card_View(){
    Surface(modifier = Modifier) {
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth(),
            elevation = 10.dp,
            backgroundColor = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                //    AsyncImage(
//                    model = ImageRequest
//                        .Builder(context = context)
//                        .data(getUriFromDrawableResId(context, R.drawable.default_patient_image))
//                        .crossfade(false).build(),
//                    contentDescription = "Patient Image",
//                    modifier = Modifier.size(150.dp).clip(CircleShape)
//                    )
                    Image(
                        painter = painterResource(id = R.drawable.default_patient_image),
                        contentDescription = "",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )


                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Patient Name  "
                    )
                    Text(
                        text = "Gaurdian Name  "
                    )

                    Text(
                        text = "Patient Id  "
                    )
                    Text(
                        text = "Blood group  "
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
                        text = "ID"
                    )
                    Text(
                        text = "o+"
                    )
                    Text(
                        text = "2121212121"
                    )

                }
            }
        }
    }
}




@Preview
@Composable
fun Preview(){
    Patient_Info_Card_View()
}

