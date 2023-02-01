package com.example.h_management.navigation

import Nur_Main_Screen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.h_management.Start_View
import com.example.h_management.authentication.Doc_Login
import com.example.h_management.authentication.Doc_Signup
import com.example.h_management.authentication.Nur_Login
import com.example.h_management.authentication.Nur_Signup
import com.example.h_management.authentication.doc_login.Doc_Login_Vm
import com.example.h_management.authentication.doc_signup.Doc_Signup_Vm
import com.example.h_management.authentication.doc_signup.Nur_Signup_Vm
import com.example.h_management.authentication.nur_login.Nur_Login_Vm
import com.example.h_management.main_screen.doc_main_screen.Doc_Main_Screen
import com.example.h_management.main_screen.doc_main_screen.Doc_Main_Screen_Vm
import com.example.h_management.main_screen.doc_main_screen.new_patient.New_Patient_Form
import com.example.h_management.main_screen.doc_main_screen.new_patient.New_Patient_Vm
import com.example.h_management.main_screen.doc_main_screen.qr.Scan_QR
import com.example.h_management.main_screen.nur_main_screen.Nur_Main_Screen_Vm
import com.example.h_management.main_screen.user_profile_screen.User_Profile
import com.example.h_management.main_screen.user_profile_screen.user_profile_Vm
import com.example.h_management.start.Start_Vm

@Composable
fun NavHost(navHostController: NavHostController){

    val context = LocalContext.current

    NavHost(navController = navHostController, startDestination = Screen.Start.route ){
        composable(route = Screen.Start.route){
            Start_View(navHostController = navHostController, viewModel = Start_Vm(navHostController))
        }

        composable(route = Screen.Doc_Login.route){
            Doc_Login(viewModel = Doc_Login_Vm(navHostController,context))
        }
        composable(route = Screen.Nur_Login.route){
            Nur_Login(viewModel = Nur_Login_Vm(navHostController,context))
        }
        composable(route = Screen.Doc_Signup.route){
            Doc_Signup(viewModel = Doc_Signup_Vm(navHostController,context))
        }
        composable(route = Screen.Nur_Signup.route){
            Nur_Signup(viewModel = Nur_Signup_Vm(navHostController,context))
        }
        composable(route = Screen.Doc_Main_Screen.route){
            Doc_Main_Screen(viewModel = Doc_Main_Screen_Vm(navHostController,context))
        }
        composable(route = Screen.New_Patient_Form.route){
            New_Patient_Form(viewModel = New_Patient_Vm(navHostController,context))
        }
        composable(route = Screen.Qr_Scan.route){
            Scan_QR()
        }
        composable(route = Screen.Nur_Main_Screen.route){
            Nur_Main_Screen(viewModel = Nur_Main_Screen_Vm(navHostController,context))
        }

        composable(
            route = Screen.User_Profile_Screen.route + "/{flag}",
            arguments = listOf(
                navArgument(name = "flag"){
                    type = NavType.BoolType
                    defaultValue = true
                }
            )
        ){
            User_Profile(viewModel = user_profile_Vm(context,navHostController,flag = it.arguments!!.getBoolean("flag")))
        }




    }

}
