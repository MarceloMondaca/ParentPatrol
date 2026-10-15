
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

// -------------------------------------------------
// COMPONENTES MATERIAL DESIGN 3
// -------------------------------------------------

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

// -------------------------------------------------
// ESTADOS DE JETPACK COMPOSE
// -------------------------------------------------

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// -------------------------------------------------
// HERRAMIENTAS DE DISEÑO
// -------------------------------------------------

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// -------------------------------------------------
// VIEWMODEL
// -------------------------------------------------

import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Pantalla de configuración individual de un niño.
 *
 * Permite:
 * - Consultar el perfil infantil.
 * - Acceder a su configuración de tiempo de uso.
 * - Solicitar la eliminación definitiva del perfil.
 */
@Composable
fun ChildSettingsScreen(
    childId: Long,
    onConfigureScreenTime: (Long) -> Unit,
    onChildDeleted: () -> Unit,
    viewModel: ChildSettingsViewModel = viewModel()
) {

    // -------------------------------------------------
    // 1. ESTADOS DE LA PANTALLA
    // -------------------------------------------------

    // Observa los datos del niño seleccionado.
    val child by viewModel.child.collectAsState()

    // Controla la visibilidad del diálogo.
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    // -------------------------------------------------
    // 2. CARGAR PERFIL INFANTIL
    // -------------------------------------------------

    // Consulta el perfil cuando cambia su ID.
    LaunchedEffect(childId) {

        viewModel.loadChild(childId)

    }

    // -------------------------------------------------
    // 3. INTERFAZ PRINCIPAL
    // -------------------------------------------------

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Muestra un indicador mientras carga el perfil.
        if (child == null) {

            CircularProgressIndicator()

        } else {

            // -------------------------------------------------
            // 4. INFORMACIÓN DEL PERFIL
            // -------------------------------------------------

            // Nombre del niño seleccionado.
            Text(
                text = "Configuración de ${child?.name}",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción de las opciones disponibles.
            Text(
                text = "Aquí podrás configurar el tiempo de uso y la protección de este niño.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // -------------------------------------------------
            // 5. CONFIGURACIÓN DE TIEMPO DE USO
            // -------------------------------------------------

            // Abre la configuración del niño seleccionado.
            Button(
                onClick = {

                    // Envía el ID del niño a la navegación.
                    onConfigureScreenTime(childId)

                }
            ) {
                Text("Configurar tiempo de uso")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // -------------------------------------------------
            // 6. SEPARADOR DE OPCIONES
            // -------------------------------------------------

            HorizontalDivider()

            Spacer(modifier = Modifier.height(24.dp))

            // -------------------------------------------------
            // 7. ELIMINAR PERFIL INFANTIL
            // -------------------------------------------------

            // Solicita confirmar la eliminación.
            TextButton(
                onClick = {

                    // Abre el diálogo de confirmación.
                    showDeleteDialog = true

                }
            ) {

                Text(
                    text = "Eliminar este perfil",
                    color = MaterialTheme.colorScheme.error
                )

            } // Fin de TextButton.

        } // Fin de validación del perfil.

    } // Fin de Column.

    // -------------------------------------------------
    // 8. DIÁLOGO DE CONFIRMACIÓN
    // -------------------------------------------------

    if (showDeleteDialog && child != null) {

        AlertDialog(

            // Cierra el diálogo sin eliminar.
            onDismissRequest = {
                showDeleteDialog = false
            },

            // Título del diálogo.
            title = {
                Text("Eliminar este perfil")
            },

            // Advertencia de eliminación definitiva.
            text = {

                Text(
                    "¿Estás seguro de que deseas eliminar el perfil de ${child?.name}? " +
                            "Esta acción será definitiva y no se podrá deshacer."
                )

            },

            // -------------------------------------------------
            // 9. CONFIRMAR ELIMINACIÓN
            // -------------------------------------------------

            confirmButton = {

                Button(
                    onClick = {

                        // Cierra el diálogo de confirmación.
                        showDeleteDialog = false

                        // Elimina el perfil desde Room.
                        viewModel.deleteChild(
                            childId = childId,

                            onDeleted = {

                                // Notifica que terminó la eliminación.
                                onChildDeleted()

                            }
                        )

                    }
                ) {
                    Text("Eliminar definitivamente")
                }

            },

            // -------------------------------------------------
            // 10. CANCELAR ELIMINACIÓN
            // -------------------------------------------------

            dismissButton = {

                OutlinedButton(
                    onClick = {

                        // Cierra el diálogo sin eliminar.
                        showDeleteDialog = false

                    }
                ) {
                    Text("Cancelar")
                }

            }

        ) // Fin de AlertDialog.

    } // Fin del diálogo de eliminación.

} // Fin de ChildSettingsScreen.


