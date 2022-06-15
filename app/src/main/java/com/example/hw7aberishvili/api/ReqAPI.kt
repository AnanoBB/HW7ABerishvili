package com.example.hw7aberishvili.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ReqAPI {

    @GET("users?page=2")
    fun getUsers(): Call<PageModel>

    @POST("users")
    fun createUser(@Body userData: CreateUserModel): Call<CreatedUserModel>

}