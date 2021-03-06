package com.android.test.marvelcharacters.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.test.marvelcharacters.database.Characters
import com.android.test.marvelcharacters.database.CharactersDao
import com.android.test.marvelcharacters.utils.Utils
import com.android.test.marvelcharacters.utils.Utils.then
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val mainCharactersList = MutableLiveData<List<Characters>>()
    val errorMessage = MutableLiveData<String>()
    val characterUser: MutableList<Characters> = mutableListOf()

    fun addCharacter(
        charDao: CharactersDao,
        id: Int, name: String, thumb: String
    ) {
        val characters = Characters(
            0,
            id,
            name,
            thumb
        )
        charDao.insertAll(
            characters
        )
    }

    fun getCharactersDB( charDao: CharactersDao){
        val characterDBUser= charDao.getAll().toMutableList()
        mainCharactersList.postValue(characterDBUser)
    }

    fun getAllCharacters(
        api_key: String,
        ts: Int,
        hash: String,
        limit: Int,
        offset: Int,
        charDao: CharactersDao
    ) = viewModelScope.launch {
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
                        characterData.total.also {
                            if (it != null) {
                                Utils.limitCharacters = it
                            }
                        }


                        while (itr.hasNext()) {
                            val result: Results = itr.next()
                            val thumbnailUtils = result.thumbnail
                            var thumb = ""
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
                        mainCharactersList.postValue(characterUser)
                    } ?: run {
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