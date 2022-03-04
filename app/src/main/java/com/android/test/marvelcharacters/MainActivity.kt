package com.android.test.marvelcharacters

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.test.marvelcharacters.adapter.CharacterAdapter
import com.android.test.marvelcharacters.database.AppDatabase
import com.android.test.marvelcharacters.database.CharactersDao
import com.android.test.marvelcharacters.databinding.ActivityMainBinding
import com.android.test.marvelcharacters.mvvm.MainRepository
import com.android.test.marvelcharacters.mvvm.MainViewModel
import com.android.test.marvelcharacters.mvvm.MyViewModelFactory
import com.android.test.marvelcharacters.mvvm.RetrofitService
import com.android.test.marvelcharacters.utils.Utils

class MainActivity : AppCompatActivity(){
    private var binding: ActivityMainBinding? = null
    private val limit=10
    var offset=0
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private val adapter = CharacterAdapter()
private var loading=false
private lateinit var characterDao: CharactersDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        viewModel = ViewModelProvider(this,
            MyViewModelFactory(MainRepository(retrofitService))).
            get(MainViewModel::class.java)

        characterDao = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build().charactersDao()


        binding!!.recyclerview.layoutManager = GridLayoutManager(applicationContext,2)

        binding!!.recyclerview.adapter = adapter
        viewModel.chaptersList.observe(this, Observer {
            adapter.setChapterList(it)
            loading=false
        })
        viewModel.errorMessage.observe(this, Observer {
            loading=false
            Toast.makeText(this@MainActivity,getString(R.string.error),Toast.LENGTH_LONG).show()

        })
        callCharacters(true)
        binding!!.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1) && !loading) {
                    // LOAD MORE
                    loading=true
                    offset=offset.plus(limit)
                    if(offset<Utils.limitCharacters)
                        callCharacters(false)
                    else {
                        offset=offset.minus(limit)
                        loading = false
                        Toast.makeText(this@MainActivity,getString(R.string.data_load),Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun callCharacters(firstCall:Boolean) {
        if(Utils.isOnline(this)) {

           /* if(firstCall)
            {

                CoroutineScope(Dispatchers.IO).launch {
                    val characters: List<User> = userDao.getAll()
                    adapter.appendList(characters)
                    offset=characters.size
                }


            }*/

           viewModel.getAllCharacters(
                Utils.value_apikey,
                Utils.value_ts,
                Utils.value_hash,
                limit,
                offset,
                characterDao
            )
        }else{
            if(offset>0)
            offset=offset.minus(limit)

            loading = false

            //val users: List<User> = userDao.getAll()

            Toast.makeText(this@MainActivity,getString(R.string.network_error),Toast.LENGTH_LONG).show()
        }

    }

}