package com.example.jederv1.details

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import android.widget.Toast
import com.example.jederv1.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class DetailActivity : YouTubeBaseActivity() {
    private val apikey = "APIKEY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val recipe = findViewById<TextView>(R.id.recipedetail)
        val ytPlayer = findViewById<YouTubePlayerView>(R.id.ytPlayer)
        val text =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum at metus dictum, volutpat neque non, dignissim leo. Praesent at neque sed eros scelerisque rhoncus. Fusce eu nunc eu mi posuere posuere a non erat. Duis dignissim odio ex, eu lobortis diam imperdiet vel. Donec eros justo, sagittis congue vulputate id, ultricies non neque. Integer hendrerit lobortis justo, sit amet commodo metus aliquet eget. Vivamus porta posuere orci, eget eleifend tortor consectetur a. Aliquam tortor ipsum, hendrerit non risus ac, eleifend efficitur magna. Donec tincidunt euismod orci, id convallis augue placerat ac. \nVestibulum vitae venenatis dolor, a posuere est. Aenean vitae vestibulum lectus. Fusce nec tellus eleifend metus ultrices vestibulum. Suspendisse sollicitudin, neque et tempor tempor, tellus libero sollicitudin lacus, ullamcorper maximus est urna non nisi. In convallis egestas lacus, vitae tempus nisi consequat ut. Suspendisse potenti. Praesent porta, eros id semper volutpat, tellus augue rhoncus diam, eu varius eros nisi non diam. Phasellus nec sem molestie, lacinia neque id, ultricies lorem. \nMorbi ornare rutrum nibh, quis pharetra dui faucibus quis. Vivamus vel ex lacus. Sed rutrum risus vitae sapien fringilla fermentum. Fusce gravida, lorem eu sagittis mollis, quam quam hendrerit massa, quis placerat dui elit sit amet arcu. Proin bibendum tincidunt est sit amet elementum..Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum at metus dictum, volutpat neque non, dignissim leo. Praesent at neque sed eros scelerisque rhoncus. Fusce eu nunc eu mi posuere posuere a non erat. " +
                    "\nDuis dignissim odio ex, eu lobortis diam imperdiet vel. Donec eros justo, sagittis congue vulputate id, ultricies non neque. Integer hendrerit lobortis justo, sit amet commodo metus aliquet eget. Vivamus porta posuere orci, eget eleifend tortor consectetur a. Aliquam tortor ipsum, hendrerit non risus ac, eleifend efficitur magna. Donec tincidunt euismod orci, id convallis augue placerat ac. Vestibulum vitae venenatis dolor, a posuere est. Aenean vitae vestibulum lectus. Fusce nec tellus eleifend metus ultrices vestibulum. Suspendisse sollicitudin, neque et tempor tempor, tellus libero sollicitudin lacus, ullamcorper maximus est urna non nisi. In convallis egestas lacus, vitae tempus nisi consequat ut. Suspendisse potenti. Praesent porta, eros id semper volutpat, tellus augue rhoncus diam, eu varius eros nisi non diam. Phasellus nec sem molestie, lacinia neque id, ultricies lorem. Morbi ornare rutrum nibh, quis pharetra dui faucibus quis. Vivamus vel ex lacus. Sed rutrum risus vitae sapien fringilla fermentum. Fusce gravida, lorem eu sagittis mollis, quam quam hendrerit massa, quis placerat dui elit sit amet arcu. Proin bibendum tincidunt est sit amet elementum." +
                    "Vivamus vel ex lacus. Sed rutrum risus vitae sapien fringilla fermentum. Fusce gravida, lorem eu sagittis mollis, quam quam hendrerit massa, quis placerat dui elit sit amet arcu. Proin bibendum tincidunt est sit amet elementum..Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum at metus dictum, volutpat neque non, dignissim leo. Praesent at neque sed eros scelerisque rhoncus. Fusce eu nunc eu mi posuere posuere a non erat. \n" +
                    "Vivamus vel ex lacus. Sed rutrum risus vitae sapien fringilla fermentum. Fusce gravida, lorem eu sagittis mollis, quam quam hendrerit massa, quis placerat dui elit sit amet arcu. Proin bibendum tincidunt est sit amet elementum..Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum at metus dictum, volutpat neque non, dignissim leo. Praesent at neque sed eros scelerisque rhoncus. Fusce eu nunc eu mi posuere posuere a non erat. \n" +
                    "Vivamus vel ex lacus. Sed rutrum risus vitae sapien fringilla fermentum. Fusce gravida, lorem eu sagittis mollis, quam quam hendrerit massa, quis placerat dui elit sit amet arcu. Proin bibendum tincidunt est sit amet elementum..Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum at metus dictum, volutpat neque non, dignissim leo. Praesent at neque sed eros scelerisque rhoncus. Fusce eu nunc eu mi posuere posuere a non erat. "
        recipe.text = text
        recipe.movementMethod = ScrollingMovementMethod()
        ytPlayer.initialize(apikey, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                player?.loadVideo("xtjJ9IthuT0")
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
}