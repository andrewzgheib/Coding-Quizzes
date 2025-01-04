package com.example.codingquizzes.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object SaveSharedPreference {
    private const val PREF_STAY_LOGGED_IN = "stay_logged_in"

    private fun getSharedPreferences(ctx: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun setStayLoggedIn(ctx: Context, stayLoggedIn: Boolean) {
        val editor = getSharedPreferences(ctx).edit()
        editor.putBoolean(PREF_STAY_LOGGED_IN, stayLoggedIn)
        editor.apply()
    }

    fun isStayLoggedIn(ctx: Context): Boolean {
        return getSharedPreferences(ctx).getBoolean(PREF_STAY_LOGGED_IN, false)
    }

    /*fun clearPreference(ctx: Context) {
        val editor = getSharedPreferences(ctx).edit()
        editor.remove(PREF_USER_NAME)
        editor.apply()
    }*/
}
