package com.example.jederv1.api

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

data class FileUploadResponse(
    @field:SerializedName("success")
    val success: Boolean,
    @field:SerializedName("result")
    val result: String,
    @field:SerializedName("resultAccuracy")
    val resultAccuracy: String,
    @field:SerializedName("imageUrl")
    val imageUrl: String,
    @field:SerializedName("recipe")
    val recipe: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("ytCode")
    val ytCode: String,
    @field:SerializedName("id")
    val id: String,
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

data class HistoryResponse(
    @field:SerializedName("success")
    val success: Boolean,
    @field:SerializedName("count")
    val count: Int,
    @field:SerializedName("responData")
    val responData: ArrayList<ResponData>,
)

data class ResponData(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("resultAccuracy")
    val resultAccuracy: String,
    @field:SerializedName("imageUrl")
    val imageUrl: String,
    @field:SerializedName("recipe")
    val recipe: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("ytCode")
    val ytCode: String,
    @field:SerializedName("timestamp")
    val timestamp: String,
)
data class ResponseDelete(
    @field:SerializedName("success")
    val success: Boolean,
    @field:SerializedName("msg")
    val msg: String,
)
data class ResponDatabyID(
    @field:SerializedName("success")
    val success: Boolean,
    @field:SerializedName("result")
    val result: String,
    @field:SerializedName("resultAccuracy")
    val resultAccuracy: String,
    @field:SerializedName("imageUrl")
    val imageUrl: String,
    @field:SerializedName("recipe")
    val recipe: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("ytCode")
    val ytCode: String,
)

class ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://capstone-project-jeder.as.r.appspot.com")
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

    @GET("/prediction/history/")
    fun fetchHistories(
        @Header("Authorization") token: String,
    ): Call<HistoryResponse>

    @GET("/prediction/history/{id}")
    fun fetchbyId(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<ResponDatabyID>

    @DELETE("/prediction/history/{id}")
    fun deletebyId(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<ResponseDelete>

}


