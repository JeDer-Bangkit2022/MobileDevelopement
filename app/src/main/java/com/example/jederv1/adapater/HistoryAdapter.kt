package com.example.jederv1.adapater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jederv1.api.ResponData
import com.example.jederv1.databinding.ItemsCardBinding


class HistoryAdapter(private val listStory: ArrayList<ResponData>) :
    RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {
    private lateinit var binding: ItemsCardBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val stories = listStory[position]
        with(binding) {
            foodname.text = stories.name
            Glide.with(binding.root.context)
                .load(stories.imageUrl)
                .circleCrop()
                .into(foodpic)
            root.setOnClickListener {
                onItemClickCallback.onItemClicked(stories)
            }
        }
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listStory[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResponData)
    }

    override fun getItemCount(): Int = this.listStory.size
}

