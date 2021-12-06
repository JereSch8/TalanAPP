package com.jereschneider.test_talana.repository

import android.content.Context
import android.preference.PreferenceManager

class RepositoryPreferences(context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = prefs.edit()

    private fun setDataString(key: String, value : String){
        editor.putString(key, value)
        editor.apply()
    }

    private fun getDataString(key: String): String {
        return prefs.getString(key, "none").toString()
    }

    private fun setDataBoolean(key: String, value : Boolean){
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getDataBoolean(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }

    fun saveApiKey(apiKey : String){
        setDataString("api-key", apiKey)
    }
    fun getApiKey() = getDataString("api-key")

    fun saveLoginKeep(b : Boolean){
        setDataBoolean("keep-login", b)
    }
    fun isLoginKeep() = getDataBoolean("keep-login")

}