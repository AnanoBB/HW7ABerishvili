package com.example.hw7aberishvili.api

import com.google.gson.annotations.SerializedName

class UserModel {
    lateinit var id: Integer

    lateinit var email: String

    @SerializedName("first_name")
    lateinit var firstName: String

    @SerializedName("last_name")
    lateinit var lastName: String

    lateinit var avatar: String
}