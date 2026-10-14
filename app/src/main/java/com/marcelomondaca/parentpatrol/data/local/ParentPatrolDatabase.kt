package com.marcelomondaca.parentpatrol.data.local

import androidx.room.Database // Declara las entidades y la versión de la base de datos.
import androidx.room.RoomDatabase // Clase base de las bases de datos Room.
import com.marcelomondaca.parentpatrol.data.local.dao.ChildProfileDao // DAO de perfiles infantiles.
import com.marcelomondaca.parentpatrol.data.local.entity.ChildProfileEntity // Entidad de perfiles infantiles.

@Database(
    entities = [ChildProfileEntity::class], // Tablas que tendrá nuestra base de datos.
    version = 1, // Primera versión de la estructura de datos.
    exportSchema = true // Permite conservar el historial de esquemas.
)
abstract class ParentPatrolDatabase : RoomDatabase() {

    abstract fun childProfileDao(): ChildProfileDao // Proporciona acceso al DAO.

    companion object {
        @Volatile // Mantiene visible la instancia entre diferentes hilos.
        private var INSTANCE: ParentPatrolDatabase? = null

        fun getDatabase(context: android.content.Context): ParentPatrolDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext, // Evita conservar una Activity en memoria.
                    ParentPatrolDatabase::class.java,
                    "parentpatrol_database" // Nombre del archivo de la base de datos.
                ).build()

                INSTANCE = instance // Conserva la instancia creada.
                instance
            }
        }
    }
}