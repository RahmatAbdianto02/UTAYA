package com.dicoding.utaya.data.api

import android.content.Context
import com.dicoding.utaya.data.utils.Preference
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private fun getInterceptor(context: Context, token: String?): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val builder = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cookieJar(MyCookieJar())
                .addInterceptor(CookieInterceptor(context))

            if (!token.isNullOrEmpty()) {
                builder.addInterceptor(AuthInterceptor(token))
            }

            return builder.build()
        }

        fun getApiService(context: Context): ApiService {
            val sharedPref = Preference.initPref(context)
            val token = sharedPref.getString("token", null)

            val retrofit = Retrofit.Builder()
                .baseUrl("https://backend-utaya-n4d5yc5fnq-et.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor(context, token))
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}

