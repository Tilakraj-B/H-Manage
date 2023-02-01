package com.example.h_management.main_screen.doc_main_screen.new_patient

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.h_management.navigation.Screen
import com.example.h_management.utils.getImageUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.tasks.await


class New_Patient_Vm(val navHostController: NavHostController,val context: Context) : ViewModel() {

    lateinit var storageReference: StorageReference
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firebaseauth: FirebaseAuth

     fun Navigate_Doc_Main_Screen(){
         navHostController.navigate(Screen.Doc_Main_Screen.route){
             popUpToRoute
         }
     }

    suspend fun Add_Image_database(uri:Uri,modal:New_Patient_Modal,location:String) : String{
        firebaseFirestore = FirebaseFirestore.getInstance()

        storageReference = FirebaseStorage.getInstance().reference
        val image_ref = storageReference
            .child("${location}/"+modal.name+"_"+modal.age+"_"+modal.guarduian_name)
        val Uri = image_ref
            .putFile(uri)
            .await()
            .storage.downloadUrl
            .await().toString()
        return Uri
    }

    suspend fun Get_hospital_name():String?{
        Log.d("tag","Get_hospital_name invoked")
        firebaseauth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        val uid = firebaseauth.currentUser?.uid
        val doc_ref = firebaseFirestore
            .collection("h-management")
            .document("user")
            .collection("doctor")
            .document(uid.toString())

        val hospital_name = doc_ref.get().await().data?.get("hospital_name")?.toString()
        Log.d("tag","Hospital Name --> ${hospital_name}")
        return hospital_name
    }


    suspend fun Add_Patient_database(modal: New_Patient_Modal, data:HashMap<String,String>){
         firebaseauth = FirebaseAuth.getInstance()
         val uid = firebaseauth.currentUser!!.uid
        val hospital_name = Get_hospital_name()
        if(hospital_name != null){
            val doc_ref = firebaseFirestore
                .collection("h-management")
                .document("hospitals")
                .collection(hospital_name)
                .document(id_generator(modal))

            doc_ref.set(data).addOnCompleteListener { task->
                if(task.isSuccessful){
                    Log.d("tag","Patient information is uploaded in database")
                    Navigate_Doc_Main_Screen()
                }
                else{
                    Log.d("tag","Patient information is uploaded in database")
                }
            }
        }
     }



     suspend fun Add_Patient(uri: Uri,modal: New_Patient_Modal){

        val url = Add_Image_database(uri,modal,"Patient_Images")
        Log.d("tag","Url --> $url")
         val qr_bitmap = get_qr_bitmap(id_generator(modal)+"_qr")
         val qr_uri = getImageUri(context,qr_bitmap)
         val qr_url = qr_uri?.let { Add_Image_database(it,modal,"Patient_QR_Images") }.toString()
         val hospital_name = Get_hospital_name()


         if(hospital_name != null){
             val data = hashMapOf(
                 "Patient_name" to modal.name,
                 "Patient_age" to modal.age,
                 "Patient_guardian_name" to modal.guarduian_name,
                 "Patient_contact_no" to modal.contact_no,
                 "Patient_email" to modal.email,
                 "Patient_blood_group" to modal.blood_group,
                 "Patient_image_url" to url,
                 "Patient_id" to id_generator(modal),
                 "hospital_name" to Get_hospital_name()!!,
                 "Patient_qr" to qr_url
             )
             Add_Patient_database(modal,data)
         }
     }

    fun id_generator(modal: New_Patient_Modal) : String{
        var id = ""
        var count = 0
        for(i in modal.name.indices ){
            id+=modal.name[i]
            count++
            if(count == 3){
                break
            }
        }
        id+="_${modal.age}"
        return id
    }

    fun get_qr_bitmap(value :String) : Bitmap{
        val qrCodeBitmap = BarcodeEncoder().encodeBitmap(value, BarcodeFormat.QR_CODE, 400, 400)
        return qrCodeBitmap
    }




}

