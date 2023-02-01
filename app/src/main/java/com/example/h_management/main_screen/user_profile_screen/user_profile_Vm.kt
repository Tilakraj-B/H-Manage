package com.example.h_management.main_screen.user_profile_screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.h_management.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class user_profile_Vm(val context : Context, val navHostController: NavHostController,val flag:Boolean):ViewModel() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore
    var map = mutableMapOf<String,String>()



    fun Navigate_Nur_Login_Screen(){
        navHostController.navigate(Screen.Nur_Login.route){
            popUpTo(Screen.Nur_Login.route)
        }
    }

    fun Navigate_Doc_Login_Screen(){
        navHostController.navigate(Screen.Doc_Login.route){
            popUpTo(Screen.Doc_Login.route)
        }
    }

    suspend fun getData(){
        Log.d("tag","getData invoked in user_profile")
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid
        Log.d("tag","user_profile uid -> ${uid}")
        var collection_name = "doctor"
        if(!flag){
            collection_name = "nurse"
        }
        val documentreference = firebaseFirestore
            .collection("h-management")
            .document("user")
            .collection(collection_name)
            .document(uid.toString())
        documentreference.get().await().apply {
            map.put("name",this.data?.get("name").toString())
            map.put("email",this.data?.get("email").toString())
            map.put("phone_no",this.data?.get("phone_no").toString())
            map.put("image_url",this.data?.get("image_url").toString())
        }
    }


    fun Sign_Out(){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        Log.d("tag","signed out successfully")
        if(flag){
            Navigate_Doc_Login_Screen()
            Toast.makeText(context,"Signed Out",Toast.LENGTH_LONG).show()
        }
        else{
            Navigate_Nur_Login_Screen()
            Toast.makeText(context,"Signed Out",Toast.LENGTH_LONG).show()
        }


    }

}