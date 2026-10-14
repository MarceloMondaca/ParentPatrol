package com.marcelomondaca.parentpatrol.ui.childprofiles

import android.app.Application // Permite acceder al contexto de la aplicación.
import androidx.lifecycle.AndroidViewModel // ViewModel con acceso a Application.
import androidx.lifecycle.viewModelScope // Permite ejecutar corrutinas.
import com.marcelomondaca.parentpatrol.data.local.ParentPatrolDatabase // Base de datos Room.
import com.marcelomondaca.parentpatrol.data.local.entity.ChildProfileEntity // Representa un perfil infantil.
import com.marcelomondaca.parentpatrol.data.repository.ChildProfileRepository // Gestiona los perfiles.
import kotlinx.coroutines.flow.SharingStarted // Controla cuándo se observan los datos.
import kotlinx.coroutines.flow.stateIn // Convierte un Flow en StateFlow.
import kotlinx.coroutines.launch // Ejecuta operaciones sin bloquear la interfaz.

class ChildProfilesViewModel(
    application: Application
) : AndroidViewModel(application) {


    private val repository = ChildProfileRepository(
        ParentPatrolDatabase.getDatabase(application).childProfileDao()
    ) // Conecta el repositorio con Room.

    val children = repository.allChildren.stateIn(
        scope = viewModelScope, // Mantiene la observación mientras vive el ViewModel.
        started = SharingStarted.WhileSubscribed(5_000), // Conserva la observación brevemente.
        initialValue = emptyList() // Comienza con una lista vacía.
    )

    fun checkExistingProfiles(onResult: (Boolean) -> Unit) {
        viewModelScope.launch { // Ejecuta la consulta sin bloquear la interfaz.
            val count = repository.countChildren() // Obtiene la cantidad de perfiles.
            onResult(count > 0) // true si existen perfiles; false si no existen.
        }
    }

    fun addChild(
        name: String,
        birthDate: Long,
        onSaved: () -> Unit, // Acción cuando el perfil se guarda.
        onDuplicate: () -> Unit // Acción cuando el perfil ya existe.
    ) {
        viewModelScope.launch {

            val exists = repository.childExists(
                name = name.trim(),
                birthDate = birthDate
            ) // Comprueba si ya existe el niño.

            if (exists) {
                onDuplicate() // Informa que encontramos un duplicado.
                return@launch // Detiene el registro.
            }

            repository.insertChild(
                ChildProfileEntity(
                    name = name.trim(),
                    birthDate = birthDate
                )
            ) // Guarda el nuevo perfil en Room.

            onSaved() // Continúa después de guardar correctamente.
        }
    }

    fun deleteChild(childId: Long) {
        viewModelScope.launch {
            repository.deleteChildById(childId) // Elimina el perfil seleccionado.
        }
    }
}