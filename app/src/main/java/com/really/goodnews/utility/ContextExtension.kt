package com.really.goodnews.utility

import android.content.Context

val Context.needRemoteUser: Boolean
    get() {
        val sharedPreferences = this.getSharedPreferences("CACHE", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("NeedRemoteUser", true)
    }

fun Context.setNeedRemoteUser(boolean: Boolean){
    val sharedPreferences = this.getSharedPreferences("CACHE", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("NeedRemoteUser", boolean)
    editor.apply()
}