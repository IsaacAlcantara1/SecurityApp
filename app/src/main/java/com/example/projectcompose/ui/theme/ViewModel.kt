package com.example.projectcompose.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcompose.main.Incident
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class IncidentViewModel(application: Application) : AndroidViewModel(application) {
    private val db = DatabaseProvider.getDatabase(application)

    fun saveIncident(title: String, date: Long, description: String, photo: ByteArray?) {
        viewModelScope.launch(Dispatchers.IO) {
            db.incidentDao().insertIncident(Incident(title = title, date = date, description = description, photo = photo))
        }
    }

    fun updateIncident(incident: Incident) {
        viewModelScope.launch(Dispatchers.IO) {
            db.incidentDao().updateIncident(incident)
        }
    }

    fun deleteIncident(incident: Incident) {
        viewModelScope.launch(Dispatchers.IO) {
            db.incidentDao().deleteIncident(incident)
        }
    }

    fun deleteAllIncidents() {
        viewModelScope.launch(Dispatchers.IO) {
            db.incidentDao().deleteAllIncidents()
        }
    }

    fun getAllIncidents(callback: (List<Incident>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val incidents = db.incidentDao().getAllIncidents()
            viewModelScope.launch(Dispatchers.Main) {
                callback(incidents)
            }
        }
    }
}
