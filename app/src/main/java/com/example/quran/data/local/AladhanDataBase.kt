package com.example.quran.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quran.model.aladhan.AladhanResponse


@Database(entities = [AladhanResponse::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AladhanDataBase : RoomDatabase() {


    abstract fun aladhanDao(): AladhanDao

}