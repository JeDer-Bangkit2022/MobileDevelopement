package com.example.jederv1.history

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jederv1.MainActivity
import com.example.jederv1.R
import com.example.jederv1.adapater.DataDummy
import com.example.jederv1.adapater.HistoryAdapter
import com.example.jederv1.databinding.ActivityHistoryBinding
import com.example.jederv1.upload.CameraUpload
import com.example.jederv1.upload.GalleryUpload
import com.google.android.material.bottomnavigation.BottomNavigationView

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listDummy = listOf(
            DataDummy("Rendang"),
            DataDummy("Kue Putu"),
            DataDummy("Kue Satu"),
            DataDummy("Tempe Goreng"),
            DataDummy("Opor"),
        )

        val adapterHistory = HistoryAdapter(listDummy)
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = adapterHistory
        }
    }
}