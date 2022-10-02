package com.example.quran.data.local

import androidx.room.TypeConverter
import com.example.quran.model.aladhan.AladhanResponse
import com.example.quran.model.aladhan.Data
import com.example.quran.model.aladhan.Timings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    var gson = Gson()

    @TypeConverter
    fun aladhanResponseToString(aladhanResponse: AladhanResponse): String {
        return gson.toJson(aladhanResponse)
    }

    @TypeConverter
    fun stringToAladhanResponse(data: String): AladhanResponse {
        val listType = object : TypeToken<AladhanResponse>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun dataToString(data: Data): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun stringToData(data: String): Data {
        val listType = object : TypeToken<Data>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun timingsToString(timings: Timings): String {
        return gson.toJson(timings)
    }

    @TypeConverter
    fun stringToTimings(data: String): Timings {
        val listType = object : TypeToken<Timings>() {}.type
        return gson.fromJson(data, listType)
    }

}