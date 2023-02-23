package com.example.sepiapets.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
data class Pet(
    @SerializedName("image_url")
    val imageUrl: String,
    val title: String,
    @SerializedName("content_url")
    val contentUrl: String,
    @SerializedName("date_added")
    val dateAdded: String
): Parcelable