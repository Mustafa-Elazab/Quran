package com.mostafa.quran.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mostafa.quran.data.local.converter.Converters
import com.mostafa.quran.data.local.dao.PrayTimeDao
import com.mostafa.quran.data.remote.dto.AladhanResponseDTO


@Database(
    entities = [AladhanResponseDTO::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun prayDao(): PrayTimeDao


}