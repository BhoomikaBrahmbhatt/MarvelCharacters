package com.android.test.marvelcharacters.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.test.marvelcharacters.database.Characters
import com.android.test.marvelcharacters.database.CharactersDao
import com.android.test.marvelcharacters.utils.Utils
import com.android.test.marvelcharacters.utils.Utils.then
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val chaptersList = MutableLiveData<List<Characters>>()
    val errorMessage = MutableLiveData<String>()
    val characterUser: MutableList<Characters> = mutableListOf()

    fun getAllCharacters(
        api_key: String,
        ts: Int,
        hash: String,
        limit: Int,
        offset: Int,
        charDao: CharactersDao
    ) {
        val response = repository.getAllChapters(api_key, ts, hash, limit, offset)
        response.enqueue(object : Callback<MarvelCharacterData> {
            override fun onResponse(
                call: Call<MarvelCharacterData>,
                response: Response<MarvelCharacterData>
            ) {

                CoroutineScope(Dispatchers.IO).launch {
                    val characterData: Data? = response.body()?.data
                    characterData?.also {
                        val chapters: ArrayList<Results> = characterData.results
                        val itr = chapters.listIterator()    // or, use `iterator()`
                        Utils.limitCharacters =
                            ((Utils.limitCharacters == 0) then {
                                characterData.total
                            } or Utils.limitCharacters) as Int

                        while (itr.hasNext()) {
                            val result: Results = itr.next()
                            val thumbnailUtils = result.thumbnail
                            var thumb =""
                            thumbnailUtils?.also {
                                 thumb = thumbnailUtils.path + "." + thumbnailUtils.extension
                            }
                            val id = result.id
                            val name = result.name
                            val characters = Characters(
                                0,
                                id,
                                name,
                                thumb
                            )
                            charDao.insertAll(
                                characters
                            )
                            characterUser.add(characters)
                        }
                        chaptersList.postValue(characterUser)
                    } ?: run{
                        errorMessage.postValue("Null Exist")
                    }
                }
            }

            override fun onFailure(call: Call<MarvelCharacterData>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }


}