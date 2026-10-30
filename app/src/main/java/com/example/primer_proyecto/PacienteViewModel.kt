package com.example.primer_proyecto

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PacienteViewModel : ViewModel() {

    // Lista observable de pacientes (estado encapsulado)
    private val _pacientes = mutableStateListOf<Paciente>()
    val pacientes: List<Paciente> get() = _pacientes

    // Estados del formulario
    var nombre by mutableStateOf("")
        private set
    var especie by mutableStateOf("")
        private set
    var edad by mutableStateOf("")
        private set
    var contactoEmergencia by mutableStateOf("")
        private set
    var esUrgencia by mutableStateOf(false)
        private set

    // Funciones para actualizar el estado desde la vista
    fun onNombreChange(nuevoValor: String) { nombre = nuevoValor }
    fun onEspecieChange(nuevoValor: String) { especie = nuevoValor }
    fun onEdadChange(nuevoValor: String) { edad = nuevoValor }
    fun onContactoChange(nuevoValor: String) { contactoEmergencia = nuevoValor }
    fun onUrgenciaChange(nuevoValor: Boolean) { esUrgencia = nuevoValor }

    // Valida y agrega el paciente a la lista
    fun agregarPaciente() {
        if (nombre.isNotBlank() && especie.isNotBlank() && edad.isNotBlank()) {
            val nuevoPaciente = Paciente(
                nombre = nombre,
                especie = especie,
                edad = edad.toIntOrNull() ?: 0, // Evita crashes si el usuario no pone un número válido
                contactoEmergencia = contactoEmergencia,
                esUrgencia = esUrgencia
            )
            _pacientes.add(nuevoPaciente)
            limpiarFormulario()
        }
    }

    // Reseteo de campos
    fun limpiarFormulario() {
        nombre = ""
        especie = ""
        edad = ""
        contactoEmergencia = ""
        esUrgencia = false
    }
}
