package com.example.h_management.main_screen.nur_main_screen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.h_management.main_screen.doc_main_screen.Doc_Main_Screen_Modal
import com.example.h_management.main_screen.doc_main_screen.new_patient.New_Patient_Modal
import com.example.h_management.navigation.Screen
import com.example.h_management.utils.getImageUri
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.tasks.await

class Nur_Main_Screen_Vm(val navHostController: NavHostController,val context : Context):ViewModel() {



        lateinit var firebaseAuth: FirebaseAuth
        lateinit var firebaseFirestore: FirebaseFirestore
        var uid: String? = null
        var list: MutableList<Doc_Main_Screen_Modal> = mutableListOf()
        var key = list.size


        fun Naviate_Qr_Scan(){
            navHostController.navigate(Screen.Qr_Scan.route){
            }
        }

        fun Navigate_Profile_Screen(){
            navHostController.navigate(Screen.User_Profile_Screen.route + "/{false}")
       }
        fun Navigate_Login_Screen(){
            navHostController.navigate(Screen.Nur_Login.route){
                popUpTo(Screen.Nur_Login.route)
          }
        }



    suspend fun get_patient_data_firestore() {
            Log.d("tag", "data get request invoked")
            firebaseAuth = FirebaseAuth.getInstance()
            uid = firebaseAuth.currentUser?.uid
            firebaseFirestore = FirebaseFirestore.getInstance()
            val hospital_name = Get_hospital_name()
            if (hospital_name != null) {
                firebaseFirestore
                    .collection("h-management")
                    .document("hospitals")
                    .collection(hospital_name)
                    .get()
                    .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                        if (task.isSuccessful) {
                            Log.d("tag","task result is successfull")
                            for (document in task.result) {
                                var modal = Doc_Main_Screen_Modal(
                                    document["Patient_name"].toString(),
                                    document["Patient_id"].toString(),
                                    document["Patient_image_url"].toString()
                                )
                                list.add(modal)
                            }

                        } else {
                            Log.d("tag", "Error getting documents: ", task.exception)
                        }
                    })
            }
            key = list.size

        }

        suspend fun Get_hospital_name():String?{
            Log.d("tag","Get_hospital_name invoked")
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseFirestore = FirebaseFirestore.getInstance()
            val uid = firebaseAuth.currentUser?.uid
            Log.d("tag","Uid --> ${uid}")
            val doc_ref = firebaseFirestore
                .collection("h-management")
                .document("user")
                .collection("nurse")
                .document(uid.toString())

            val hospital_name = doc_ref.get().await().data?.get("hospital_name")?.toString()
            Log.d("tag","Hospital Name --> ${hospital_name}")
            return hospital_name
        }

        fun Sign_Out(){
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut().apply {
                Log.d("tag","signed out")
                Toast.makeText(context,"Signed out",Toast.LENGTH_LONG).show()
                Navigate_Login_Screen()
            }
        }



}