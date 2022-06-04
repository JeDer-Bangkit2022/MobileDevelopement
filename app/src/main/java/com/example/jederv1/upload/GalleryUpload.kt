package com.example.jederv1.upload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.jederv1.result.ResultActivity
import com.example.jederv1.api.ApiConfig
import com.example.jederv1.api.FileUploadResponse
import com.example.jederv1.databinding.ActivityGalleryUploadBinding
import com.example.jederv1.reduceFileImage
import com.example.jederv1.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class GalleryUpload : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryUploadBinding
    private var getFile: File? = null

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                CameraUpload.REQUIRED_PERMISSIONS,
                CameraUpload.REQUEST_CODE_PERMISSIONS
            )
        }
        binding.uploadButton.setOnClickListener {
            uploadImage()
        }
        binding.GalleryButton.setOnClickListener {
            startGallery()
        }

    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@GalleryUpload)
            getFile = myFile
            binding.previewImageView.setImageURI(selectedImg)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun uploadImage() {
        showLoading(true)
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
//            val description = "Test"
//            val descriptions = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )
            val bundle = intent.extras
            val token = bundle?.getString("token")

            token?.let {
                ApiConfig().getApiService().uploadImage("Bearer $it", imageMultipart)
            }
                ?.enqueue(object : Callback<FileUploadResponse> {
                    override fun onResponse(
                        call: Call<FileUploadResponse>,
                        response: Response<FileUploadResponse>
                    ) {
                        if (response.isSuccessful) {
                            showLoading(false)
                            val res = response.body()
                            if (res != null && res.success) {
                                val result = res.result
                                val resAcc = res.resultAccuracy
                                val imageUrl = res.imageUrl
                                val recipe = res.recipe
                                val desc = res.description
                                val ytCode = res.ytCode
                                AlertDialog.Builder(this@GalleryUpload).apply {
                                    setTitle("Yeah!")
                                    setMessage("Upload Success.")
                                    setPositiveButton("Next") { _, _ ->
                                        val intent = Intent(context, ResultActivity::class.java)
                                        val tokenformain = Bundle()
                                        tokenformain.putString("token", token)
                                        tokenformain.putString("result", result)
                                        tokenformain.putString("resAcc", resAcc)
                                        tokenformain.putString("imageUrl", imageUrl)
                                        tokenformain.putString("recipe", recipe)
                                        tokenformain.putString("desc", desc)
                                        tokenformain.putString("ytCode", ytCode)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        intent.putExtras(tokenformain)
                                        startActivity(intent)
                                        finish()
                                    }
                                    create()
                                    show()
                                }

                            }
                        } else {
                            Toast.makeText(
                                this@GalleryUpload,
                                response.message(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                        showLoading(false)
                        Toast.makeText(
                            this@GalleryUpload,
                            "Instance Retrofit Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        } else {
            showLoading(false)
            Toast.makeText(
                this@GalleryUpload,
                "Please put in the picture first.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

}