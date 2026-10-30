package com.example.primer_proyecto

import java.util.UUID

data class Paciente(
    val id: String = UUID.randomUUID().toString(), // Genera un ID único automáticamente
    val nombre: String,
    val especie: String,
    val edad: Int,
    val contactoEmergencia: String,
    val esUrgencia: Boolean
)