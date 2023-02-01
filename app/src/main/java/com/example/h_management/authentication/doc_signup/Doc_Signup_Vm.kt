package com.example.h_management.authentication.doc_signup

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.h_management.main_screen.doc_main_screen.new_patient.New_Patient_Modal
import com.example.h_management.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class Doc_Signup_Vm(val navHostController: NavHostController, val context: Context):ViewModel() {


    private lateinit var auth: FirebaseAuth
    private lateinit var store : FirebaseFirestore
    private lateinit var storageReference:StorageReference

    fun Navigate_Login_Screen(){
        navHostController.navigate(Screen.Doc_Login.route){
            popUpTo(Screen.Doc_Login.route)
        }
    }

    suspend fun Add_Image_database(uri: Uri, modal: Doc_Signup_Modal, location:String) : String{
        store = FirebaseFirestore.getInstance()

        storageReference = FirebaseStorage.getInstance().reference
        val image_ref = storageReference
            .child("${location}/"+modal.full_name+"_"+modal.hospital_name)
        val Uri = image_ref
            .putFile(uri)
            .await()
            .storage.downloadUrl
            .await().toString()
        return Uri
    }

    suspend fun Signup_Button_Click(docSignupModal: Doc_Signup_Modal,uri: Uri){

        var flag = false
        store = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(docSignupModal.email,docSignupModal.password).
        addOnCompleteListener { task->
            if(task.isSuccessful){
                Log.d("tag","successful")
                Toast.makeText(context,"Signed up successfully",Toast.LENGTH_LONG).show()
                val uid = auth.currentUser?.uid.toString()
                auth.signOut()
                viewModelScope.launch {
                    var url = Add_Image_database(uri,docSignupModal,"Doctor_Images")
                    val data = hashMapOf(
                        "name" to docSignupModal.full_name,
                        "phone_no" to docSignupModal.contact_no,
                        "email" to docSignupModal.email,
                        "hospital_name" to docSignupModal.hospital_name,
                        "image_url" to url
                    )
                    store.collection("h-management")
                        .document("hospitals")
                        .update(mapOf( docSignupModal.hospital_name to true))
                        .addOnCompleteListener{task->
                            if(task.isSuccessful){
                                Log.d("tag","hospital name stored in doc")
                            }
                        }
                    store.collection("h-management")
                        .document("user")
                        .collection("doctor")
                        .document(uid).set(data)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Log.d("tag","data added successfull")
                                Navigate_Login_Screen()
                            }
                            else{
                                Log.d("tag","data not added")
                            }
                        }
                }

            }
            else{
                Log.d("tag","failed")
                Toast.makeText(context,"Sign up failed",Toast.LENGTH_LONG).show()
            }

        }
    }








}
