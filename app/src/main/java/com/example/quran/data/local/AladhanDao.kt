package com.example.quran.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quran.model.aladhan.AladhanResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface AladhanDao {


    @Query("SELECT  * FROM  prayTime_db")
    fun getAllPrayTime(): Flow<AladhanResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayTime(prayTime: AladhanResponse)

    @Query("DELETE FROM prayTime_db")
    suspend fun deletePrayTime()
}