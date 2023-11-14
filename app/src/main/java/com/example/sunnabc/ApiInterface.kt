package com.shanu.sunnabc

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @Headers("X-RapidAPI-Key: f72f84b85cmsh7c07925a66b3ef9p1bf960jsn9c3ce9f417e4" ,
            "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com")
    @GET("search")
    fun getData(@Query("q") query: String): Call<MyData>
}