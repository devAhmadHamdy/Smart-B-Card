package com.uchihan.smartb_card.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.data.models.UserModel

@Dao
interface NearbyDao {
    @Query("SELECT * FROM roomusermodel")
    suspend fun getAll(): List<RoomUserModel>

    @Insert
    suspend fun addNearby(roomUserModel: RoomUserModel)

    @Delete
    suspend fun deleteNearby(roomUserModel: RoomUserModel)
}