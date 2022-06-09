package com.example.jederv1.details

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.jederv1.BuildConfig
import com.example.jederv1.MainActivity
import com.example.jederv1.R
import com.example.jederv1.api.ApiConfig
import com.example.jederv1.api.ResponseDelete
import com.example.jederv1.history.HistoryActivity
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : YouTubeBaseActivity() {
    private val apikey = BuildConfig.API_KEY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle = intent.extras
        val ytCode = bundle?.getString("ytCode").toString()
        val result = bundle?.getString("result").toString()
        val recipes = bundle?.getString("recipe").toString()
        val id = bundle?.getString("id").toString()
        val token = bundle?.getString("token").toString()

        Log.d("codecode3", bundle.toString())
        Log.d("codecode", ytCode)
        Log.d("codecode1", result)
        Log.d("codecode2", recipes)


        val judul = findViewById<TextView>(R.id.textViewjudul)
        val recipe = findViewById<TextView>(R.id.recipedetail)
        val ytPlayer = findViewById<YouTubePlayerView>(R.id.ytPlayer)
        val home = findViewById<Button>(R.id.buttonhome)

        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        recipe.text = recipes
        recipe.movementMethod = ScrollingMovementMethod()
        judul.text = result

        delete(token,id)

        ytPlayer.initialize(apikey, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                player?.loadVideo(ytCode)
                player?.play()
            }

            // Inside onInitializationFailure
            // implement the failure functionality
            // Here we will show toast
            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@DetailActivity, "Video player Failed", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun delete(token: String, id: String) {
        val buttondel = findViewById<Button>(R.id.buttondel)
        buttondel.setOnClickListener {
            showLoading(true)
            val deleteId = ApiConfig().getApiService().deletebyId("Bearer $token", id)
            deleteId.enqueue(object : Callback<ResponseDelete>{
                override fun onResponse(
                    call: Call<ResponseDelete>,
                    response: Response<ResponseDelete>
                ) {
                    showLoading(false)
                    if (response.isSuccessful && response.body() != null){
                        Toast.makeText(this@DetailActivity, "Delete Successfull", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@DetailActivity,MainActivity::class.java)
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<ResponseDelete>, t: Throwable) {
                    Toast.makeText(this@DetailActivity, "Delete Unsuccessfull", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }

    }
    private fun showLoading(state: Boolean) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBarDetail)
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
        }
    }

}