package com.hfmarin.meliapp.network

import com.hfmarin.meliapp.network.model.MeliSearchPageDto
import com.hfmarin.meliapp.network.model.ResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeliService {
    @GET("sites/MLU/search")
    suspend fun search(@Query("q") query: String): MeliSearchPageDto

    @GET("items/{id}")
    suspend fun item(@Path("id") id: String): ResultDto
}