package com.example.h_management.authentication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.h_management.BuildConfig
import com.example.h_management.R
import com.example.h_management.authentication.doc_signup.Nur_Signup_Vm
import com.example.h_management.authentication.nur_signup.Nur_Signup_Modal
import com.example.h_management.utils.createImageFile
import com.example.h_management.utils.getUriFromDrawableResId
import kotlinx.coroutines.launch
import java.util.*


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Nur_Signup(modifier: Modifier = Modifier,viewModel: Nur_Signup_Vm) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ClickableText(
            text = AnnotatedString("Already a user? Login"),
            onClick = { viewModel.Navigate_Nur_Login_Screen() },
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
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nurse Sign Up",
            style = TextStyle(fontSize = 40.sp),
            fontFamily = FontFamily.Default
        )

        Spacer(modifier = Modifier.height(20.dp))
        var fullname by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var work_Id by rememberSaveable { mutableStateOf("") }
        var phone_no by rememberSaveable { mutableStateOf("") }
        var hospital_name by rememberSaveable{ mutableStateOf("") }
        var coroutine = rememberCoroutineScope()
        val context = LocalContext.current
        val file = context.createImageFile()
        val uri = FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            BuildConfig.APPLICATION_ID + ".provider", file
        )
        var capturedImageUri by remember {
            mutableStateOf<Uri>(getUriFromDrawableResId(context, R.drawable.default_patient_image))
        }
        var modal  = Nur_Signup_Modal(
            full_name = fullname,
            email = email,
            hospital_name = hospital_name,
            password = password,
            contact_no = phone_no,
            uri = uri,
            url = Uri.EMPTY.toString(),
        )

        val cameraLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) {
            capturedImageUri = uri
        }


        var hasCamPermission by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            )
        }




        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                hasCamPermission = granted
            }
        )
        LaunchedEffect(key1 = true) {
            launcher.launch(Manifest.permission.CAMERA)
        }

        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(capturedImageUri)
                .crossfade(true)
                .build(),
            contentDescription ="Nurse Image",
            modifier = Modifier
                .size(150.dp)
                .clip(
                    CircleShape
                )
        )

        Spacer(modifier = Modifier.height(6.dp))


        IconButton(
            onClick = {
                if(hasCamPermission ){
                    cameraLauncher.launch(uri)
                }
            }) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_baseline_camera_alt_24
                ),
                contentDescription ="",
                modifier = Modifier.size(35.dp)
            )
        }


        OutlinedTextField(
            value = fullname,
            onValueChange = { fullname = it },
            label = { Text(text = "Full Name") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "E-mail") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") }
        )

        Spacer(modifier = Modifier.height(20.dp))


        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = phone_no,
            onValueChange = { phone_no = it },
            label = { Text(text = "Contact No.") }
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = hospital_name,
            onValueChange = { hospital_name = it },
            label = { Text(text = "Hospital Name") }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .padding(40.dp, 0.dp, 40.dp, 0.dp)
        ) {
            Button(
                onClick = { coroutine.launch {viewModel.Signup_Button_Click(modal,capturedImageUri)  } },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                elevation = ButtonDefaults
                    .elevation(
                        defaultElevation = 20.dp,
                        pressedElevation = 10.dp,
                        disabledElevation = 0.dp
                    )
            ) {
                Text(text = "Sign Up")
            }

        }

    }
}



@Preview
@Composable
private fun preview(){
    Surface{

    }
    
}
