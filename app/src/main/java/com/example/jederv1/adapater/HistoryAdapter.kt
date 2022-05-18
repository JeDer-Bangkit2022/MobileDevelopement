package com.example.jederv1.adapater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jederv1.databinding.ItemsCardBinding


class HistoryAdapter(private val dummy: List<DataDummy>) :
    RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {
    private lateinit var binding: ItemsCardBinding

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history = dummy[position]
        with(binding) {
            foodname.text = history.name
        }
    }

    override fun getItemCount(): Int = dummy.size
}

data class DataDummy(
    val name: String
    )
