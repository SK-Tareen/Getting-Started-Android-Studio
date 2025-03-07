package com.example.newhw.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object FileUtils {

    // Utility function to save an image from a URI to internal storage
    fun saveImageToInternalStorage(context: Context, uri: Uri): Uri? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val file = File(context.filesDir, "saved_image.jpg")
            val outputStream = FileOutputStream(file)

            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()

            return Uri.fromFile(file)
        } catch (e: IOException) {
            Log.e("FileUtils", "Error saving image: ${e.message}")
        }
        return null
    }
}
