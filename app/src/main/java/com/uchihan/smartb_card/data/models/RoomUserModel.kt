package com.uchihan.smartb_card.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomUserModel(
    val name: String,
    @PrimaryKey val mobile: String,
    val email: String,
    val work: String,
    val address: String,
    val gender:Int
)

fun userModelToRoomUserModel(userModel: UserModel):RoomUserModel{
    return RoomUserModel(userModel.name,userModel.mobile,userModel.email,userModel.work,userModel.address,userModel.gender)
}