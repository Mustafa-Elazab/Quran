package com.example.quran.di

import android.content.Context
import androidx.room.Room
import com.example.quran.data.local.AladhanDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun providePrayTimeDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AladhanDataBase::class.java,
        "prayTime_db"
    ).build()


    @Singleton
    @Provides
    fun provideDao(database: AladhanDataBase) = database.aladhanDao()
}