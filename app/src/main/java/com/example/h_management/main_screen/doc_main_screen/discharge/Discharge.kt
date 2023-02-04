package com.example.h_management.main_screen.doc_main_screen.discharge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.h_management.main_screen.doc_main_screen.patient_info.Patient_Info_Card_View

@Composable
fun Discharge_Preview(
    modifier: Modifier
){
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Patient_Info_Card_View()



    }

}

@Preview(showBackground = true)
@Composable
private fun Display(){
    Surface(modifier = Modifier.fillMaxSize()) {
        Discharge_Preview(modifier = Modifier)
    }
}