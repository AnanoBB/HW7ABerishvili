package com.example.hw7aberishvili.api

import com.google.gson.annotations.SerializedName

class PageModel {
    lateinit var page: Integer

    @SerializedName("per_page")
    lateinit var perPage: Integer

    lateinit var total: Integer

    @SerializedName("total_pages")
    lateinit var totalPages: Integer

    lateinit var data: List<UserModel>
}