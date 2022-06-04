package com.example.jederv1.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.jederv1.MainActivity
import com.example.jederv1.databinding.ActivityResultBinding
import com.example.jederv1.details.DetailActivity
import com.example.jederv1.entity.UserPreference
import com.example.jederv1.entity.ViewModelFactory
import com.example.jederv1.history.HistoryViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val result = bundle?.getString("result").toString()
        val resAcc = bundle?.getString("resAcc")
        val imageUrl = bundle?.getString("imageUrl").toString()
        val desc = bundle?.getString("desc").toString()
       Log.d("HASILACC", resAcc.toString())
        Glide.with(binding.root.context)
            .load(imageUrl)
            .into(binding.imagedesc)

        with(binding) {
            descripsi.text = desc
            textViewjudul.text = result
            if (resAcc != null){
                akurasi.text = "Akurasi : $resAcc"
            }else{
                akurasi.visibility = View.INVISIBLE
            }

        }
        binding.button3.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
        }
        binding.next.setOnClickListener {
            historyViewModel = ViewModelProvider(
                this,
                ViewModelFactory(UserPreference.getInstance(dataStore))
            )[HistoryViewModel::class.java]

            val intent = Intent(this@ResultActivity, DetailActivity::class.java)

            if (bundle != null) {
                intent.putExtras(bundle)
            }
            startActivity(intent)
        }

    }
}