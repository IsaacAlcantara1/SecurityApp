package com.example.projectcompose.ui.theme

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.projectcompose.main.Incident



@Dao
interface IncidentDao {
    @Insert
    suspend fun insertIncident(incident: Incident)

    @Update
    suspend fun updateIncident(incident: Incident)

    @Delete
    suspend fun deleteIncident(incident: Incident)

    @Query("DELETE FROM incidents")
    suspend fun deleteAllIncidents()

    @Query("SELECT * FROM incidents")
    suspend fun getAllIncidents(): List<Incident>

    @Query("SELECT * FROM incidents WHERE id = :id")
    suspend fun getIncidentById(id: Int): Incident?
}


