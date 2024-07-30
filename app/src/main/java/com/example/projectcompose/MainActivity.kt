package com.example.projectcompose

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.projectcompose.ui.theme.IncidentViewModel
import com.example.projectcompose.ui.theme.ProjectComposeTheme
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.Date

class MainActivity : ComponentActivity() {
    private val mainViewModel: IncidentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    IncidentScreen(mainViewModel)
                    }

                }
            }
        }
    }

@Composable
fun IncidentScreen(
    viewModel: IncidentViewModel 
) {
    var showDialog by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(Date().time) }
    var description by remember { mutableStateOf("") }
    var photo by remember { mutableStateOf<ByteArray?>(null) }

    val context = LocalContext.current
    val getImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(it)
            val byteArrayOutputStream = ByteArrayOutputStream()
            inputStream?.copyTo(byteArrayOutputStream)
            photo = byteArrayOutputStream.toByteArray()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Incident")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            // Your content here, e.g., a list of incidents
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add New Incident") },
            text = {
                Column {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Date: ${Date(date)}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { getImage.launch("image/*") }) {
                        Text("Select Photo")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.saveIncident(title, date, description, photo)
                    showDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

