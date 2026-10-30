package com.example.primer_proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    // Instanciamos el ViewModel
    private val viewModel: PacienteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                KotlinPetScreen(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KotlinPetScreen(viewModel: PacienteViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KotlinPet Manager \uD83D\uDC3E", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // --- FORMULARIO ---
            OutlinedTextField(
                value = viewModel.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre del paciente") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.especie,
                onValueChange = { viewModel.onEspecieChange(it) },
                label = { Text("Especie / Raza") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.edad,
                onValueChange = { viewModel.onEdadChange(it) },
                label = { Text("Edad (años)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.contactoEmergencia,
                onValueChange = { viewModel.onContactoChange(it) },
                label = { Text("Contacto de emergencia") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Checkbox(
                    checked = viewModel.esUrgencia,
                    onCheckedChange = { viewModel.onUrgenciaChange(it) }
                )
                Text("Requiere atención inmediata (Urgencia)")
            }

            Button(
                onClick = { viewModel.agregarPaciente() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Paciente")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Text("Pacientes Registrados:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // --- LISTA DINÁMICA ---
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewModel.pacientes) { paciente ->
                    PacienteCard(paciente)
                }
            }
        }
    }
}

@Composable
fun PacienteCard(paciente: Paciente) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (paciente.esUrgencia) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = paciente.nombre, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "Especie: ${paciente.especie} | Edad: ${paciente.edad} años")
            Text(text = "Contacto: ${paciente.contactoEmergencia}")
            if (paciente.esUrgencia) {
                Text(
                    text = "¡URGENCIA!",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}