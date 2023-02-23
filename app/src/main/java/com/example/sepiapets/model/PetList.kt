package com.example.sepiapets.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PetList(
    val pets: List<Pet>
):Parcelable