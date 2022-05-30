package com.example.jederv1.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodModel(
    val id: Int,
    val name: String,
    val description: String,
    val recipes: String,
    val ytcode: String,
): Parcelable

