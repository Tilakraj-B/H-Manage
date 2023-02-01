package com.example.h_management.authentication.nur_login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.h_management.authentication.doc_login.Doc_Login_Modal
import com.example.h_management.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject





@HiltViewModel
class Nur_Login_Vm @Inject constructor(val navHostController: NavHostController,val context : Context): ViewModel() {


    private lateinit var auth : FirebaseAuth
    private lateinit var user :String

    fun Navigate_Nur_Signup_Screen () {
        navHostController.navigate(Screen.Nur_Signup.route) {
            popUpTo(Screen.Start.route) {
            }
        }
    }

    fun Navigate_Nur_Main_Screen(){
        navHostController.navigate(Screen.Nur_Main_Screen.route){
            popUpToRoute
        }
    }


    fun Login_Click(modal: Nur_Login_Modal) {


        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if(user != null){
            user.reload().addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(context,"Relod successfull in", Toast.LENGTH_LONG).show()
                    Log.d("tag","Logined in Successfull")
                }
                else{
                    Toast.makeText(context,"Logined in Failed", Toast.LENGTH_LONG).show()
                    Log.d("login","Cannot Login")
                }
            }
        }
        else{
            auth.signInWithEmailAndPassword(modal.contact_no+"@nurse.in",modal.password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Navigate_Nur_Main_Screen()
                    Toast.makeText(context,"Logined in", Toast.LENGTH_LONG).show()
                    Log.d("tag","Logined in Successfull")
                }
                else{
                    Toast.makeText(context,"Logined in Failed", Toast.LENGTH_LONG).show()
                    Log.d("tag","Cannot Login")
                }
            }
        }

    }
}

