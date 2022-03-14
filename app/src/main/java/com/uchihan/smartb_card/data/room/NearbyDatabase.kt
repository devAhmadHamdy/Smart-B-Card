package com.uchihan.smartb_card.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uchihan.smartb_card.data.models.RoomUserModel

@Database(entities = [RoomUserModel::class], version = 2,exportSchema = false)
abstract class NearbyDatabase : RoomDatabase() {
    abstract  fun getNearbyDao():NearbyDao

}