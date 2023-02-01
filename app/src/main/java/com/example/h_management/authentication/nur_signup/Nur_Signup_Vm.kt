package com.example.h_management.authentication.doc_signup

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.h_management.authentication.nur_signup.Nur_Signup_Modal
import com.example.h_management.main_screen.doc_main_screen.new_patient.New_Patient_Modal
import com.example.h_management.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class Nur_Signup_Vm(val navHostController: NavHostController, val context: Context):ViewModel() {


    private lateinit var auth: FirebaseAuth
    private lateinit var store : FirebaseFirestore
    private lateinit var storageReference: StorageReference

    fun Navigate_Nur_Login_Screen(){
        navHostController.navigate(Screen.Nur_Login.route){
            popUpTo(Screen.Nur_Login.route)
        }
    }

    suspend fun Signup_Button_Click(nurSignupModal: Nur_Signup_Modal,uri: Uri){
        auth = FirebaseAuth.getInstance()
        store = FirebaseFirestore.getInstance()
        store.collection("h-management")
            .document("hospitals")
            .get()
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    val x = task.result.contains(nurSignupModal.hospital_name)
                    if(x == true){
                        Sign_Up(nurSignupModal,uri)
                    }
                    else{
                        Log.d("tag","hospital does not exist")
                        Toast.makeText(context,"Check hospital Name",Toast.LENGTH_LONG).show()
                    }
                }
            }

    }

    suspend fun Add_Image_database(uri: Uri, modal: Nur_Signup_Modal, location:String) : String{
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


    fun Sign_Up(nurSignupModal: Nur_Signup_Modal,uri: Uri) {
        auth.createUserWithEmailAndPassword(
            nurSignupModal.contact_no+"@nurse.in",
            nurSignupModal.password
        )
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    Log.d("tag","acount created successfully")
                    val uid = task.result.user?.uid
                    viewModelScope.launch {
                        val url = Add_Image_database(uri,nurSignupModal,"Nurse_Images")
                        val data = mapOf(
                            "name" to nurSignupModal.full_name,
                            "email" to nurSignupModal.email,
                            "phone_no" to nurSignupModal.contact_no,
                            "hospital_name" to nurSignupModal.hospital_name,
                            "image_url" to url
                        )

                        store.collection("h-management")
                            .document("user")
                            .collection("nurse")
                            .document(uid.toString())
                            .set(data)
                            .addOnCompleteListener{task->
                                if(task.isSuccessful){
                                    Log.d("tag","nurse data stored successfully")
                                    Navigate_Nur_Login_Screen()
                                }
                            }
                    }
                }
            }
    }
}
