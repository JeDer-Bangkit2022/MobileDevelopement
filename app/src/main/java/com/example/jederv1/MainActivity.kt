package com.example.jederv1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jederv1.databinding.ActivityMainBinding
import com.example.jederv1.history.HistoryActivity
import com.example.jederv1.upload.CameraUpload
import com.example.jederv1.upload.GalleryUpload

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
    }

    private fun setupButton(){
        binding.imageButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        binding.imageButton2.setOnClickListener {
            val intent = Intent(this, GalleryUpload::class.java)
            startActivity(intent)
        }
        binding.imageButton3.setOnClickListener {
            val intent = Intent(this,CameraUpload::class.java)
            startActivity(intent)
        }
    }
}