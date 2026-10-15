
package com.marcelomondaca.parentpatrol.ui.childsettings

// -------------------------------------------------
// ANDROID Y VIEWMODEL
// -------------------------------------------------

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

// -------------------------------------------------
// BASE DE DATOS Y REPOSITORIO
// -------------------------------------------------

import com.marcelomondaca.parentpatrol.data.local.ParentPatrolDatabase
import com.marcelomondaca.parentpatrol.data.local.entity.ChildProfileEntity
import com.marcelomondaca.parentpatrol.data.repository.ChildProfileRepository

// -------------------------------------------------
// CORRUTINAS Y ESTADOS
// -------------------------------------------------

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Gestiona la configuración individual
 * de los perfiles infantiles.
 */
class ChildSettingsViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Repositorio conectado a Room.
    private val repository = ChildProfileRepository(
        ParentPatrolDatabase.getDatabase(application).childProfileDao()
    )

    // Estado interno del perfil seleccionado.
    private val _child = MutableStateFlow<ChildProfileEntity?>(null)

    // Estado observable desde la interfaz.
    val child: StateFlow<ChildProfileEntity?> = _child

    // -------------------------------------------------
    // 1. CONSULTAR PERFIL INFANTIL
    // -------------------------------------------------

    // Busca un perfil mediante su ID.
    fun loadChild(childId: Long) {

        viewModelScope.launch {

            // Consulta los datos desde Room.
            _child.value = repository.getChildById(childId)

        } // Fin de corrutina.

    } // Fin de loadChild.

    // -------------------------------------------------
    // 2. ELIMINAR PERFIL INFANTIL
    // -------------------------------------------------

    // Elimina un perfil definitivamente.
    fun deleteChild(
        childId: Long,
        onDeleted: () -> Unit
    ) {

        viewModelScope.launch {

            // Elimina el registro desde Room.
            repository.deleteChildById(childId)

            // Confirma que terminó la eliminación.
            onDeleted()

        } // Fin de corrutina.

    } // Fin de deleteChild.

} // Fin de ChildSettingsViewModel.

