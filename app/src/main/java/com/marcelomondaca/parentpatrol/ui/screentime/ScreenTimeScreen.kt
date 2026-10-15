
package com.marcelomondaca.parentpatrol.ui.screentime

// -------------------------------------------------
// COMPONENTES DE INTERFAZ
// -------------------------------------------------

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding

// -------------------------------------------------
// MATERIAL DESIGN 3
// -------------------------------------------------

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text

// -------------------------------------------------
// ESTADOS DE JETPACK COMPOSE
// -------------------------------------------------

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

// -------------------------------------------------
// HERRAMIENTAS DE DISEÑO
// -------------------------------------------------

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Permite configurar el tiempo de uso
 * de un perfil infantil específico.
 */
@Composable
fun ScreenTimeScreen(
    childId: Long,
    viewModel: ScreenTimeViewModel,
    onContinueClick: (Int) -> Unit
) {

    // -------------------------------------------------
    // 1. CARGAR TIEMPO DEL NIÑO
    // -------------------------------------------------

    // Consulta la configuración individual.
    LaunchedEffect(childId) {

        viewModel.loadChildTime(childId)

    }

    // Minutos seleccionados actualmente.
    val selectedMinutes = viewModel.selectedMinutes

    // -------------------------------------------------
    // 2. INTERFAZ PRINCIPAL
    // -------------------------------------------------

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp)
    ) {

        // Título de la pantalla.
        Text(
            text = "Configurar tiempo de uso",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Instrucción para el adulto.
        Text(
            text = "¿Cuánto tiempo podrá usar el dispositivo?",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // -------------------------------------------------
        // 3. OPCIÓN DE 30 MINUTOS
        // -------------------------------------------------

        if (selectedMinutes == 30) {

            Button(
                onClick = {
                    viewModel.saveSelectedMinutes(30)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("30 minutos")
            }

        } else {

            OutlinedButton(
                onClick = {
                    viewModel.saveSelectedMinutes(30)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("30 minutos")
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        // -------------------------------------------------
        // 4. OPCIÓN DE 60 MINUTOS
        // -------------------------------------------------

        if (selectedMinutes == 60) {

            Button(
                onClick = {
                    viewModel.saveSelectedMinutes(60)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("60 minutos")
            }

        } else {

            OutlinedButton(
                onClick = {
                    viewModel.saveSelectedMinutes(60)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("60 minutos")
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        // -------------------------------------------------
        // 5. OPCIÓN DE 90 MINUTOS
        // -------------------------------------------------

        if (selectedMinutes == 90) {

            Button(
                onClick = {
                    viewModel.saveSelectedMinutes(90)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("90 minutos")
            }

        } else {

            OutlinedButton(
                onClick = {
                    viewModel.saveSelectedMinutes(90)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("90 minutos")
            }

        }

        Spacer(modifier = Modifier.height(32.dp))

        // -------------------------------------------------
        // 6. CONTINUAR
        // -------------------------------------------------

        Button(
            onClick = {

                // Continúa si existe un tiempo seleccionado.
                selectedMinutes?.let { minutes ->

                    onContinueClick(minutes)

                }

            },
            enabled = selectedMinutes != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }

    } // Fin de Column.

} // Fin de ScreenTimeScreen.
