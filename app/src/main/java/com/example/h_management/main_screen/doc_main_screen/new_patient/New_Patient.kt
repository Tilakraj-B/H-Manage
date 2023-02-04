package com.example.h_management.main_screen.doc_main_screen.new_patient

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.h_management.BuildConfig
import com.example.h_management.R
import com.example.h_management.ui.theme.Shapes
import com.example.h_management.utils.*
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun Issue_List(modal: New_Patient_Modal,viewModel: New_Patient_Vm,index:Int){
    var issue by rememberSaveable{ mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier.padding(5.dp),
        value = issue,
        onValueChange ={
            issue = it
        },
        label = {
            Text(text = "Issue")
        },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults
                .textFieldColors(backgroundColor = Color.Transparent),
    )
    Spacer(modifier = Modifier.height(6.dp))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun New_Patient_Form(
    modifier: Modifier = Modifier,
    viewModel: New_Patient_Vm
){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val context = LocalContext.current

        var full_name by rememberSaveable{ mutableStateOf("") }
        var age by rememberSaveable{ mutableStateOf("") }
        var guardian_name by rememberSaveable{ mutableStateOf("") }
        var contact_no by rememberSaveable{ mutableStateOf("") }
        var email by rememberSaveable{ mutableStateOf("") }
        var blood_group by rememberSaveable{ mutableStateOf("") }
        var issue by rememberSaveable{ mutableStateOf("") }
        val coroutineScope  = rememberCoroutineScope()
        val file = context.createImageFile()
        var openDialog by rememberSaveable{ mutableStateOf(false) }

        val uri = FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            BuildConfig.APPLICATION_ID + ".provider", file
        )
        var capturedImageUri by remember {
            mutableStateOf<Uri>(getUriFromDrawableResId(context,R.drawable.default_patient_image))
        }
        val modal = New_Patient_Modal(
            full_name,
            age,
            guardian_name,
            contact_no,
            email,
            blood_group,
            uri,
            issue = issue,
            url = Uri.EMPTY.toString(),
            id = ""
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




        Spacer(modifier = Modifier.height(6.dp))

        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(capturedImageUri)
                .crossfade(true)
                .build(),
            contentDescription ="Patient Image",
             modifier = Modifier
                 .size(200.dp)
                 .clip(
                     CircleShape
                 )
        )

        Spacer(modifier = Modifier.height(6.dp))


        IconButton(
            onClick = {
                if(hasCamPermission){
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

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = full_name,
            onValueChange = { full_name = it },
            label = { Text(text = "Full Name") },
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text(text = "Age") },
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = guardian_name,
            onValueChange = {guardian_name = it },
            label = { Text(text = "Guardian Name") },
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = contact_no,
            onValueChange = {contact_no = it },
            label = { Text(text = "Contact No.") },
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email = it },
            label = { Text(text = "E-Mail") },
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = blood_group,
            onValueChange = {blood_group = it },
            label = { Text(text = "Blood Group") },
        )

        Spacer(modifier = Modifier.height(12.dp))

//        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.border(1.dp,Color.Gray,
//            RoundedCornerShape(1.dp))) {
//            var textFieldCount  by remember { mutableStateOf (1) }
//            var textFieldCountprev by rememberSaveable {
//                mutableStateOf(1)
//            }
//
//            Row(modifier = Modifier,
//            horizontalArrangement = Arrangement.SpaceAround){
//                Text(text = "Issue", fontSize = 24.sp)
//                IconButton(onClick = { textFieldCount++ },) {
//                    Icon(painter = painterResource(id = R.drawable.ic_baseline_add_circle_outline_24), contentDescription = "Add Issue")
//                }
//            }
//
//            repeat(textFieldCount){
//                Issue_List(modal = modal, viewModel = viewModel , index = textFieldCount-1)
//            }
//
//
//        }
        OutlinedTextField(
            modifier = Modifier.padding(5.dp),
            value = issue,
            onValueChange ={ issue = it },
            label = { Text(text = "Issue") },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults
                .textFieldColors(backgroundColor = Color.Transparent),
        )
        Spacer(modifier = Modifier.height(6.dp))

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { coroutineScope.launch {
                openDialog = true
//                viewModel.Add_Patient(capturedImageUri,modal)
            } },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_baseline_check_circle_outline_24
                ),
                contentDescription = "",Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Add Patient",)
        }

        if(openDialog){
            AlertDialog(
                onDismissRequest = { /*TODO*/ },
                title = {
                    Text(text = "Admit Patient ? ")
                },
                buttons = {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                   viewModel.Add_Patient(capturedImageUri,modal)
                                   Toast.makeText(context, "NO", Toast.LENGTH_LONG)
                                       .show()
                                }
                                      },
                        ) {
                            Text(text = "No")
                        }

                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.Add_Patient(capturedImageUri,modal)
                                    Toast.makeText(context, "Yes", Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                        ) {
                            Text(text = "Yes")
                        }
                    }
                }
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun Preview(){
    val nav = rememberNavController()
    Surface(Modifier.fillMaxSize()) {
    }
}

