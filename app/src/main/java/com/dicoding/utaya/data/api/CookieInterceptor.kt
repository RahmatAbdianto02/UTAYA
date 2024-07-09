package com.dicoding.utaya.data.api

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class CookieInterceptor(private val context: Context) : Interceptor {
    companion object {
        private const val PREFS_NAME = "PREF_COOKIES"
        private const val COOKIE_KEY = "COOKIE_KEY"

        fun getCookies(context: Context): Set<String>? {
            val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getStringSet(COOKIE_KEY, null)
        }

        fun saveCookies(context: Context, cookies: List<String>) {
            val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putStringSet(COOKIE_KEY, cookies.toSet())
            editor.apply()
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val cookies = getCookies(context)
        Log.d("CookieInterceptor", "Cookies sent: $cookies")
        val newRequest = if (cookies != null) {
            request.newBuilder()
                .header("Cookie", cookies.joinToString("; "))
                .build()
        } else {
            request
        }
        val response = chain.proceed(newRequest)
        if (response.headers("Set-Cookie").isNotEmpty()) {
            saveCookies(context, response.headers("Set-Cookie"))
        }
        return response
    }
}

