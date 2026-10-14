package com.marcelomondaca.parentpatrol.data.local.entity

import androidx.room.Entity // Identifica una tabla de Room.
import androidx.room.PrimaryKey // Define la clave primaria de la tabla.

@Entity(tableName = "child_profiles") // Tabla donde guardaremos los perfiles infantiles.
data class ChildProfileEntity(

    @PrimaryKey(autoGenerate = true) // Genera un ID único para cada perfil.
    val id: Long = 0,

    val name: String, // Nombre del niño o niña.
    val birthDate: Long // Fecha de nacimiento.
)