package com.android.test.marvelcharacters.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.test.marvelcharacters.databinding.AdapterCharactersBinding
import com.android.test.marvelcharacters.mvvm.Results
import com.bumptech.glide.Glide

class CharacterAdapter : RecyclerView.Adapter<MainViewHolder>() {
    var chapters = mutableListOf<Results>()
    lateinit var mcontext: Context
    @SuppressLint("NotifyDataSetChanged")
    fun setChapterList(chapters: List<Results>) {
        this.chapters = chapters.toMutableList()
        notifyDataSetChanged()
    }
    fun appendList(chapters: List<Results>){
        this.chapters.addAll(chapters.toMutableList())
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        mcontext=parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCharactersBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val chapter = chapters[position]
        val name=chapter.name
        //val meaning = chapter.modified
        //val summary = chapter.description
        holder.binding.name.text = "$name"
        //holder.binding.meaning.text=meaning
        //holder.binding.summary.text=summary
        val imgurl=chapter.thumbnail!!.path+"."+chapter.thumbnail!!.extension
        Glide.with(holder.itemView.context).load(imgurl).into(
            holder.binding.imageview
        )
    }
    override fun getItemCount(): Int {
        return chapters.size
    }
}
class MainViewHolder(val binding: AdapterCharactersBinding) : RecyclerView.ViewHolder(binding.root) {
}