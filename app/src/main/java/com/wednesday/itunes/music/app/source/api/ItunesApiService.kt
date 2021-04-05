package com.wednesday.itunes.music.app.source.api

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ItunesApiService {

    @POST("search?media=music")
    fun searchMusic(
        @Query("term") keyword: String,
        @Query("limit") limit: Int
    ): Call<ItuneResponse>

    @POST("search?media=music")
    fun searchMusic(
        @Query("term") keyword: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<ItuneResponse>
}