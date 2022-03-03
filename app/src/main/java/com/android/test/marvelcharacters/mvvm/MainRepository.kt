package com.android.test.marvelcharacters.mvvm


class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllChapters(api_key: String,ts: Int,hash: String,
    limit: Int,offset: Int) = retrofitService.getAllMarvels(api_key,ts,hash,limit,offset)
}