package com.example.h_management.navigation

sealed class Screen(val route : String){
    object Start :Screen("start_screen")
    object Doc_Login : Screen("doc_login_screen")
    object Nur_Login : Screen("nur_login_screen")
    object Doc_Signup : Screen("doc_signup_screen")
    object Nur_Signup : Screen("nur_signup_screen")
    object Doc_Main_Screen : Screen("doc_main_screen")
    object New_Patient_Form : Screen("new_patient_screen")
    object Qr_Scan : Screen("qr_scan")
    object Nur_Main_Screen : Screen("nur_main_screen")
    object User_Profile_Screen : Screen("user_profile_screen")
}

