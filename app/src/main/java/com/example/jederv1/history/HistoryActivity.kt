package com.example.jederv1.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.jederv1.adapater.HistoryAdapter
import com.example.jederv1.api.ApiConfig
import com.example.jederv1.api.HistoryResponse
import com.example.jederv1.api.ResponData
import com.example.jederv1.api.ResponDatabyID
import com.example.jederv1.databinding.ActivityHistoryBinding
import com.example.jederv1.details.DetailActivity
import com.example.jederv1.entity.UserPreference
import com.example.jederv1.entity.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HistoryActivity : AppCompatActivity(){
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var responData: ArrayList<ResponData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
    }

    private fun setupViewModel() {
//        showLoading(true)
        historyViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[HistoryViewModel::class.java]
        historyViewModel.getToken().observe(this) { tokenClass ->
            setupData(tokenClass.token)
        }
    }

    private fun setupData(token: String) {
        showLoading(true)
        val histories = ApiConfig().getApiService().fetchHistories("Bearer $token")
        histories.enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                val res = response.body()
                if (response.isSuccessful) {
                    showLoading(false)
                    Log.d("Berhasil", response.message())
                    if (res != null) {
                        Log.d("LALALALA", res.toString())
                        responData = res.responData
                        val adapterHistory = HistoryAdapter(responData)
                        adapterHistory.setOnItemClickCallback(object :
                            HistoryAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: ResponData) {
                                val byId =
                                    ApiConfig().getApiService().fetchbyId("Bearer $token", data.id)
                                byId.enqueue(object : Callback<ResponDatabyID> {
                                    override fun onResponse(
                                        call: Call<ResponDatabyID>,
                                        response: Response<ResponDatabyID>
                                    ) {
                                        if (response.isSuccessful) {
                                            Log.d("AHAHAHHA", response.body().toString())
                                            val responseBody = response.body()
                                            if (responseBody != null) {
                                                val result = responseBody.result
                                                val recipe = responseBody.recipe
                                                val ytCode = responseBody.ytCode
                                                val intent = Intent(
                                                    this@HistoryActivity,
                                                    DetailActivity::class.java
                                                )
                                                val userdata = Bundle()
                                                userdata.putString("id", data.id)
                                                userdata.putString("result", result)
                                                userdata.putString("recipe", recipe)
                                                userdata.putString("ytCode", ytCode)
                                                userdata.putString("token", token)
                                                Log.d("AWAWAW", result)
                                                Log.d("AWAWAW1", recipe)
                                                Log.d("AWAWAW2", ytCode)
                                                intent.putExtras(userdata)
                                                startActivity(intent)
                                            }
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<ResponDatabyID>,
                                        t: Throwable
                                    ) {
                                        Toast.makeText(
                                            applicationContext,
                                            t.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                            }
                        })
                        val animator: ItemAnimator? = binding.rvHistory.itemAnimator
                        if (animator is SimpleItemAnimator) {
                            animator.supportsChangeAnimations = false
                            animator.changeDuration = 0
                        }
                        binding.rvHistory.apply {
                            layoutManager = LinearLayoutManager(this@HistoryActivity)
                            adapterHistory.notifyItemChanged(responData.size)
                            adapter = adapterHistory
                        }
                    }
                }

            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Toast.makeText(
                    this@HistoryActivity,
                    "Failed to Instance Retrofit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

}


//private fun getData(token: String) {
////        showLoading(false)
//        val adapter = HistoryPagingAdapter()
//        binding.rvHistory.adapter = adapter.withLoadStateFooter(
//            footer = LoadingStateAdapter {
//                adapter.retry()
//            })
//        lifecycleScope.launch {
//            historyViewModel.getStories(token).collectLatest { data ->
//                adapter.submitData(data)
//                Log.d("DATA", data.toString())
//            }
//        }
//        adapter.setOnItemClickCallback(object : HistoryPagingAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: ResponData) {
//
//
//            }
//        })
//    }
