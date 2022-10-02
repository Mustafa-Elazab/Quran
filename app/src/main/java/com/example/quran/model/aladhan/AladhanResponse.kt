package com.example.quran.model.aladhan

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayTime_db")
data class AladhanResponse(
    @PrimaryKey(autoGenerate = false)
    val code: Int,
    val `data`: Data,
    val status: String
)