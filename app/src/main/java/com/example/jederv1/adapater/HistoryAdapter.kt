package com.example.jederv1.adapater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jederv1.databinding.ItemsCardBinding


class HistoryAdapter(private val dummy: List<DataDummy>) :
    RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {
    private lateinit var binding: ItemsCardBinding
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    interface OnItemClickCallback {
        fun onItemClicked(data: DataDummy)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history = dummy[position]
        with(binding) {
            foodname.text = history.name
            dateofpic.text = history.date
            Glide.with(binding.root.context)
                .load(history.pic)
                .circleCrop()
                .into(foodpic)
            root.setOnClickListener {
                onItemClickCallback.onItemClicked(history)
            }
        }
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(dummy[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = dummy.size
}

data class DataDummy(
    val name: String,
    val date: String,
    val pic: Int
)
