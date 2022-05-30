package com.example.jederv1.adapater

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jederv1.databinding.ItemsCardBinding
import com.example.jederv1.entity.FoodModel


class HistoryAdapter :
    RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {
    private lateinit var binding: ItemsCardBinding
    private lateinit var onItemClickCallback: OnItemClickCallback

    private var listFood = ArrayList<FoodModel>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listFood.clear()
            }
            this.listFood.addAll(listNotes)
        }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    interface OnItemClickCallback {
        fun onItemClicked(selectedFoodModel: FoodModel, position: Int?)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history = listFood[position]
        with(binding){
            foodname.text = history.name
        }
    }

    override fun getItemCount(): Int = this.listFood.size
}

