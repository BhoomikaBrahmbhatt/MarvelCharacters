package com.android.test.marvelcharacters.mvvm

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.android.test.marvelcharacters.database.AppDatabase
import com.android.test.marvelcharacters.database.CharactersDao
import com.android.test.marvelcharacters.utils.Utils
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat


@RunWith(JUnit4::class)
class MainViewModelTest : TestCase() {
    private lateinit var viewModel: MainViewModel
    private lateinit var characterDao: CharactersDao
    private val limit = 30
    var offset = 0

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
                .build()
        val retrofitService = RetrofitService.getInstance()
        characterDao = db.charactersDao()
        viewModel = MainViewModel(MainRepository(retrofitService))
    }

    @Test
    fun testModel() {
        viewModel.addCharacter(characterDao, 999, "TestName", "TestUrl")
        /* viewModel.getAllCharacters(
             Utils.value_apikey,
             Utils.value_ts,
             Utils.value_hash,
             limit,
             offset,characterDao
         )*/
        viewModel.getCharactersDB(characterDao)
        val result = viewModel.mainCharactersList.getOrAwaitValue().find {
            it.name == "TestName" && it.thumbnail == "TestUrl"
        }

        assertThat(result != null, equalTo(true))


    }
}