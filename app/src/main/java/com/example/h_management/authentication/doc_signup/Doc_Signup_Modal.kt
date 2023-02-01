package com.example.h_management.authentication.doc_signup

import android.net.Uri
import android.os.Parcelable
import androidx.compose.runtime.MutableState
import kotlinx.parcelize.Parcelize

@Parcelize
data class Doc_Signup_Modal(
    var full_name: String,
    var email: String,
    var password: String,
    var contact_no: String,
    var hospital_name :String,
    var uri: Uri,
    var url:String,
):Parcelable
