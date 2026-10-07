package com.marcelomondaca.parentpatrol.ui.protection // Paquete de la pantalla de protección.

import androidx.compose.foundation.layout.Column // Organiza elementos verticalmente.
import androidx.compose.foundation.layout.fillMaxSize // Ocupa toda la pantalla.
import androidx.compose.foundation.layout.padding // Agrega espacio interior.
import androidx.compose.foundation.layout.safeDrawingPadding // Respeta cámara y barras del sistema.
import androidx.compose.material3.MaterialTheme // Permite usar estilos del tema.
import androidx.compose.material3.Text // Permite mostrar texto.
import androidx.compose.runtime.Composable // Permite crear componentes Compose.
import androidx.compose.ui.Modifier // Permite modificar componentes.
import androidx.compose.ui.unit.dp // Unidad de medida.
import androidx.compose.foundation.layout.Spacer // Crea espacios entre elementos.
import androidx.compose.foundation.layout.height // Permite definir una altura.
import androidx.compose.material3.Switch // Interruptor de activación.
import androidx.compose.runtime.getValue // Permite leer estados con "by".
import androidx.compose.runtime.mutableStateOf // Crea un estado modificable.
import androidx.compose.runtime.remember // Conserva el estado.
import androidx.compose.runtime.setValue // Permite modificar estados con "by".
import androidx.compose.foundation.layout.Row // Organiza elementos horizontalmente.
import androidx.compose.foundation.layout.Arrangement // Distribuye el espacio entre elementos.
import androidx.compose.ui.Alignment // Permite alinear los elementos.
import androidx.compose.foundation.layout.fillMaxWidth // Ocupa todo el ancho disponible.

@Composable // Indica que esta función crea interfaz con Compose.
fun ProtectionScreen() { // Crea la pantalla de protección.

    var blockAdultContent by remember {
        mutableStateOf(false)
    } // Guarda si el bloqueo de contenido adulto está activado.

    var blockInappropriateApps by remember {
        mutableStateOf(false)
    } // Guarda si el bloqueo de apps no recomendadas está activado.

    Column( // Contenedor vertical de la pantalla.
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla.
            .safeDrawingPadding() // Evita cámara y barras del sistema.
            .padding(24.dp) // Agrega margen interior.
    )

    {
         Text( // Muestra el título.
            text = "Configurar protección", // Título de la pantalla.
            style = MaterialTheme.typography.headlineMedium // Estilo del título.
         )


         Spacer(modifier = Modifier.height(24.dp)) // Separa el título de la opción.

        Row(
            modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho disponible.
            horizontalArrangement = Arrangement.SpaceBetween, // Separa texto e interruptor.
            verticalAlignment = Alignment.CenterVertically // Los alinea verticalmente.
        ) {

            Text(
                text = "Bloquear contenido para adultos",
                style = MaterialTheme.typography.bodyLarge
            )

            Switch(
                checked = blockAdultContent, // Muestra el estado actual.
                onCheckedChange = { isChecked ->
                    blockAdultContent = isChecked // Guarda el nuevo estado.
                }
            )
         }

        Spacer(modifier = Modifier.height(16.dp)) // Separa las opciones.

        Row(
            modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho disponible.
            horizontalArrangement = Arrangement.SpaceBetween, // Separa texto e interruptor.
            verticalAlignment = Alignment.CenterVertically // Los alinea verticalmente.
        )
        {
            Text(
                text = "Bloquear aplicaciones no recomendadas",
                style = MaterialTheme.typography.bodyLarge
            )

            Switch(
                checked = blockInappropriateApps, // Muestra el estado actual.
                onCheckedChange = { isChecked ->
                    blockInappropriateApps = isChecked // Guarda el nuevo estado.
                }
            )
        }

    } // Fin Column.
}