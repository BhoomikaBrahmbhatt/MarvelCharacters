package com.android.test.marvelcharacters.mvvm

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {


    @GET("/v1/public/characters")
    fun getAllMarvels(@Query("apikey") api_key: String,
    @Query("ts") ts: Int,
    @Query("hash") hash:String,
    @Query("limit") limit:Int,
    @Query("offset") offset:Int): Call<MarvelCharacterData>


    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://gateway.marvel.com:443/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}