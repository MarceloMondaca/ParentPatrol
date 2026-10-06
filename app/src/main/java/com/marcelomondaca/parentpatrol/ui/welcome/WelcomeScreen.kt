package com.marcelomondaca.parentpatrol.ui.welcome

import androidx.compose.foundation.layout.Arrangement // Organiza elementos dentro del Column.
import androidx.compose.foundation.layout.Column // Organiza elementos verticalmente.
import androidx.compose.foundation.layout.Spacer // Crea espacios entre elementos.
import androidx.compose.foundation.layout.fillMaxSize // Ocupa todo el espacio disponible.
import androidx.compose.foundation.layout.fillMaxWidth // Ocupa todo el ancho disponible.
import androidx.compose.foundation.layout.height // Permite definir una altura.
import androidx.compose.foundation.layout.padding // Agrega espacio alrededor del contenido.
import androidx.compose.material3.Button // Botón de Material 3.
import androidx.compose.material3.MaterialTheme // Accede al tema visual de la app.
import androidx.compose.material3.Text // Muestra texto en pantalla.
import androidx.compose.runtime.Composable // Permite crear componentes de Compose.
import androidx.compose.ui.Alignment // Permite alinear elementos.
import androidx.compose.ui.Modifier // Permite modificar componentes.
import androidx.compose.ui.text.font.FontWeight // Permite cambiar el grosor del texto.
import androidx.compose.ui.text.style.TextAlign // Permite alinear el texto.
import androidx.compose.ui.unit.dp // Unidad de medida para dimensiones.

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier, // Permite personalizar la pantalla desde fuera.
    onStartClick: () -> Unit // Acción al presionar "Comenzar".
) {
    Column(
        modifier = modifier
            .fillMaxSize() // Ocupa toda la pantalla.
            .padding(24.dp), // Margen interior de la pantalla.
        verticalArrangement = Arrangement.Center, // Centra verticalmente.
        horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente.
    ) {
        Text(
            text = "ParentPatrol",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp)) // Espacio pequeño.

        Text(
            text = "Protege. Guía. Acompaña.",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(24.dp)) // Espacio medio.

        Text(
            text = "Acompaña y protege la experiencia digital de tus hijos.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp)) // Espacio antes del botón.

        Button(
            onClick = onStartClick, // Ejecuta la acción de navegación.
            modifier = Modifier.fillMaxWidth() // Botón ocupa todo el ancho.
        ) {
            Text("Comenzar")
        }
    }
}