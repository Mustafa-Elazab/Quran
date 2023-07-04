package com.mostafa.quran.di

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import android.os.Vibrator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    fun provideVibrator(application: Application): Vibrator {
        return application.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    @Provides
    fun provideAlarmManager(application: Application): AlarmManager {
        return application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
}