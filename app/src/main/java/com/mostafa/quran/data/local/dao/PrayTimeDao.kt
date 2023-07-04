package com.mostafa.quran.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mostafa.quran.data.remote.dto.AladhanResponseDTO


@Dao
interface PrayTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPrayTime(aladhanDto: AladhanResponseDTO)

    @Query("SELECT * FROM aladhanresponsedto")
    fun getPrayTime(): AladhanResponseDTO


    @Query("DELETE FROM aladhanresponsedto")
    suspend fun deletePrayTime()

}