package com.mostafa.quran.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mostafa.quran.data.remote.dto.AladhanData
import com.mostafa.quran.data.remote.dto.AladhanResponseDTO
import com.mostafa.quran.data.remote.dto.TimingResponse

class Converters {


    @TypeConverter
    fun aladhanResponseDTOToString(aladhanResponseDTO: AladhanResponseDTO): String {
        return Gson().toJson(aladhanResponseDTO)
    }


    @TypeConverter
    fun stringToAladhanResponseDTO(data: String): AladhanResponseDTO {
        val listType = object : TypeToken<AladhanResponseDTO>() {}.type
        return Gson().fromJson(data, listType)
    }


    @TypeConverter
    fun aladhanDataToString(aladhanData: List<AladhanData>?): String {
        return Gson().toJson(aladhanData)
    }


    @TypeConverter
    fun stringToAladhanData(data: String): List<AladhanData>? {
        val listType = object : TypeToken<List<AladhanData>?>() {}.type
        return Gson().fromJson(data, listType)
    }


    @TypeConverter
    fun timingsToString(timings: TimingResponse): String {
        return Gson().toJson(timings)
    }

    @TypeConverter
    fun stringToTimings(data: String): TimingResponse {
        val listType = object : TypeToken<TimingResponse>() {}.type
        return Gson().fromJson(data, listType)
    }


}