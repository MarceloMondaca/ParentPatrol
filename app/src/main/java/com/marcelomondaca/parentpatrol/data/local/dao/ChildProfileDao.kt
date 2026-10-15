package com.marcelomondaca.parentpatrol.data.local.dao

import androidx.room.Dao // Identifica una interfaz de acceso a datos.
import androidx.room.Insert // Permite insertar registros en la base de datos.
import androidx.room.Query // Permite ejecutar consultas SQL.
import com.marcelomondaca.parentpatrol.data.local.entity.ChildProfileEntity // Representa un perfil infantil.
import kotlinx.coroutines.flow.Flow // Permite observar cambios en los datos.

@Dao // Indica que esta interfaz administra operaciones de la base de datos.
interface ChildProfileDao {

      @Insert // Guarda un nuevo perfil infantil.
    suspend fun insertChild(child: ChildProfileEntity): Long

    @Query("""
    SELECT EXISTS(
        SELECT 1
        FROM child_profiles
        WHERE LOWER(TRIM(name)) = LOWER(TRIM(:name))
        AND birthDate = :birthDate
    )
""") // Comprueba si ya existe un perfil con el mismo nombre y fecha.
    suspend fun childExists(
        name: String,
        birthDate: Long
    ): Boolean

    @Query("SELECT * FROM child_profiles ORDER BY name ASC") // Consulta los perfiles ordenados por nombre.
    fun getAllChildren(): Flow<List<ChildProfileEntity>>

    @Query("DELETE FROM child_profiles WHERE id = :childId") // Elimina un perfil por su identificador.
    suspend fun deleteChildById(childId: Long)

    @Query("SELECT COUNT(*) FROM child_profiles") // Cuenta los perfiles registrados.
    suspend fun countChildren(): Int // Devuelve la cantidad de niños guardados.

    // Busca un perfil infantil mediante su ID.
    @Query("SELECT * FROM child_profiles WHERE id = :childId LIMIT 1")
    suspend fun getChildById(childId: Long): ChildProfileEntity?
}