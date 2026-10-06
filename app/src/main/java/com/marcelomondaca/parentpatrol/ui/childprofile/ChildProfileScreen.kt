package com.marcelomondaca.parentpatrol.ui.childprofile

import androidx.compose.foundation.layout.Column // Organiza elementos verticalmente.
import androidx.compose.foundation.layout.Spacer // Crea espacios entre elementos.
import androidx.compose.foundation.layout.fillMaxSize // Ocupa toda la pantalla.
import androidx.compose.foundation.layout.fillMaxWidth // Ocupa todo el ancho disponible.
import androidx.compose.foundation.layout.height // Permite definir una altura.
import androidx.compose.foundation.layout.padding // Agrega espacio alrededor del contenido.
import androidx.compose.foundation.layout.safeDrawingPadding // Respeta las zonas seguras.
import androidx.compose.material3.MaterialTheme // Accede al tema visual de la app.
import androidx.compose.material3.OutlinedTextField // Campo de texto con borde.
import androidx.compose.material3.OutlinedTextFieldDefaults // Configura colores del campo.
import androidx.compose.material3.Text // Muestra texto en pantalla.
import androidx.compose.runtime.Composable // Permite crear componentes Compose.
import androidx.compose.runtime.getValue // Permite leer estados con "by".
import androidx.compose.runtime.mutableStateOf // Crea un estado modificable.
import androidx.compose.runtime.setValue // Permite modificar estados con "by".
import androidx.compose.ui.Modifier // Permite modificar componentes.
import androidx.compose.ui.unit.dp // Unidad de medida para dimensiones.
import androidx.compose.material3.DatePicker // Selector de fecha.
import androidx.compose.material3.DatePickerDialog // Ventana del calendario.
import androidx.compose.material3.TextButton // Botones del calendario.
import androidx.compose.foundation.clickable // Permite detectar el toque sobre el campo.
import androidx.compose.material3.rememberDatePickerState // Guarda el estado del calendario.
import androidx.compose.material3.ExperimentalMaterial3Api // Permite usar APIs experimentales de Material 3.
import androidx.compose.foundation.interaction.MutableInteractionSource // Controla la interacción táctil.
import androidx.compose.foundation.layout.Box // Permite superponer componentes.
import androidx.compose.runtime.remember // Conserva objetos durante la recomposición.
import java.time.Instant // Convierte la fecha seleccionada.
import java.time.ZoneId // Usa la zona horaria del dispositivo.
import java.time.format.DateTimeFormatter // Da formato DD/MM/AAAA.
import java.time.Period // Calcula la diferencia entre dos fechas.
import java.time.LocalDate // Obtiene la fecha actual.
import androidx.compose.material3.SelectableDates // Define qué fechas se pueden seleccionar.

@OptIn(ExperimentalMaterial3Api::class) // Habilita el uso del DatePicker experimental.

@Composable
fun ChildProfileScreen() { // Pantalla para configurar el perfil del niño.

    var childName by remember { mutableStateOf("") } // Guarda el nombre ingresado.
    var birthDate by remember { mutableStateOf("") } // Guarda la fecha de nacimiento.
    var childAge by remember { mutableStateOf<Int?>(null) } // Guarda la edad calculada.
    var showDatePicker by remember { mutableStateOf(false) } // Controla si mostramos el calendario.

    val initialDate = LocalDate.now().minusYears(5) // Abre el calendario naciemientos de 5 años atrás.

    val initialDateMillis = initialDate
        .atStartOfDay(ZoneId.of("UTC"))
        .toInstant()
        .toEpochMilli() // Convierte la fecha inicial a milisegundos.

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val birthDate = Instant
                    .ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDate()

                val age = Period.between(
                    birthDate,
                    LocalDate.now()
                ).years // Calcula la edad para esta fecha.

                return age in 5..17 // Permite únicamente edades entre 5 y 17 años.
            }
        }
    )

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

        Spacer(modifier = Modifier.height(24.dp)) // Separa título y campo.

        OutlinedTextField(
            value = childName, // Muestra el nombre actual.
            onValueChange = { childName = it }, // Actualiza el nombre.
            label = { Text("Nombre") },
            singleLine = true, // Mantiene el nombre en una línea.
            modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho.
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface, // Texto activo.
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface, // Texto inactivo.
                focusedLabelColor = MaterialTheme.colorScheme.primary, // Etiqueta activa.
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant, // Etiqueta inactiva.
                focusedBorderColor = MaterialTheme.colorScheme.primary, // Borde activo.
                unfocusedBorderColor = MaterialTheme.colorScheme.outline, // Borde inactivo.
                cursorColor = MaterialTheme.colorScheme.primary // Cursor.
            )
        )

        Spacer(modifier = Modifier.height(16.dp)) // Separa ambos campos.

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = birthDate,
                onValueChange = { }, // La fecha se selecciona desde el calendario.
                label = { Text("Fecha de nacimiento") },
                placeholder = { Text("DD/MM/AAAA") },
                singleLine = true,
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .matchParentSize() // Cubre exactamente el campo de fecha.
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        showDatePicker = true // Abre el calendario.
                    }
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Separa la fecha de la edad.

        Text(
            text = if (childAge != null) {
                "Edad: $childAge años"
            } else {
                "Edad: --"
            },
            style = MaterialTheme.typography.bodyLarge
        )
    } // Cierra Column.

    if (showDatePicker) { // Muestra el selector de fecha.
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false // Cierra el calendario.
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Instant
                                .ofEpochMilli(millis)
                                .atZone(ZoneId.of("UTC"))
                                .toLocalDate() // Convierte la selección a una fecha.

                            birthDate = selectedDate.format(
                                DateTimeFormatter.ofPattern("dd/MM/yyyy")
                            ) // Formatea la fecha para mostrarla.

                            childAge = Period.between(
                                selectedDate,
                                LocalDate.now()
                            ).years // Calcula la edad actual.
                        }

                        showDatePicker = false // Cierra el calendario.
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false // Cancela y cierra el calendario.
                    }
                ) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(
                state = datePickerState // Controla la fecha seleccionada.
            )
        }
    }

} // Cierra ChildProfileScreen.
