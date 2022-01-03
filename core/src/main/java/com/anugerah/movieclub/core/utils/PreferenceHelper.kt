package com.anugerah.movieclub.core.utils

import android.content.Context

object PreferenceHelper {

    private const val spName = "movie_shared_preferences"

    fun saveSetting(context: Context, value: Boolean) {
        val sharedPref = context.getSharedPreferences(spName, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean("is_dark_mode", value)
            apply()
        }
    }

    fun getSetting(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(spName,  Context.MODE_PRIVATE)
        return sharedPref!!.getBoolean("is_dark_mode", false)
    }
}