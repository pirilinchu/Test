package com.example.test2.services

import com.example.test2.Model.BeerModel
import retrofit2.Call
import retrofit2.http.GET

interface BeerApi {
    @GET("beers")
    fun getBeerList(): Call<List<BeerModel>>
}