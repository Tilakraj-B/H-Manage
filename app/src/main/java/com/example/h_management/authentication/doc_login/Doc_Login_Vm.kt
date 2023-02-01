package com.example.h_management.authentication.doc_login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.h_management.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class Doc_Login_Vm (val navHostController: NavHostController,val context: Context): ViewModel(){



    private lateinit var auth : FirebaseAuth
    private lateinit var user :String


    fun Signuphere_Click(){
        navHostController.navigate(Screen.Doc_Signup.route){

        }
    }

    fun Navigate_Doc_Main_Screen(){
        navHostController.navigate(Screen.Doc_Main_Screen.route){
            popUpToRoute
        }

    }

    fun Login_Click(modal: Doc_Login_Modal) {


        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if(user != null){
            user.reload().addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(context,"Relod successfull in",Toast.LENGTH_LONG).show()
                    Log.d("login","Logined in Successfull")

                    Navigate_Doc_Main_Screen()
                }
                else{
                    Toast.makeText(context,"Logined in Failed",Toast.LENGTH_LONG).show()
                    Log.d("login","Cannot Login")
                }
            }
        }
        else{
            auth.
            signInWithEmailAndPassword(modal.email,modal.password).
            addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(context,"Logined in",Toast.LENGTH_LONG).show()
                    Log.d("login","Logined in Successfull")
                    Navigate_Doc_Main_Screen()
                }
                else{
                    Toast.makeText(context,"Logined in Failed",Toast.LENGTH_LONG).show()
                    Log.d("login","Cannot Login")
                }
            }
        }

    }






}