package com.example.jederv1.history

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jederv1.R
import com.example.jederv1.adapater.HistoryAdapter
import com.example.jederv1.databinding.ActivityHistoryBinding
import com.example.jederv1.details.DetailActivity
import java.util.*

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterHistory = HistoryAdapter()
//        adapterHistory.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: DataDummy) {
//                val intent = Intent(this@HistoryActivity, DetailActivity::class.java)
//                startActivity(intent)
//            }
//        })
//private fun getCurrentDate(): String {
//    val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
//    val date = Date()
//    return dateFormat.format(date)
//}
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = adapterHistory
        }



    }
}