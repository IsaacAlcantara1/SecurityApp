package com.example.projectcompose.ui.theme

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projectcompose.main.Incident


@Database(entities = [Incident::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incidentDao(): IncidentDao
}
