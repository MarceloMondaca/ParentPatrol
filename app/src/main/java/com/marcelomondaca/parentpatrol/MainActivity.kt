package com.marcelomondaca.parentpatrol

import android.os.Bundle // Guarda/recupera el estado de la Activity.
import androidx.activity.ComponentActivity // Clase base de nuestra Activity.
import androidx.activity.compose.setContent // Permite crear la interfaz con Compose.
import androidx.activity.enableEdgeToEdge // Permite usar toda la pantalla.
import com.marcelomondaca.parentpatrol.navigation.ParentPatrolNavHost // Navegación de la app.
import com.marcelomondaca.parentpatrol.ui.theme.ParentPatrolTheme // Tema visual de ParentPatrol.

class MainActivity : ComponentActivity() { // Activity principal de la aplicación.

    override fun onCreate(savedInstanceState: Bundle?) { // Se ejecuta al iniciar la Activity.
        super.onCreate(savedInstanceState) // Inicializa la Activity.

        enableEdgeToEdge() // Aprovecha toda la pantalla.

        setContent { // Inicia la interfaz con Jetpack Compose.
            ParentPatrolTheme { // Aplica el tema de ParentPatrol.
                ParentPatrolNavHost() // Inicia y controla la navegación.
            }
        }
    }
}

