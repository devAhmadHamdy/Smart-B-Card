package com.uchihan.smartb_card.data.repository

import com.uchihan.smartb_card.data.models.RoomUserModel
import com.uchihan.smartb_card.data.models.UserModel
import com.uchihan.smartb_card.data.room.NearbyDao
import com.uchihan.smartb_card.domain.repository.NearbyRepo
import javax.inject.Inject

class NearbyRepoImpl @Inject constructor(private val nearbyDao: NearbyDao) : NearbyRepo {
    override suspend fun getAllNearby(): List<RoomUserModel> {
        return nearbyDao.getAll()
    }

    override suspend fun addNearby(roomUserModel: RoomUserModel) {
        nearbyDao.addNearby(roomUserModel)
    }

    override suspend fun deleteNearby(roomUserModel: RoomUserModel) {
        nearbyDao.deleteNearby(roomUserModel)
    }
}