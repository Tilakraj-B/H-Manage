package com.example.h_management.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun Get_Uri_From_Bitmap(context: Context, bitmap: Bitmap?): Uri {
    val bytes = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
    return Uri.parse(path.toString())
}
fun getImageUri(inContext: Context, inImage: Bitmap?): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path =
        MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
    return Uri.parse(path)
}


fun getUriFromDrawableResId(context : Context, drawableResId:Int) : Uri {
    val builder = StringBuilder().append(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .append("://")
        .append(context.resources.getResourcePackageName(drawableResId))
        .append("/")
        .append(context.resources.getResourceTypeName(drawableResId))
        .append("/")
        .append(context.resources.getResourceEntryName(drawableResId))
    return Uri.parse(builder.toString())
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmm ss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}