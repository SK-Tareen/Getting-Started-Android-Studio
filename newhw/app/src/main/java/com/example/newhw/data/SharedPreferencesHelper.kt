package com.example.newhw.data

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri

object SharedPreferencesHelper {
    fun saveUserData(context: Context, name: String, city: String, imageUri: Uri?) {
        val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("name", name)
            putString("city", city)
            putString("imageUri", imageUri?.toString())
            apply()
        }
    }

    fun loadUserData(context: Context, onLoad: (String?, String?, Uri?) -> Unit) {
        val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", null)
        val city = sharedPreferences.getString("city", null)
        val imageUri = sharedPreferences.getString("imageUri", null)?.let { Uri.parse(it) }
        onLoad(name, city, imageUri)
    }
}
