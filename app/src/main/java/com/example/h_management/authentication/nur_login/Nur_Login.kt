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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.h_management.authentication.nur_login.Nur_Login_Modal
import com.example.h_management.authentication.nur_login.Nur_Login_Vm
import kotlinx.coroutines.launch


@Composable
fun Nur_Login(
    modifier: Modifier = Modifier,
    viewModel: Nur_Login_Vm,
) { Box(
    modifier = Modifier.fillMaxSize()
) {
        ClickableText(
            text = AnnotatedString("Sign Up here"),
            onClick = { viewModel.Navigate_Nur_Signup_Screen() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colors.secondary
            )
        )
    }


    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var phone_no by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        val modal = Nur_Login_Modal(
            contact_no = phone_no,
            password = password
        )
        val coroutine = rememberCoroutineScope()

        Text(
            text = "Nurse Login",
            style = TextStyle(
                fontSize = 40.sp),
            fontFamily = FontFamily.Default
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = phone_no,
            onValueChange = { phone_no = it },
            label = {
                Text(text = "Phone no.") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = ""
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            leadingIcon = { Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "")
            }
        )


        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .padding(
                    40.dp,
                    0.dp,
                    40.dp,
                    0.dp)
        ) {
            Button(
                onClick = {coroutine.launch { viewModel.Login_Click(modal) }},
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)) {
                Text(
                    text = "Login"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = {},
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default)
        )


    }
}


