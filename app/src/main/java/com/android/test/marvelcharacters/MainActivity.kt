package com.android.test.marvelcharacters

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.test.marvelcharacters.adapter.CharacterAdapter
import com.android.test.marvelcharacters.databinding.ActivityMainBinding
import com.android.test.marvelcharacters.mvvm.MainRepository
import com.android.test.marvelcharacters.mvvm.MainViewModel
import com.android.test.marvelcharacters.mvvm.MyViewModelFactory
import com.android.test.marvelcharacters.mvvm.RetrofitService
import com.android.test.marvelcharacters.utils.Utils
import com.android.test.marvelcharacters.utils.Utils.then

class MainActivity : AppCompatActivity(){
    private var binding: ActivityMainBinding? = null
    private val limit=20
    var offset=0
    private var total=0
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private val adapter = CharacterAdapter()
private var loading=false
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        viewModel = ViewModelProvider(this,
            MyViewModelFactory(MainRepository(retrofitService))).
            get(MainViewModel::class.java)

        binding!!.recyclerview.layoutManager = GridLayoutManager(applicationContext,2)

        binding!!.recyclerview.adapter = adapter
        viewModel.chaptersList.observe(this, Observer {
            if(total>0){
                adapter.appendList(it.data!!.results)
            }
            else
            adapter.setChapterList(it.data!!.results)

            total = ((total == 0) then it.data!!.total!! or total) as Int
            loading=false
        })
        viewModel.errorMessage.observe(this, Observer {
            loading=false
        })
        viewModel.getAllCharacters(Utils.value_apikey,Utils.value_ts,Utils.value_hash,limit,offset)
        binding!!.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1) && !loading) {
                    // LOAD MORE
                    loading=true
                    offset=offset.plus(limit)
                    if(offset<total)
                    viewModel.getAllCharacters(Utils.value_apikey,Utils.value_ts,Utils.value_hash,limit,offset.plus(limit))
                    else {
                        offset=offset.minus(limit)
                        loading = false
                        Toast.makeText(this@MainActivity,"All characters shown!!",Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

}