package com.practice.task.network

import com.practice.task.models.RegisterUserResponse
import com.practice.task.models.RestaurantsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface API {

    @GET("get-homepage-data")
    suspend fun getRestaurants(@Header("Authorization") token: String): RestaurantsResponse

    @Multipart
    @POST("register-user")
    suspend fun registerUser(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("password") password: RequestBody,
        @Part("confirm_password") confirm_password: RequestBody,
        @Part image: MultipartBody.Part
    ) : RegisterUserResponse

}