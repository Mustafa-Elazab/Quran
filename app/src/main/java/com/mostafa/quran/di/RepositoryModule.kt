package com.mostafa.quran.di
import com.mostafa.quran.data.repository.AlarmRepositoryImpl
import com.mostafa.quran.data.repository.HomeRepositoryImpl
import com.mostafa.quran.data.repository.QuranRepositoryImpl
import com.mostafa.quran.domain.repository.AlarmRepository
import com.mostafa.quran.domain.repository.HomeRepository
import com.mostafa.quran.domain.repository.QuranRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHomeRepository(repository: HomeRepositoryImpl): HomeRepository


    @Binds
    abstract fun bindQuranRepository(repository: QuranRepositoryImpl): QuranRepository


    @Binds
    abstract fun bindAlarmRepository(repository: AlarmRepositoryImpl): AlarmRepository

}