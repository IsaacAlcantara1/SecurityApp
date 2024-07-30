package com.example.projectcompose.main

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incidents")
data class Incident(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val date: Long,
    val description: String,
    val photo: ByteArray? = null
)



