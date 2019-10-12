package com.mghtest.reminderwithlocation.database.prefs

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(val context: Context) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE)

    fun saveString(KEY_NAME:String, value:String){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }

    fun getString(KEY_NAME: String):String?{
        return sharedPreferences.getString(KEY_NAME, null)
    }

    fun clear(KEY_NAME:String){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(KEY_NAME)
        editor.commit()
    }
}