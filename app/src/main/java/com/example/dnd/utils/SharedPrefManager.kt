package com.example.dnd.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPrefManager(val context: Context) {
    var USER_STATUS = "USER_STATUS"
    var ACTIVATE = "ACTIVATE"
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "MySharedPreference",
        MODE_PRIVATE
    )

    fun getUserStatus(): String {
        return sharedPreferences.getString(USER_STATUS, "")!!
    }

    fun getStatusActivate(): Boolean {
        return sharedPreferences.getBoolean(ACTIVATE, false)!!
    }



    fun setstatusActivate(bool : Boolean){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(ACTIVATE, bool)
        editor.apply()
    }

    fun setUserStatus(status: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(USER_STATUS, status)
        editor.apply()
    }
}