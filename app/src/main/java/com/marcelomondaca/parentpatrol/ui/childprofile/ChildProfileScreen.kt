package com.marcelomondaca.parentpatrol.ui.childprofile

import androidx.compose.foundation.layout.Column // Organiza elementos verticalmente.
import androidx.compose.foundation.layout.fillMaxSize // Ocupa toda la pantalla.
import androidx.compose.foundation.layout.padding // Agrega espacio alrededor del contenido.
import androidx.compose.foundation.layout.safeDrawingPadding // Respeta las zonas seguras del dispositivo.
import androidx.compose.material3.MaterialTheme // Accede al tema visual de la app.
import androidx.compose.material3.Text // Muestra texto en pantalla.
import androidx.compose.runtime.Composable // Permite crear componentes de Compose.
import androidx.compose.ui.Modifier // Permite modificar componentes.
import androidx.compose.ui.unit.dp // Unidad de medida para dimensiones.

@Composable
fun ChildProfileScreen() { // Pantalla para configurar el perfil del niño.

    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla.
            .safeDrawingPadding() // Evita cámara y barras del sistema.
            .padding(24.dp) // Agrega margen interior.
    ) {
        Text(
            text = "Configurar perfil del niño",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
