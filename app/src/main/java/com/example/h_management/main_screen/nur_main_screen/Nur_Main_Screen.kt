@file:Suppress("OPT_IN_IS_NOT_ENABLED")
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.h_management.R
import com.example.h_management.main_screen.doc_main_screen.*
import com.example.h_management.main_screen.doc_main_screen.New_Patient_Button
import com.example.h_management.main_screen.doc_main_screen.Patient_Card
import com.example.h_management.main_screen.doc_main_screen.Recycler_View
import com.example.h_management.main_screen.doc_main_screen.Top_Bar
import com.example.h_management.main_screen.nur_main_screen.Nur_Main_Screen_Modal
import com.example.h_management.main_screen.nur_main_screen.Nur_Main_Screen_Vm



import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.h_management.utils.getUriFromDrawableResId
import kotlinx.coroutines.launch


@Composable
fun Top_Bar(
    modifier: Modifier = Modifier,
    viewModel:Nur_Main_Screen_Vm
) {
    Box {
        TopAppBar(
            modifier = Modifier.wrapContentHeight(),
            elevation = 10.dp,
            backgroundColor = MaterialTheme.colors.secondary)
        {
            var search_text by rememberSaveable{ mutableStateOf("") }
            val coroutine = rememberCoroutineScope()

            TextField(
                value = search_text,
                onValueChange = { search_text = it },
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                label = {"Patient Name/Id"},
                textStyle = TextStyle(fontSize = 20.sp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_baseline_search_24),
                        contentDescription = "Search",
                    )
                }
            )
            IconButton(
                onClick = { viewModel.Naviate_Qr_Scan() },
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.secondaryVariant,
                        shape = CircleShape
                    )
                    .wrapContentSize()
                    .size(35.dp)) {

                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_baseline_qr_code_scanner_24
                    ),
                    contentDescription = "Scan")
            }

            Spacer(modifier = Modifier.width(10.dp))

            IconButton(
                onClick = { viewModel.Navigate_Profile_Screen() },
                modifier = Modifier
                    .background(
                        color = MaterialTheme
                            .colors
                            .secondaryVariant,
                        shape = CircleShape
                    )
                    .wrapContentSize()
                    .size(35.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Person")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Patient_Card(
    modifier: Modifier = Modifier,
    modal: Nur_Main_Screen_Modal
){
    val context = LocalContext.current
    Card(
        modifier = modifier
    ) {

        Column(
            modifier = modifier.padding(10.dp)
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data(modal.Patient_image_url)
                    .crossfade(true)
                    .build(),
                contentDescription = modal.Patient_name,
                modifier = modifier.size(200.dp)
            )

            Spacer(modifier = modifier.height(10.dp))

            Text(
                text = modal.Patient_name.toString(),
                modifier = modifier
                    .align(Alignment.CenterHorizontally),
                fontSize = 30.sp
            )

            Spacer(modifier = modifier.height(10.dp))

            Text(
                text = modal.Patient_id.toString(),
                modifier = modifier
                    .align(Alignment.CenterHorizontally),
                fontSize = 30.sp)
        }

    }
}

@Composable
fun Nur_Main_Screen(
    modifier: Modifier = Modifier,
    viewModel: Nur_Main_Screen_Vm
){
    Column {
        Top_Bar(viewModel = viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 10.dp,
                    end = 10.dp,
                    bottom = 10.dp
                )
                .weight(1f)
        ){
            Recycler_View(viewModel = viewModel)
        }
        Spacer(modifier = modifier.height(10.dp))
    }


}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Recycler_View(
    viewModel: Nur_Main_Screen_Vm
){
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    coroutine.launch { viewModel.get_patient_data_firestore() }
    var list = rememberSaveable {viewModel.list }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(16.dp)
    ){
        items(list){
            Patient_Card(modal = it)
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    val modal= Doc_Main_Screen_Modal(
        "name",
        "id",
        getUriFromDrawableResId(LocalContext.current,R.drawable.default_patient_image).toString())
    Surface (modifier = Modifier){
        Patient_Card(modal = modal)
    }
}
