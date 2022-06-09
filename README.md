
# Mobile Developement for JeDer Application

This is an Indonesian-Food-Recognizer named Jendela Resep or we can call it JeDer.



## How it Works

- Register and Login with an account.
- Take a photo of a food or get it from gallery.
- Upload it and wait for response.
- Tada~~ the result will come up.

## Features

- Taking a picture or getting from gallery to scan an Indonesian Food
- List of food that has been scanned.
- Delete an item from the list.
- Recipes for each type of food.
- Youtube Videos about how to make the food.


## Screenshots
<img src="https://user-images.githubusercontent.com/65072564/172913612-0102b832-99a2-4e38-9ef3-99dc6f3b3033.jpg" height="320" width="180"><img src="https://user-images.githubusercontent.com/65072564/172914510-cea9e84b-4673-48a3-ad1c-32701d2da4db.jpg" height="320" width="180"><img src="https://user-images.githubusercontent.com/65072564/172914522-e3722a8a-62d4-43bc-9fc6-d4be8ebae5e0.jpg" height="320" width="180"><img src="https://user-images.githubusercontent.com/65072564/172914548-15890f06-3429-4a78-b7ff-f97133b8e273.jpg" height="320" width="180"><img src="https://user-images.githubusercontent.com/65072564/172914562-0d35958b-b002-4d2c-83a1-9aed95b559d9.jpg" height="320" width="180"><img src="https://user-images.githubusercontent.com/65072564/172914577-5e6a594b-09c5-401a-b94c-c3c61255c910.jpg" height="320" width="180"><img src="https://user-images.githubusercontent.com/65072564/172914582-f6aa834c-231a-4be9-a5f8-2c477c97e946.jpg" height="320" width="180"><img src="https://user-images.githubusercontent.com/65072564/172914586-bc000e2a-ee53-4383-8b7f-74c21dfcccaa.jpg" height="320" width="180">

## Some Important Codes

```Kotlin
#Uploading Image to Predict
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
```
```Kotlin
#Post Method for Prediction
@Multipart
@POST("/prediction")
fun uploadImage(
    @Header("Authorization")
    token: String,
    @Part image: MultipartBody.Part,
): Call<FileUploadResponse>
```
```Kotlin
#Getting Youtube to Work
ytPlayer.initialize(apikey, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                player?.loadVideo(ytCode)
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
```
```Kotlin
#Adapter For History
class HistoryAdapter(private val listStory: ArrayList<ResponData>) :
    RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {
    private lateinit var binding: ItemsCardBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val stories = listStory[position]
        with(binding) {
            foodname.text = stories.name
            Glide.with(binding.root.context)
                .load(stories.imageUrl)
                .circleCrop()
                .into(foodpic)
            dateofpic.text = stories.timestamp
            root.setOnClickListener {
                onItemClickCallback.onItemClicked(stories)
            }

        }
        holder.setIsRecyclable(false)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listStory[holder.bindingAdapterPosition])
        }
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: ResponData)
    }

    override fun getItemCount(): Int = this.listStory.size
}
```



