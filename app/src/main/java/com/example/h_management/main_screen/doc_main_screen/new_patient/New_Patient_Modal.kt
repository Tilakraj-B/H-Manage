package com.example.h_management.main_screen.doc_main_screen.new_patient

import android.net.Uri


data class New_Patient_Modal(
    var name: String,
    var age: String,
    var guarduian_name:String,
    var contact_no:String,
    var email:String,
    var blood_group: String,
    var uri: Uri,
    var url:String,
    var issue: String,
    var id: String?,
    )
