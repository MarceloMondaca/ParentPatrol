package com.marcelomondaca.parentpatrol.navigation // Paquete de navegación.

import androidx.compose.runtime.Composable // Permite crear componentes Compose.
import androidx.navigation.compose.NavHost // Contiene las rutas de navegación.
import androidx.navigation.compose.composable // Define una ruta.
import androidx.navigation.compose.rememberNavController // Crea el controlador de navegación.
import com.marcelomondaca.parentpatrol.ui.childprofile.ChildProfileScreen // Pantalla del perfil del niño.
import com.marcelomondaca.parentpatrol.ui.protection.ProtectionScreen // Pantalla de protección del menor.
import com.marcelomondaca.parentpatrol.ui.screentime.ScreenTimeScreen // Pantalla de tiempo de uso.
import com.marcelomondaca.parentpatrol.ui.welcome.WelcomeScreen // Pantalla de bienvenida.
import androidx.lifecycle.viewmodel.compose.viewModel // Obtiene el ViewModel asociado a la pantalla.

@Composable // Indica que esta función crea interfaz con Compose.
fun ParentPatrolNavHost() { // Gestiona la navegación de ParentPatrol.

    val navController = rememberNavController() // Controla la navegación entre pantallas.

    NavHost( // Contenedor principal de navegación.
        navController = navController, // Asigna el controlador de navegación.
        startDestination = "welcome" // Define la pantalla inicial.
    ) {

        composable("welcome") { // Define la ruta de bienvenida.
            WelcomeScreen( // Muestra la pantalla de bienvenida.
                onStartClick = { // Acción al presionar Comenzar.
                    navController.navigate("child_profile") // Va al perfil del niño.
                }
            )
        }

        composable("child_profile") { // Define la ruta del perfil.
            ChildProfileScreen( // Muestra el perfil del niño.
                onContinueClick = { // Acción al presionar Continuar.
                    navController.navigate("screen_time") // Va a configurar el tiempo.
                }
            )
        }

        composable("screen_time") { // Define la ruta de tiempo de uso.
            ScreenTimeScreen( // Muestra la configuración de tiempo.
                onContinueClick = { minutes -> // Recibe los minutos seleccionados.
                    navController.navigate("protection") // Va a protección del menor.
                }
            )
        }

        composable("protection") { // Define la ruta de protección.
            ProtectionScreen( // Muestra la pantalla de protección.
                viewModel = viewModel() // Obtiene el ViewModel asociado a esta pantalla.
            )
        }
    } // Fin de las rutas del NavHost.
} // Fin de ParentPatrolNavHost.