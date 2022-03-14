package com.uchihan.smartb_card.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.uchihan.smartb_card.common.Constants
import com.uchihan.smartb_card.data.repository.NearbyRepoImpl
import com.uchihan.smartb_card.data.room.NearbyDao
import com.uchihan.smartb_card.data.room.NearbyDatabase
import com.uchihan.smartb_card.domain.repository.NearbyRepo
import com.uchihan.smartb_card.domain.use_case.GetAllCardsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesDatabase(@ApplicationContext context: Context):NearbyDatabase =
        Room.databaseBuilder(context,NearbyDatabase::class.java,"nearbydatabase")
            .build()

    @Provides
    fun providesNearbyDao(nearbyDatabase: NearbyDatabase):NearbyDao =
        nearbyDatabase.getNearbyDao()

    @Provides
    fun providesNearbyRepositoryImpl(nearbyDao: NearbyDao):NearbyRepoImpl =
        NearbyRepoImpl(nearbyDao)

    @Provides
    fun provideNearbyRepo(nearbyDao: NearbyDao):NearbyRepo =  NearbyRepoImpl(nearbyDao)

    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.USER_DATA,MODE_PRIVATE)
    }

    @Provides
    fun provideNearbyUseCase(nearbyRepo: NearbyRepoImpl):GetAllCardsUseCase{
        return GetAllCardsUseCase(nearbyRepo)
    }

    @Provides
    fun provideGson(): Gson{
        return Gson()
    }
}