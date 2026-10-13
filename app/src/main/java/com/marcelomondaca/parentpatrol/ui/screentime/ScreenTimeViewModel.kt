package com.marcelomondaca.parentpatrol.ui.screentime

import android.app.Application // Permite acceder al contexto de la aplicación.
import androidx.lifecycle.AndroidViewModel // ViewModel con acceso a Application.
import com.marcelomondaca.parentpatrol.data.ScreenTimePreferences // Gestiona los minutos guardados.
import androidx.lifecycle.viewModelScope // Permite ejecutar corrutinas desde el ViewModel.
import kotlinx.coroutines.launch // Inicia una corrutina.
import androidx.compose.runtime.getValue // Permite leer el estado con by.
import androidx.compose.runtime.mutableStateOf // Crea un estado observable por Compose.
import androidx.compose.runtime.setValue // Permite actualizar el estado con by.

class ScreenTimeViewModel(
    application: Application // Recibe el contexto de la aplicación.
) : AndroidViewModel(application) {
    private val screenTimePreferences = ScreenTimePreferences(application) // Accede a DataStore.

    var selectedMinutes by mutableStateOf<Int?>(null) // Mantiene el tiempo seleccionado.
        private set // Solo el ViewModel puede modificar este estado.

    init { // Se ejecuta al crear el ViewModel.
        viewModelScope.launch { // Inicia una corrutina.
            screenTimePreferences.selectedMinutesFlow.collect { minutes ->
                selectedMinutes = minutes // Recupera los minutos guardados.
            }
        }
    }

    fun saveSelectedMinutes(minutes: Int) { // Recibe los minutos seleccionados.
        selectedMinutes = minutes // Actualiza inmediatamente el estado de la interfaz.

        viewModelScope.launch { // Inicia una corrutina sin bloquear la interfaz.
            screenTimePreferences.saveSelectedMinutes(minutes) // Guarda los minutos en DataStore.
        }
    }
}