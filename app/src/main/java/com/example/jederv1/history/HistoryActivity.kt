package com.example.jederv1.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jederv1.adapater.HistoryAdapter
import com.example.jederv1.api.ApiConfig
import com.example.jederv1.api.HistoryResponse
import com.example.jederv1.api.ResponData
import com.example.jederv1.databinding.ActivityHistoryBinding
import com.example.jederv1.details.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var responData: ArrayList<ResponData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupData()
    }

    private fun setupData() {
        val bundle = intent.extras
        val token = bundle?.getString("token")
        val histories = ApiConfig().getApiService().fetchHistories("Bearer $token")
        histories.enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                val res = response.body()
                if (response.isSuccessful) {

                    Log.d("Berhasil", response.message())
                    if (res != null) {
                        Log.d("LALALALA", res.toString())
                        responData = res.responData
                        val adapterHistory = HistoryAdapter(responData)
                        adapterHistory.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: ResponData) {
                                Intent(
                                    this@HistoryActivity,
                                    DetailActivity::class.java
                                ).also {
                                    it.apply {
                                        val userdata = Bundle()
                                        userdata.putString("id", data.id)
                                        putExtras(userdata)
                                    }
                                    startActivity(it)
                                }
                            }
                        })
                        binding.rvHistory.apply {
                            layoutManager = LinearLayoutManager(this@HistoryActivity)
                            adapter = adapterHistory
                        }
                    }
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Toast.makeText(
                    this@HistoryActivity,
                    "Gagal instance Retrofit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
