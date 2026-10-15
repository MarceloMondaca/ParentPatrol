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
import com.marcelomondaca.parentpatrol.ui.childprofiles.ChildProfilesScreen // Pantalla con todos los perfiles infantiles.
import com.marcelomondaca.parentpatrol.ui.childprofiles.ChildProfilesViewModel // Gestiona los perfiles infantiles.
import android.widget.Toast // Muestra mensajes breves al usuario.
import androidx.compose.ui.platform.LocalContext // Obtiene el contexto de Android.
import androidx.compose.runtime.LaunchedEffect // Ejecuta una acción al iniciar la pantalla.
import androidx.compose.runtime.mutableStateOf // Guarda un estado observable.
import androidx.compose.runtime.getValue // Permite leer estados con by.
import androidx.compose.runtime.setValue // Permite modificar estados con by.
import androidx.compose.runtime.remember // Conserva el estado entre recomposiciones.


@Composable // Indica que esta función crea interfaz con Compose.
fun ParentPatrolNavHost() { // Gestiona la navegación de ParentPatrol.

    val navController = rememberNavController() // Controla la navegación entre pantallas.
    val context = LocalContext.current // Contexto para mostrar mensajes.
    val profilesViewModel: ChildProfilesViewModel = viewModel() // Consulta los perfiles guardados.

    var initialRoute by remember { mutableStateOf<String?>(null) } // Espera la consulta inicial.

    LaunchedEffect(Unit) { // Se ejecuta una vez al entrar en esta composición.
        profilesViewModel.checkExistingProfiles { hasProfiles ->
            initialRoute = if (hasProfiles) "child_profiles" else "welcome"
        }
    }

    val startRoute = initialRoute ?: return // Espera la consulta y obtiene una ruta válida.

    NavHost( // Contenedor principal de navegación.
        navController = navController, // Asigna el controlador de navegación.
        startDestination = startRoute // Define la pantalla inicial según los perfiles guardados.

    ) {

        composable("welcome") { // Define la ruta de bienvenida.
            WelcomeScreen( // Muestra la pantalla de bienvenida.
                onStartClick = { // Acción al presionar Comenzar.
                    navController.navigate("child_profile") // Va al perfil del niño.
                }
            )
        }

        composable("child_profile") { // Pantalla para registrar un niño.
            val profilesViewModel: ChildProfilesViewModel = viewModel() // Gestiona los perfiles.

            ChildProfileScreen(
                onContinueClick = { name, birthDate -> // Recibe nombre y fecha.
                    profilesViewModel.addChild(
                        name = name,
                        birthDate = birthDate,

                        onSaved = { // Se ejecuta cuando Room guarda correctamente.
                            navController.navigate("child_profiles") {
                                popUpTo("child_profile") {
                                    inclusive = true // Evita regresar al formulario.
                                }
                            }
                        },

                        onDuplicate = { // Se ejecuta cuando el perfil ya existe.
                            Toast.makeText(
                                context,
                                "Este niño ya tiene un perfil registrado en ParentPatrol.",
                                Toast.LENGTH_LONG
                            ).show() // Informa al padre que el perfil está duplicado.
                        }
                    )
                }
            )
        }

        composable("screen_time") { // Define la ruta de tiempo de uso.
            ScreenTimeScreen(
                viewModel = viewModel(), // Obtiene el ViewModel de tiempo de pantalla.
                onContinueClick = { minutes -> // Recibe los minutos seleccionados.
                    navController.navigate("protection") // Navega a protección del menor.
                }
            )
        }

        composable("child_profiles") { // Pantalla principal de perfiles infantiles.
            ChildProfilesScreen(
                onAddChildClick = { // Permite registrar otro niño.
                    navController.navigate("child_profile")
                },
                onChildSelected = { childId -> // Recibe el ID del perfil seleccionado.
                    Toast.makeText(
                        context,
                        "Perfil seleccionado: $childId",
                        Toast.LENGTH_SHORT
                    ).show() // Confirma temporalmente la selección.
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