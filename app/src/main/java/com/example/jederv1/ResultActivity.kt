package com.example.jederv1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.jederv1.databinding.ActivityResultBinding
import com.example.jederv1.details.DetailActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val result = bundle?.getString("result").toString()
        val resAcc = bundle?.getString("resAcc").toString()
        val imageUrl = bundle?.getString("imageUrl").toString()
        val desc = bundle?.getString("desc")
       Log.d("HASILACC", resAcc)
        Glide.with(binding.root.context)
            .load(imageUrl)
            .into(binding.imagedesc)

        with(binding) {
            descripsi.text = desc
            textViewjudul.text = result
            akurasi.text = "Akurasi : $resAcc"
        }
        binding.button3.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
        }
        binding.next.setOnClickListener {
            val intent = Intent(this@ResultActivity, DetailActivity::class.java)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            startActivity(intent)
        }

    }
}