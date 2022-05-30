package com.example.jederv1.api

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

data class FileUploadResponse(
    @field:SerializedName("success")
    val success: Boolean,
    @field:SerializedName("fnlResult")
    val fnlResult: FinalResult
)

data class FinalResult(
    @field:SerializedName("result")
    val result: String,
    @field:SerializedName("recipe")
    val recipe: String,
    @field:SerializedName("ytCode")
    val ytCode: String,

)

data class LoginResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("token")
    val token: String
)

data class RegisterResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("user")
    val user: User
)

data class TokenClass(
    val token: String
)

data class User(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    val islogin: Boolean
)

class ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://named-reporter-343719.as.r.appspot.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @FormUrlEncoded
    @POST("/user/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @Multipart
    @POST("/prediction")
    fun uploadImage(
        @Header("Authorization")
        token: String,
        @Part image: MultipartBody.Part,
    ): Call<FileUploadResponse>


//    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
//        "photo",
//        file.name,
//        requestImageFile
//    )
}
