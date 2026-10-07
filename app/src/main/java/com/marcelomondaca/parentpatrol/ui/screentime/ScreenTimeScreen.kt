package com.marcelomondaca.parentpatrol.ui.screentime

import androidx.compose.foundation.layout.Column // Organiza elementos verticalmente.
import androidx.compose.foundation.layout.Spacer // Crea espacios entre elementos.
import androidx.compose.foundation.layout.fillMaxSize // Ocupa toda la pantalla.
import androidx.compose.foundation.layout.fillMaxWidth // Ocupa todo el ancho disponible.
import androidx.compose.foundation.layout.height // Permite definir una altura.
import androidx.compose.foundation.layout.padding // Agrega espacio interior.
import androidx.compose.foundation.layout.safeDrawingPadding // Respeta las zonas seguras.
import androidx.compose.material3.MaterialTheme // Accede al tema visual de ParentPatrol.
import androidx.compose.material3.OutlinedButton // Botón con borde.
import androidx.compose.material3.Text // Muestra texto.
import androidx.compose.runtime.Composable // Permite crear componentes Compose.
import androidx.compose.runtime.getValue // Permite leer estados con "by".
import androidx.compose.runtime.mutableStateOf // Crea un estado modificable.
import androidx.compose.runtime.remember // Conserva el estado.
import androidx.compose.runtime.setValue // Permite modificar estados con "by".
import androidx.compose.ui.Modifier // Permite modificar componentes.
import androidx.compose.ui.unit.dp // Unidad de medida.
import androidx.compose.material3.Button // Botón relleno para la opción seleccionada.

@Composable
  fun ScreenTimeScreen(
    onContinueClick: (Int) -> Unit // Envía los minutos seleccionados.
  ){
    var selectedMinutes by remember {
        mutableStateOf<Int?>(null)
    } // Guarda el tiempo seleccionado.

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp)
    ) {

        Text(
            text = "Configurar tiempo de uso",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¿Cuánto tiempo podrá usar el dispositivo?",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (selectedMinutes == 30) {
            Button(
                onClick = { selectedMinutes = 30 },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("30 minutos")
            }
        } else {
            OutlinedButton(
                onClick = { selectedMinutes = 30 },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("30 minutos")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (selectedMinutes == 60) {
            Button(
                onClick = { selectedMinutes = 60 },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("60 minutos")
            }
        } else {
            OutlinedButton(
                onClick = { selectedMinutes = 60 },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("60 minutos")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (selectedMinutes == 90) {
            Button(
                onClick = { selectedMinutes = 90 },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("90 minutos")
            }
        } else {
            OutlinedButton(
                onClick = { selectedMinutes = 90 },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("90 minutos")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                selectedMinutes?.let { minutes ->
                    onContinueClick(minutes) // Envía el tiempo seleccionado.
                }
            },
            enabled = selectedMinutes != null, // Solo se habilita al elegir un tiempo.
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }
    } // fin Column
} // fin fun ScreenTimeScreen()