package com.android.test.marvelcharacters.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    val chaptersList = MutableLiveData<MarvelCharacterData>()
    val errorMessage = MutableLiveData<String>()

    fun getAllCharacters(api_key:String,ts:Int,hash:String,limit:Int,offset:Int) {
        val response = repository.getAllChapters(api_key,ts,hash,limit,offset)
        response.enqueue(object : Callback<MarvelCharacterData> {
            override fun onResponse(call: Call<MarvelCharacterData>, response: Response<MarvelCharacterData>) {
                chaptersList.postValue(response.body())
            }
            override fun onFailure(call: Call<MarvelCharacterData>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}