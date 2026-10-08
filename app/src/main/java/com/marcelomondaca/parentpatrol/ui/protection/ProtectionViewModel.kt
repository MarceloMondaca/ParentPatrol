package com.marcelomondaca.parentpatrol.ui.protection
import androidx.lifecycle.AndroidViewModel // ViewModel con acceso a Application.
import androidx.compose.runtime.getValue // Permite leer el estado con by.
import androidx.compose.runtime.mutableStateOf // Crea un estado observable.
import androidx.compose.runtime.setValue // Permite actualizar el estado.
import android.app.Application // Permite acceder al contexto de la aplicación.
import com.marcelomondaca.parentpatrol.data.ProtectionPreferences // Gestiona las preferencias guardadas.
import androidx.lifecycle.viewModelScope // Permite ejecutar corrutinas desde el ViewModel.
import kotlinx.coroutines.launch // Inicia una corrutina.

class ProtectionViewModel(
    application: Application // Recibe el contexto de la aplicación.
) : AndroidViewModel(application) {

    private val protectionPreferences = ProtectionPreferences(application) // Accede a DataStore.
    var blockAdultContent by mutableStateOf(false)
        private set // Solo el ViewModel puede modificar este estado.

    var blockInappropriateApps by mutableStateOf(false)
        private set // Solo el ViewModel puede modificar este estado.


    init { // Se ejecuta al crear el ViewModel.
        viewModelScope.launch { // Inicia una corrutina.
            protectionPreferences.blockAdultContentFlow.collect { enabled ->
                blockAdultContent = enabled // Recupera el estado guardado.
            }
        }

        viewModelScope.launch { // Inicia una corrutina.
            protectionPreferences.blockInappropriateAppsFlow.collect { enabled ->
                blockInappropriateApps = enabled // Recupera el estado guardado.
            }
        }
    }

    fun updateAdultContentBlocking(enabled: Boolean) {
        blockAdultContent = enabled // Actualiza el interruptor.

        viewModelScope.launch { // Inicia una corrutina.
            protectionPreferences.saveAdultContentBlocking(enabled) // Guarda el estado en DataStore.
        }
    }

    fun updateInappropriateApps(enabled: Boolean) {
        blockInappropriateApps = enabled // Actualiza el interruptor.

        viewModelScope.launch { // Inicia una corrutina.
            protectionPreferences.saveInappropriateAppsBlocking(enabled) // Guarda el estado en DataStore.
        }
    }
}

