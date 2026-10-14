package com.marcelomondaca.parentpatrol.ui.childprofiles

import androidx.compose.foundation.layout.Arrangement // Organiza los elementos.
import androidx.compose.foundation.layout.Column // Coloca elementos verticalmente.
import androidx.compose.foundation.layout.fillMaxSize // Ocupa el espacio disponible.
import androidx.compose.foundation.layout.padding // Agrega espacio interior.
import androidx.compose.material3.Text // Muestra texto en pantalla.
import androidx.compose.runtime.Composable // Declara una interfaz Compose.
import androidx.compose.ui.Alignment // Permite alinear elementos.
import androidx.compose.ui.Modifier // Permite modificar la interfaz.
import androidx.compose.ui.unit.dp // Define medidas de espacio.
import androidx.compose.foundation.lazy.LazyColumn // Muestra listas de elementos.
import androidx.compose.foundation.lazy.items // Recorre los perfiles infantiles.
import androidx.compose.runtime.collectAsState // Observa los datos del ViewModel.
import androidx.compose.runtime.getValue // Permite leer estados con by.
import androidx.lifecycle.viewmodel.compose.viewModel // Obtiene el ViewModel de la pantalla.
import androidx.compose.material3.Button // Botón para agregar otro niño.
import androidx.compose.foundation.layout.Spacer // Separa elementos.
import androidx.compose.foundation.layout.height // Define espacios verticales.


@Composable
fun ChildProfilesScreen(
    onAddChildClick: () -> Unit, // Abre el formulario de registro.
    viewModel: ChildProfilesViewModel = viewModel()
) {
    val children by viewModel.children.collectAsState() // Observa los perfiles guardados.

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Perfiles infantiles") // Título de la pantalla.

        Spacer(modifier = Modifier.height(16.dp)) // Separa el título del botón.

        Button(
            onClick = onAddChildClick // Solicita registrar otro niño.
        ) {
            Text("Agregar niño")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Separa el botón de la lista.


        if (children.isEmpty()) {
            Text(text = "Aún no hay perfiles registrados.") // Mensaje inicial.
        } else {
            LazyColumn {
                items(
                    items = children,
                    key = { child -> child.id } // Identifica cada perfil.
                ) { child ->
                    Text(
                        text = child.name,
                        modifier = Modifier.padding(12.dp)
                    ) // Muestra el nombre de cada niño.
                }
            }
        }
    }
}
