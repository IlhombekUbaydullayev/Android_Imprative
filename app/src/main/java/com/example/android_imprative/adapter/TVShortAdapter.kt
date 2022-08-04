package com.example.android_imprative.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_imprative.databinding.ItemTvShortBinding
import com.example.android_imprative.databinding.ItemTvShowBinding
import com.example.android_imprative.model.TVShow

class TVShortAdapter : ListAdapter<String,TVShortAdapter.TvShowHolder>(ITEM_DIF) {

    var onClick: ((String, ImageView) -> Unit)? = null
    companion object{
        val ITEM_DIF = object : DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class TvShowHolder(private val binding: ItemTvShortBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(){
            val tvShort = getItem(adapterPosition)
            with(binding){
                Glide.with(root).load(tvShort).into(ivShort)

                ivShort.setOnClickListener {
                    // Save TVShow into Room


                    // Call Details Activity
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowHolder {
        return TvShowHolder(ItemTvShortBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TvShowHolder, position: Int) {
        holder.bind()
    }
}