package com.marcelomondaca.parentpatrol.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme // Detecta el tema del sistema.
import androidx.compose.material3.MaterialTheme // Tema principal de Material 3.
import androidx.compose.material3.darkColorScheme // Crea la paleta oscura.
import androidx.compose.material3.lightColorScheme // Crea la paleta clara.
import androidx.compose.runtime.Composable // Permite crear componentes Compose.
import androidx.compose.ui.graphics.Color // Permite definir colores.

// Colores para el modo claro.
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF3F51B5), // Azul principal.
    onPrimary = Color.White, // Texto sobre azul.
    primaryContainer = Color(0xFFDDE1FF), // Contenedores destacados.
    onPrimaryContainer = Color(0xFF00105C), // Texto en contenedores.

    secondary = Color(0xFF455A64), // Color secundario.
    onSecondary = Color.White, // Texto sobre secundario.

    background = Color(0xFFFFFBFF), // Fondo principal.
    onBackground = Color(0xFF1B1B1F), // Texto sobre fondo.

    surface = Color(0xFFFFFBFF), // Superficie de componentes.
    onSurface = Color(0xFF1B1B1F), // Texto principal.
    onSurfaceVariant = Color(0xFF46464F), // Texto secundario.

    outline = Color(0xFF777680) // Bordes de campos.
)

// Colores para el modo oscuro.
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBAC3FF), // Azul claro principal.
    onPrimary = Color(0xFF08218A), // Texto sobre azul.
    primaryContainer = Color(0xFF26399F), // Contenedores destacados.
    onPrimaryContainer = Color(0xFFDDE1FF), // Texto en contenedores.

    secondary = Color(0xFFBFC8CC), // Color secundario.
    onSecondary = Color(0xFF293236), // Texto sobre secundario.

    background = Color(0xFF121318), // Fondo oscuro.
    onBackground = Color(0xFFE4E1E9), // Texto sobre fondo.

    surface = Color(0xFF121318), // Superficie oscura.
    onSurface = Color(0xFFE4E1E9), // Texto principal.
    onSurfaceVariant = Color(0xFFC7C5D0), // Texto secundario.

    outline = Color(0xFF91909A) // Bordes de campos.
)

@Composable
fun ParentPatrolTheme(
    //darkTheme: Boolean = isSystemInDarkTheme(), // Sigue el modo del teléfono.
    darkTheme: Boolean = false, // Prueba temporal: fuerza el modo claro.
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme // Usa colores oscuros.
    } else {
        LightColorScheme // Usa colores claros.
    }

    MaterialTheme(
        colorScheme = colorScheme, // Aplica nuestra paleta.
        typography = Typography, // Aplica nuestra tipografía.
        content = content // Muestra el contenido de la app.
    )
}