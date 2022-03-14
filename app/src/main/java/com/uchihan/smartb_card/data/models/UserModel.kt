package com.uchihan.smartb_card.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserModel(
    val name: String,
    val mobile: String,
    val email: String,
    val work: String,
    val address: String,
    val gender:Int
)
