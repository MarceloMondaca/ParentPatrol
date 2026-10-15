
package com.marcelomondaca.parentpatrol.ui.childprofiles

// Componentes para organizar la interfaz.
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width // Espacio horizontal.

// Componentes para mostrar listas de perfiles.
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

// Componentes visuales de Material 3.
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text


// Estados y funciones de Jetpack Compose.
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

// Herramientas para alinear y dar formato.
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Permite obtener el ViewModel de la pantalla.
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Pantalla principal de selección de perfiles infantiles.
 *
 * Muestra los perfiles registrados en Room y permite:
 * - Seleccionar un niño mediante su ID.
 * - Abrir el formulario para agregar otro niño.
 */
@Composable
fun ChildProfilesScreen(
    onAddChildClick: () -> Unit, // Abre el formulario de registro.
    onChildSelected: (Long) -> Unit, // Entrega el ID del perfil seleccionado.
    viewModel: ChildProfilesViewModel = viewModel()
) {

    // Observa automáticamente los perfiles almacenados en Room.
    val children by viewModel.children.collectAsState()

    // Contenedor principal de la pantalla.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // -------------------------------------------------
        // IDENTIDAD VISUAL DE PARENTPATROL
        // -------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Escudo representativo de la protección infantil.
            Text(
                text = "🛡️",
                style = MaterialTheme.typography.headlineMedium
            )

            // Espacio entre el escudo y el nombre.
            Spacer(modifier = Modifier.width(10.dp))

            // Nombre de nuestra aplicación.
            Text(
                text = "ParentPatrol",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

        } // Fin del encabezado.

        Spacer(modifier = Modifier.height(6.dp))

        // Eslogan oficial de ParentPatrol.
        Text(
            text = "Protege. Guía. Acompaña.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Separa la identidad visual del título principal.
        Spacer(modifier = Modifier.height(32.dp))

        // -------------------------------------------------
        // 1. TÍTULO PRINCIPAL
        // -------------------------------------------------


        Text(
            text = "¿Quién usará el teléfono?",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Descripción para orientar al padre.
        Text(
            text = "Selecciona el perfil infantil que deseas configurar.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // -------------------------------------------------
        // 2. LISTA DE PERFILES INFANTILES
        // -------------------------------------------------

        if (children.isEmpty()) {

            // Mensaje cuando todavía no existen perfiles.
            Text(
                text = "Aún no hay perfiles registrados.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        } else {

            // Lista dinámica de perfiles registrados.
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {

                items(
                    items = children,
                    key = { child -> child.id } // ID único de Room.
                ) { child ->

                    // Tarjeta individual de cada niño.
                    Card(
                        onClick = {
                            onChildSelected(child.id) // Envía el ID seleccionado.
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                    ) {

                        // Organiza el nombre y la flecha horizontalmente.
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // Nombre del niño registrado.
                            Text(
                                text = child.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f)
                            )

                            // Flecha que indica que el perfil se puede seleccionar.
                            Text(
                                text = "›",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.primary
                            )

                        } // Fin de Row.

                    } // Fin de Card.

                } // Fin de items.

            } // Fin de LazyColumn.

        } // Fin de if / else.

        // -------------------------------------------------
        // 3. BOTÓN PARA AGREGAR OTRO NIÑO
        // -------------------------------------------------

        Spacer(modifier = Modifier.height(20.dp))

        // Este botón permanece fuera del if / else.
        // Por eso aparece aunque todavía no existan perfiles.
        OutlinedButton(
            onClick = onAddChildClick,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {

            Text(
                text = "+  Agregar otro niño",
                style = MaterialTheme.typography.labelLarge
            )

        } // Fin de OutlinedButton.

    } // Fin de Column.

} // Fin de ChildProfilesScreen.
