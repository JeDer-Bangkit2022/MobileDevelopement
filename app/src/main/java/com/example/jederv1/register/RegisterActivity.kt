package com.example.jederv1.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.jederv1.api.ApiConfig
import com.example.jederv1.api.ApiService
import com.example.jederv1.api.RegisterResponse
import com.example.jederv1.databinding.ActivityRegisterBinding
import com.example.jederv1.entity.UserModel
import com.example.jederv1.entity.UserPreference
import com.example.jederv1.entity.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun setupViewModel() {
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[RegisterViewModel::class.java]
    }

    private fun setupAction() {
        binding.mybutton1.setOnClickListener {
            val name = binding.EditText1.text.toString().trim()
            val email = binding.EditText2.text.toString().trim()
            val password = binding.EditText3.text.toString().trim()
            when {
                name.isEmpty() -> {
                    binding.EditText1.error = "Masukkan Nama"
                    Toast.makeText(
                        applicationContext,
                        "Masukan Nama",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                email.isEmpty() -> {
                    binding.EditText2.error = "Masukkan Email"
                    Toast.makeText(
                        applicationContext,
                        "Masukan Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                password.isEmpty() -> {
                    binding.EditText3.error = "Masukkan Password"
                    binding.mybutton1.isEnabled = false
                    Toast.makeText(
                        applicationContext,
                        "Masukan Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Log.d("NAMAAAAA", name)
                    Log.d("EMAILLL", email)
                    Log.d("Passworddddd", password)
                    val register = ApiConfig().getApiService().register(name, email, password)
                    register.enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {

                            if (response.body() != null) {
                                Log.d("MAMAMMA", response.body().toString())
                                registerViewModel.saveUser(UserModel(name, email, password, false))
                                AlertDialog.Builder(this@RegisterActivity).apply {
                                    setTitle("Yeah!")
                                    setMessage("Anda berhasil membuat akun")
                                    setPositiveButton("Lanjut") { _, _ ->
                                        finish()
                                    }
                                    create()
                                    show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(
                                applicationContext,
                                t.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
                }
            }
        }
    }


    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.textView3, View.ALPHA, 1f).setDuration(500)
        val nameTextView = ObjectAnimator.ofFloat(binding.nama, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.EditText1, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.email, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.EditText2, View.ALPHA, 1f).setDuration(500)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.password, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.EditText3, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.mybutton1, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 500
        }.start()
    }
}