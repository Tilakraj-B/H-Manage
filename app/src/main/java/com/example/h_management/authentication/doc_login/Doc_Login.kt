package com.example.h_management.authentication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.h_management.authentication.doc_login.Doc_Login_Modal
import com.example.h_management.authentication.doc_login.Doc_Login_Vm
import com.example.h_management.authentication.nur_login.Nur_Login_Vm
import dagger.hilt.android.lifecycle.HiltViewModel



@Composable
fun Doc_Login(
    modifier: Modifier = Modifier,
    viewModel: Doc_Login_Vm) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ClickableText(
            text = AnnotatedString("Sign Up here"),
            onClick = { viewModel.Signuphere_Click() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colors.secondary)
        )
    }


    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var email by rememberSaveable {mutableStateOf("")}
        var password by rememberSaveable { mutableStateOf("") }
        var modal = Doc_Login_Modal(email = email, password = password)

        Text(text = "Doctor Login",
            style = TextStyle(
                fontSize = 40.sp
            ),
            fontFamily = FontFamily.Default
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "E-mail") },
            leadingIcon = { Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it},
            label = { Text(
                text = "Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = { viewModel.Login_Click(modal = modal) },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)) {
                Text(text = "Login")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ClickableText(text = AnnotatedString("Forgot password?"),
            onClick = {viewModel.Navigate_Doc_Main_Screen() },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default)
        )
    }
}








