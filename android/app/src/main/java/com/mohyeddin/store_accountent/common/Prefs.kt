package com.mohyeddin.store_accountent.common

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context){
    private val prefs: SharedPreferences = context.getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
    fun writeToken(token: String){
        with(prefs.edit()){
            putString("token",token)
            apply()
        }
    }

    fun writeIsSigned(isSigned: Boolean){
        with(prefs.edit()){
            putBoolean("isSigned",isSigned)
            apply()
        }
    }

    fun writeFilterTradeDate(date: String){
        with(prefs.edit()){
            putString("filterDate",date)
            apply()
        }
    }

    fun writeFilterTradeType(type: String){
        with(prefs.edit()){
            putString("filterType",type)
            apply()
        }
    }

    fun readFilterTradeType() : String?{
        return prefs.getString("filterType","")
    }

    fun readFilterTradeDate() : String?{
        return prefs.getString("filterDate","")
    }

    fun writeIsConfigured(isConf: Boolean){
        with(prefs.edit()){
            putBoolean("isConf",isConf)
            apply()
        }
    }

    fun getIsSigned() : Boolean {
        return prefs.getBoolean("isSigned",false)
    }

    fun getToken(): String? {
        return prefs.getString("token", "")
    }

    fun getIsConfigured(): Boolean {
        return prefs.getBoolean("isConf",false)
    }
}