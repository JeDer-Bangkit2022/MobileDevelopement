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
            "\n\nBahan:\n" +
                    "- 1 ekor ayam kampung, potong 4 bagian\n" +
                    "- 4 butir telur rebus\n" +
                    "- 1 batang serai, memarkan\n" +
                    "- 3 cm lengkuas, memarkan\n" +
                    "- 1 lbr daun salam\n" +
                    "- 2 lbr daun jeruk purut\n" +
                    "- 1 sdt garam\n" +
                    "- 1 sdt gula pasir\n" +
                    "- 500 ml santan kental\n" +
                    "- 500 ml air\n" +
                    "- 3 sdm minyak goreng untuk menumis\n" +
                    "Bumbu halus:\n" +
                    "- 8 butir bawang merah\n" +
                    "- 3 siung bawang putih\n" +
                    "- 4 buah kemiri sangrai\n" +
                    "- 1 sdt ketumbar bubuk\n" +
                    "- 1/2 sdt jintan bubuk\n" +
                    "- 1/2 sdt merica bubuk\n" +
                    "- 2 cm jahe\n" +
                    "Cara membuat:\n" +
                    "1. Lumuri ayam dengan air perasan jeruk nipis dan 1 sdt garam, diamkan 15 menit, lalu cuci bersih.\n" +
                    "2. Tumis bumbu halus, serai, lengkuas, daun salam dan daun jeruk purut sampai harum, masukkan ayam. Setelah kaku dan berubah warna, tuang air, masak sampai ayam empuk.\n" +
                    "3. Setelah air berkurang, masukkan santan dan telur rebus, masak sambil sesekali diaduk. Masak sampai kuah mendidih kembali dan bumbu meresap.\n" +
                    "4. Angkat. Sajikan dengan taburan bawang goreng.\n\n"
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