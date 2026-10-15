
package com.marcelomondaca.parentpatrol.navigation

// -------------------------------------------------
// ANDROID
// -------------------------------------------------

import android.widget.Toast

// -------------------------------------------------
// JETPACK COMPOSE
// -------------------------------------------------

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

// -------------------------------------------------
// NAVEGACIÓN Y VIEWMODEL
// -------------------------------------------------

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// -------------------------------------------------
// PANTALLAS DE PARENTPATROL
// -------------------------------------------------

import com.marcelomondaca.parentpatrol.ui.childprofile.ChildProfileScreen
import com.marcelomondaca.parentpatrol.ui.childprofiles.ChildProfilesScreen
import com.marcelomondaca.parentpatrol.ui.childprofiles.ChildProfilesViewModel
import com.marcelomondaca.parentpatrol.ui.childsettings.ChildSettingsScreen
import com.marcelomondaca.parentpatrol.ui.protection.ProtectionScreen
import com.marcelomondaca.parentpatrol.ui.screentime.ScreenTimeScreen
import com.marcelomondaca.parentpatrol.ui.welcome.WelcomeScreen

/**
 * Gestiona la navegación principal de ParentPatrol.
 *
 * Permite:
 * - Registrar perfiles infantiles.
 * - Seleccionar y configurar cada niño.
 * - Eliminar perfiles definitivamente.
 * - Acceder al tiempo de uso individual.
 */
@Composable
fun ParentPatrolNavHost() {

    // Controlador principal de navegación.
    val navController = rememberNavController()

    // Contexto para mostrar mensajes.
    val context = LocalContext.current

    // ViewModel de perfiles infantiles.
    val profilesViewModel: ChildProfilesViewModel = viewModel()

    // -------------------------------------------------
    // 1. DETERMINAR PANTALLA INICIAL
    // -------------------------------------------------

    // Ruta inicial pendiente de consultar.
    var initialRoute by remember {
        mutableStateOf<String?>(null)
    }

    // Comprueba si existen perfiles en Room.
    LaunchedEffect(Unit) {

        profilesViewModel.checkExistingProfiles { hasProfiles ->

            // Selecciona la pantalla inicial.
            initialRoute = if (hasProfiles) {
                "child_profiles"
            } else {
                "welcome"
            }

        }

    }

    // Espera el resultado de Room.
    val startRoute = initialRoute ?: return

    // -------------------------------------------------
    // 2. CONTENEDOR PRINCIPAL DE NAVEGACIÓN
    // -------------------------------------------------

    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {

        // -------------------------------------------------
        // 3. PANTALLA DE BIENVENIDA
        // -------------------------------------------------

        composable("welcome") {

            WelcomeScreen(
                onStartClick = {

                    // Abre el registro infantil.
                    navController.navigate("child_profile")

                }
            )

        } // Fin de welcome.

        // -------------------------------------------------
        // 4. REGISTRO DE PERFIL INFANTIL
        // -------------------------------------------------

        composable("child_profile") {

            // ViewModel del formulario.
            val childProfileViewModel: ChildProfilesViewModel = viewModel()

            ChildProfileScreen(
                onContinueClick = { name, birthDate ->

                    // Guarda el nuevo perfil en Room.
                    childProfileViewModel.addChild(
                        name = name,
                        birthDate = birthDate,

                        // Perfil guardado correctamente.
                        onSaved = {

                            // Regresa a la lista de perfiles.
                            navController.navigate("child_profiles") {

                                // Retira el formulario del historial.
                                popUpTo("child_profile") {
                                    inclusive = true
                                }

                            }

                        },

                        // Perfil duplicado.
                        onDuplicate = {

                            Toast.makeText(
                                context,
                                "Este niño ya tiene un perfil registrado en ParentPatrol.",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    )

                }
            )

        } // Fin de child_profile.

        // -------------------------------------------------
        // 5. LISTA DE PERFILES INFANTILES
        // -------------------------------------------------

        composable("child_profiles") {

            ChildProfilesScreen(

                // Abre el formulario de registro.
                onAddChildClick = {

                    navController.navigate("child_profile")

                },

                // Abre la configuración del niño.
                onChildSelected = { childId ->

                    navController.navigate("child_settings/$childId")

                }

            )

        } // Fin de child_profiles.

        // -------------------------------------------------
        // 6. CONFIGURACIÓN INDIVIDUAL DEL NIÑO
        // -------------------------------------------------

        composable("child_settings/{childId}") { backStackEntry ->

            // Recupera el ID del perfil seleccionado.
            val childId = backStackEntry.arguments
                ?.getString("childId")
                ?.toLongOrNull()

            // Verifica que el ID sea válido.
            if (childId != null) {

                ChildSettingsScreen(
                    childId = childId,

                    // Abre el tiempo de uso individual.
                    onConfigureScreenTime = { selectedChildId ->

                        navController.navigate(
                            "screen_time/$selectedChildId"
                        )

                    },

                    // Se ejecuta después de eliminar el perfil.
                    onChildDeleted = {

                        // Regresa a la lista de perfiles.
                        navController.navigate("child_profiles") {

                            // Retira la lista anterior del historial.
                            popUpTo("child_profiles") {
                                inclusive = true
                            }

                            // Evita duplicar el destino.
                            launchSingleTop = true

                        }

                    }
                )

            } // Fin de validación.

        } // Fin de child_settings.

        // -------------------------------------------------
        // 7. CONFIGURACIÓN DE TIEMPO DE USO
        // -------------------------------------------------

        composable("screen_time/{childId}") { backStackEntry ->

            // Recupera el ID del niño seleccionado.
            val childId = backStackEntry.arguments
                ?.getString("childId")
                ?.toLongOrNull()

            // Verifica que el ID sea válido.
            if (childId != null) {

                ScreenTimeScreen(
                    childId = childId,
                    viewModel = viewModel(),

                    // Continúa hacia protección.
                    onContinueClick = { _ ->

                        navController.navigate("protection")

                    }
                )

            } // Fin de validación.

        } // Fin de screen_time.

        // -------------------------------------------------
        // 8. CONFIGURACIÓN DE PROTECCIÓN INFANTIL
        // -------------------------------------------------

        composable("protection") {

            ProtectionScreen(
                viewModel = viewModel()
            )

        } // Fin de protection.

    } // Fin de NavHost.

} // Fin de ParentPatrolNavHost.

