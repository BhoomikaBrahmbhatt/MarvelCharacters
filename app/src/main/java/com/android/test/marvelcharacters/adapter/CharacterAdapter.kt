package com.android.test.marvelcharacters.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.test.marvelcharacters.database.Characters
import com.android.test.marvelcharacters.databinding.AdapterCharactersBinding
import com.bumptech.glide.Glide

class CharacterAdapter : RecyclerView.Adapter<MainViewHolder>() {
    var characterList = mutableListOf<Characters>()
    lateinit var mcontext: Context
    @SuppressLint("NotifyDataSetChanged")
    fun setChapterList(characterList: List<Characters>) {
        this.characterList = characterList.toMutableList()
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun appendList(characterList: List<Characters>){
        this.characterList.addAll(characterList.toMutableList())
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        mcontext=parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCharactersBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val chapter = characterList[position]
        val name=chapter.name
        holder.binding.name.text = "$name"
        val marvelUrl=chapter.thumbnail
        Glide.with(holder.itemView.context).load(marvelUrl).into(
            holder.binding.imageview
        )
    }
    override fun getItemCount(): Int {
        return characterList.size
    }
}
class MainViewHolder(val binding: AdapterCharactersBinding) : RecyclerView.ViewHolder(binding.root) {
}