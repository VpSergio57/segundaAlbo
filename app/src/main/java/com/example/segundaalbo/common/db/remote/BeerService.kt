package com.example.segundaalbo.common.db.remote

import com.example.segundaalbo.common.db.entities.BeerEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface BeerService {
    //@GET("beers?page=1&per_page=20")
    @GET("beers")
    suspend fun getBeers(
        @Query("pages") page: String,
        @Query("per_page") per_page: String
    ):Response<MutableList<BeerEntity>>

}

//interface BeerService {
//
//    @GET("beers?page=1&per_page=20")
//    suspend fun getBeers():Response<MutableList<BeerEntity>>
//
//}