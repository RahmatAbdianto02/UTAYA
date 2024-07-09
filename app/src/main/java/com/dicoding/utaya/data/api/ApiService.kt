package com.dicoding.utaya.data.api

import com.dicoding.utaya.data.response.camera.ResponsePredict
import com.dicoding.utaya.data.response.changePw.ResponseChangePw
import com.dicoding.utaya.data.response.history.ResponseHistory
import com.dicoding.utaya.data.response.login.ResponseLogin
import com.dicoding.utaya.data.response.produk.TestResponseProdukItem
import com.dicoding.utaya.data.response.register.ResponseRegister
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface ApiService {

    @FormUrlEncoded
    @POST("users")
    suspend fun postRegister(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String,
    ) : ResponseRegister

    @FormUrlEncoded
    @POST("login")
    suspend fun postLogin(
        @Field("username") username: String,
        @Field("password") password: String,
    ) : ResponseLogin

    @FormUrlEncoded
    @PUT("users")
    suspend fun putPassword(
        @Header("Authorization") token: String,
        @Field("password") password: String,
        @Field("newPassword") newPassword: String,
        @Field("confirmNewPassword") confirmNewPassword: String,
    ) : ResponseChangePw

    @GET("skintype")
    suspend fun getProduk(
        @Header("Authorization") token: String
    ) : List<TestResponseProdukItem>


    @GET("history")
    suspend fun getHistory(
        @Header("Authorization") token: String
    ) : ResponseHistory


    @Multipart
    @POST("predict")
    suspend fun postSkinPredict(
        @Part file: MultipartBody.Part
    ) : ResponsePredict
}
