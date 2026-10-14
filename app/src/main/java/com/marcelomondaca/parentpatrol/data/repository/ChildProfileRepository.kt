package com.marcelomondaca.parentpatrol.data.repository

import com.marcelomondaca.parentpatrol.data.local.dao.ChildProfileDao // Permite acceder al DAO.
import com.marcelomondaca.parentpatrol.data.local.entity.ChildProfileEntity // Representa un perfil infantil.
import kotlinx.coroutines.flow.Flow // Permite observar cambios en los perfiles.

class ChildProfileRepository(
    private val childProfileDao: ChildProfileDao // Recibe el DAO de Room.
) {

    suspend fun countChildren(): Int = childProfileDao.countChildren() // Consulta cuántos perfiles existen.
    val allChildren: Flow<List<ChildProfileEntity>> =
        childProfileDao.getAllChildren() // Observa los perfiles guardados.

    suspend fun insertChild(child: ChildProfileEntity): Long {
        return childProfileDao.insertChild(child) // Guarda un perfil y devuelve su ID.
    }

    suspend fun childExists(
        name: String,
        birthDate: Long
    ): Boolean {
        return childProfileDao.childExists(
            name = name,
            birthDate = birthDate
        ) // Consulta en Room si el perfil ya existe.
    }

    suspend fun deleteChildById(childId: Long) {
        childProfileDao.deleteChildById(childId) // Elimina un perfil por su ID.
    }
}

