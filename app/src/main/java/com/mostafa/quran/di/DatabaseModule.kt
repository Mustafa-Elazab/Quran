package com.mostafa.quran.di

import android.content.Context
import androidx.room.Room
import com.mostafa.quran.data.local.dao.PrayTimeDao
import com.mostafa.quran.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideVisaDao(appDatabase: AppDatabase): PrayTimeDao = appDatabase.prayDao()


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-db"
        ).build()


}