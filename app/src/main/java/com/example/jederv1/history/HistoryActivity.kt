package com.example.jederv1.history

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jederv1.R
import com.example.jederv1.adapater.DataDummy
import com.example.jederv1.adapater.HistoryAdapter
import com.example.jederv1.databinding.ActivityHistoryBinding
import com.example.jederv1.details.DetailActivity

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listDummy = listOf(
            DataDummy("Rendang", "24-12-2020", R.drawable.dummyphotos),
            DataDummy("Kue Putu, ", "1-1-2021", R.drawable.dummyphotos),
            DataDummy("Kue Satu", "2-1-2021", R.drawable.dummyphotos),
            DataDummy("Tempe Goreng", "3-1-2021", R.drawable.dummyphotos),
            DataDummy("Opor", "5-2-2021", R.drawable.dummyphotos),
            DataDummy("Bakso", "24-12-2020", R.drawable.dummyphotos),
            DataDummy("Mie Goreng, ", "1-1-2021", R.drawable.dummyphotos),
            DataDummy("Mie Ayam", "2-1-2021", R.drawable.dummyphotos),
            DataDummy("Sate Padang", "3-1-2021", R.drawable.dummyphotos),
            DataDummy("Nasi Padang", "5-2-2021", R.drawable.dummyphotos),
        ).sortedBy { it.name }


        val adapterHistory = HistoryAdapter(listDummy)
        adapterHistory.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataDummy) {
                val intent = Intent(this@HistoryActivity, DetailActivity::class.java)
                startActivity(intent)
            }
        })
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = adapterHistory
        }



    }
}