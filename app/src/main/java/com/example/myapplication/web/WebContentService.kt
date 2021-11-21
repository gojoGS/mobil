package com.example.myapplication.web

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface WebContentService {
    @GET("/qod")
    fun getQuote(): Call<ResponseBody>
}