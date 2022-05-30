package com.example.jederv1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.jederv1.api.TokenClass
import com.example.jederv1.databinding.ActivityMainBinding
import com.example.jederv1.entity.UserModel
import com.example.jederv1.entity.UserPreference
import com.example.jederv1.entity.ViewModelFactory
import com.example.jederv1.history.HistoryActivity
import com.example.jederv1.upload.CameraUpload
import com.example.jederv1.upload.GalleryUpload
import com.example.jederv1.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var user : UserModel
    private lateinit var tokenClass: TokenClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        Glide.with(this).load(R.drawable.jendelaresep2).into(binding.imageView2)

        setupViewModel()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]
        mainViewModel.getToken().observe(this){tokenuser ->
            this.tokenClass = tokenuser
            setupButton(tokenuser.token)
        }
        mainViewModel.getUser().observe(this) { user ->
            this.user = user
            if (user.isLogin) {
                val name = user.name
                binding.welcomeuser.text = "Welcome $name"
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun setupButton(token: String) {
        val tokenintent = Bundle()
        binding.button2.setOnClickListener {
            mainViewModel.logout()
        }
        binding.imageButton.setOnClickListener {
            tokenintent.putString("token", token)
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtras(tokenintent)
            startActivity(intent)
        }
        binding.imageButton2.setOnClickListener {
            tokenintent.putString("token", token)
            val intent = Intent(this, GalleryUpload::class.java)
            intent.putExtras(tokenintent)
            startActivity(intent)
        }
        binding.imageButton3.setOnClickListener {
            tokenintent.putString("token", token)
            val intent = Intent(this, CameraUpload::class.java)
            intent.putExtras(tokenintent)
            startActivity(intent)
        }
    }
}