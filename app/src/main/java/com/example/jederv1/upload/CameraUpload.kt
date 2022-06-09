package com.example.jederv1.upload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.jederv1.result.ResultActivity
import com.example.jederv1.api.ApiConfig
import com.example.jederv1.api.FileUploadResponse
import com.example.jederv1.createTempFile
import com.example.jederv1.databinding.ActivityCameraUploadBinding
import com.example.jederv1.reduceFileImage
import com.example.jederv1.rotateBitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class CameraUpload : AppCompatActivity() {
    private lateinit var binding: ActivityCameraUploadBinding
    private var getFile: File? = null
    private lateinit var currentPhotoPath: String

    companion object {
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        const val REQUEST_CODE_PERMISSIONS = 10
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
        binding = ActivityCameraUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.cameraButton.setOnClickListener {
            startTakePhoto()
        }
        binding.uploadButton.setOnClickListener {
            uploadImage()
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@CameraUpload,
                "com.example.jederv1",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result = rotateBitmap(BitmapFactory.decodeFile(getFile?.path), true)
            binding.previewImageView.setImageBitmap(result)
        }
    }

    private fun uploadImage() {
        showLoading(true)
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
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
                                val id = res.id
                                val result = res.result
                                val resAcc = res.resultAccuracy
                                val imageUrl = res.imageUrl
                                val recipe = res.recipe
                                val desc = res.description
                                val ytCode = res.ytCode
                                AlertDialog.Builder(this@CameraUpload).apply {
                                    setTitle("Yeah!")
                                    setMessage("Upload Success.")
                                    setPositiveButton("Next") { _, _ ->
                                        val intent = Intent(context, ResultActivity::class.java)
                                        val tokenformain = Bundle()
                                        tokenformain.putString("id", id)
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
                                this@CameraUpload,
                                response.message(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                        Toast.makeText(
                            this@CameraUpload,
                            "Instance Retrofit Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        } else {
            Toast.makeText(
                this@CameraUpload,
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