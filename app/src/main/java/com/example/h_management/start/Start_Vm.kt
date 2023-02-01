package com.example.h_management.start

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.h_management.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Start_Vm(val navHostController: NavHostController):ViewModel() {

    init {
        check_User()
    }
    private lateinit var firebaseAuth: FirebaseAuth


    fun Navigate_Doc_Main_Scree(){
        navHostController.navigate(Screen.Doc_Main_Screen.route)
    }

    fun check_User(){
        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        user?.reload()?.addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("check user","previous user reauthenticated")
                Navigate_Doc_Main_Scree()

            }
            else{
                Log.d("check user","No user found")
            }
        }
    }

    fun card_click(flag :Boolean){
        if(flag){
            navHostController.navigate(Screen.Doc_Login.route)
        }
        else{
            navHostController.navigate(Screen.Nur_Login.route)
        }
    }

}