
package com.marcelomondaca.parentpatrol.ui.childsettings

// -------------------------------------------------
// COMPONENTES DE INTERFAZ
// -------------------------------------------------

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

// Componentes Material 3.
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

// -------------------------------------------------
// ESTADOS Y VIEWMODEL
// -------------------------------------------------

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// Herramientas de diseño.
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Obtiene el ViewModel de la pantalla.
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Configuración individual de un perfil infantil.
 *
 * Muestra los datos del niño y permite
 * solicitar la eliminación de su perfil.
 */
@Composable
fun ChildSettingsScreen(
    childId: Long,
    onChildDeleted: () -> Unit, // Avisa cuando se elimina el perfil.
    viewModel: ChildSettingsViewModel = viewModel()
) {

    // Observa el perfil seleccionado.
    val child by viewModel.child.collectAsState()

    // Controla el diálogo de eliminación.
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    // -------------------------------------------------
    // 1. CARGAR PERFIL INFANTIL
    // -------------------------------------------------

    LaunchedEffect(childId) {

        // Consulta el perfil en Room.
        viewModel.loadChild(childId)

    }

    // -------------------------------------------------
    // 2. INTERFAZ PRINCIPAL
    // -------------------------------------------------

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (child == null) {

            // Muestra un indicador durante la consulta.
            CircularProgressIndicator()

        } else {

            // Nombre del perfil infantil.
            Text(
                text = "Configuración de ${child?.name}",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción de las futuras opciones.
            Text(
                text = "Aquí podrás configurar el tiempo de uso y la protección de este niño.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Separa las opciones de configuración.
            HorizontalDivider()

            Spacer(modifier = Modifier.height(24.dp))

            // Solicita eliminar el perfil.
            TextButton(
                onClick = {
                    showDeleteDialog = true
                }
            ) {

                Text(
                    text = "Eliminar perfil infantil",
                    color = MaterialTheme.colorScheme.error
                )

            } // Fin de TextButton.

        } // Fin de validación del perfil.

    } // Fin de Column.

    // -------------------------------------------------
    // 3. CONFIRMACIÓN DE ELIMINACIÓN
    // -------------------------------------------------

    if (showDeleteDialog && child != null) {

        AlertDialog(

            // Cierra el diálogo al tocar fuera.
            onDismissRequest = {
                showDeleteDialog = false
            },

            // Título de confirmación.
            title = {
                Text("Eliminar perfil infantil")
            },

            // Advierte sobre la eliminación definitiva.
            text = {
                Text(
                    "¿Estás seguro de que deseas eliminar el perfil de ${child?.name}? " +
                            "Esta acción será definitiva y no se podrá deshacer."
                )
            },

            // Botón para confirmar la eliminación.
            confirmButton = {


                Button(
                    onClick = {

                        // Cierra el diálogo de confirmación.
                        showDeleteDialog = false

                        // Elimina el perfil seleccionado desde Room.
                        viewModel.deleteChild(
                            childId = childId,
                            onDeleted = {

                                // Avisa a la navegación que terminó la eliminación.
                                onChildDeleted()

                            }
                        )

                    }
                ) {
                    Text("Eliminar definitivamente")
                }


            },

            // Botón para cancelar la eliminación.
            dismissButton = {

                OutlinedButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text("Cancelar")
                }

            }

        ) // Fin de AlertDialog.

    } // Fin del diálogo de eliminación.

} // Fin de ChildSettingsScreen.

