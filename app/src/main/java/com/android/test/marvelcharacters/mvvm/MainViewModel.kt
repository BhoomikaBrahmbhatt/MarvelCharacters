package com.android.test.marvelcharacters.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.test.marvelcharacters.database.Characters
import com.android.test.marvelcharacters.database.CharactersDao
import com.android.test.marvelcharacters.utils.Utils
import com.android.test.marvelcharacters.utils.Utils.then
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    val chaptersList = MutableLiveData<List<Characters>>()
    val errorMessage = MutableLiveData<String>()
    val characterUser: MutableList<Characters> = mutableListOf<Characters>()

    fun getAllCharacters(api_key:String,ts:Int,hash:String,limit:Int,offset:Int,charDao:CharactersDao) {
        val response = repository.getAllChapters(api_key,ts,hash,limit,offset)
        response.enqueue(object : Callback<MarvelCharacterData> {
            override fun onResponse(call: Call<MarvelCharacterData>, response: Response<MarvelCharacterData>) {

                CoroutineScope(Dispatchers.IO).launch {

                    val chapters: List<Results> = response.body()!!.data!!.results
                    val itr = chapters.listIterator()    // or, use `iterator()`
                    Utils.limitCharacters = (( Utils.limitCharacters == 0) then response.body()!!.data!!.total!! or  Utils.limitCharacters) as Int

                    while (itr.hasNext()) {
                       val result: Results = itr.next()

                        val thumb = result.thumbnail!!.path + "." + result.thumbnail!!.extension
                        val id=result.id
val name =   result.name
                        val characters=Characters(0,
                            id,
                            name,
                            thumb)
                        charDao.insertAll(
                            characters
                        )
                        characterUser.add(characters)
                    }
                    chaptersList.postValue(characterUser)

                }

            }
            override fun onFailure(call: Call<MarvelCharacterData>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}