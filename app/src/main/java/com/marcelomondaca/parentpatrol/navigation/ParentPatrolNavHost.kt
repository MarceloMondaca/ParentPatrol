package com.marcelomondaca.parentpatrol.navigation

import androidx.compose.runtime.Composable // Permite crear componentes de Compose.
import androidx.navigation.compose.NavHost // Contenedor de las rutas de navegación.
import androidx.navigation.compose.composable // Define una ruta de navegación.
import androidx.navigation.compose.rememberNavController // Crea el controlador de navegación.
import com.marcelomondaca.parentpatrol.ui.childprofile.ChildProfileScreen // Pantalla del perfil del niño.
import com.marcelomondaca.parentpatrol.ui.screentime.ScreenTimeScreen // Pantalla de tiempo de uso.
import com.marcelomondaca.parentpatrol.ui.welcome.WelcomeScreen // Pantalla de bienvenida.

@Composable
fun ParentPatrolNavHost() {

    val navController = rememberNavController() // Controla la navegación entre pantallas.

    NavHost(
        navController = navController,
        startDestination = "welcome" // Define la pantalla inicial.
    ) {

        composable("welcome") { // Ruta de bienvenida.
            WelcomeScreen(
                onStartClick = {
                    navController.navigate("child_profile") // Va al perfil del niño.
                }
            )
        }

        composable("child_profile") { // Ruta del perfil del niño.
            ChildProfileScreen(
                onContinueClick = {
                    navController.navigate("screen_time") // Va a configurar el tiempo de uso.
                }
            )
        }

        composable("screen_time") { // Ruta de configuración del tiempo de uso.
            ScreenTimeScreen()
        }
    }
}