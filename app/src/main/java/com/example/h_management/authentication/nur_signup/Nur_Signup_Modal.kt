package com.example.h_management.authentication.nur_signup

import android.net.Uri

data class Nur_Signup_Modal(
    val full_name:String,
    val email:String,
    val password :String,
    val contact_no:String,
    val hospital_name : String,
    var uri: Uri,
    var url:String,
)