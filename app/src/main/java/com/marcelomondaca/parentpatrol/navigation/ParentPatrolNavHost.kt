
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
 * Permite registrar, seleccionar, configurar
 * y eliminar perfiles infantiles.
 */
@Composable
fun ParentPatrolNavHost() {

    // Controlador de navegación.
    val navController = rememberNavController()

    // Contexto para mostrar mensajes.
    val context = LocalContext.current

    // ViewModel de perfiles infantiles.
    val profilesViewModel: ChildProfilesViewModel = viewModel()

    // -------------------------------------------------
    // 1. PANTALLA INICIAL
    // -------------------------------------------------

    // Espera el resultado de Room.
    var initialRoute by remember {
        mutableStateOf<String?>(null)
    }

    // Comprueba si existen perfiles.
    LaunchedEffect(Unit) {

        profilesViewModel.checkExistingProfiles { hasProfiles ->

            initialRoute = if (hasProfiles) {
                "child_profiles"
            } else {
                "welcome"
            }

        }

    }

    // Espera una ruta válida.
    val startRoute = initialRoute ?: return

    // -------------------------------------------------
    // 2. NAVEGACIÓN PRINCIPAL
    // -------------------------------------------------

    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {

        // -------------------------------------------------
        // 3. BIENVENIDA
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
        // 4. REGISTRO INFANTIL
        // -------------------------------------------------

        composable("child_profile") {

            // ViewModel del formulario.
            val childProfileViewModel: ChildProfilesViewModel = viewModel()

            ChildProfileScreen(
                onContinueClick = { name, birthDate ->

                    // Guarda el perfil en Room.
                    childProfileViewModel.addChild(
                        name = name,
                        birthDate = birthDate,

                        // Perfil guardado correctamente.
                        onSaved = {

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
        // 5. LISTA DE PERFILES
        // -------------------------------------------------

        composable("child_profiles") {

            ChildProfilesScreen(

                // Registra otro niño.
                onAddChildClick = {
                    navController.navigate("child_profile")
                },

                // Abre el perfil seleccionado.
                onChildSelected = { childId ->

                    navController.navigate("child_settings/$childId")

                }

            )

        } // Fin de child_profiles.

        // -------------------------------------------------
        // 6. CONFIGURACIÓN INFANTIL
        // -------------------------------------------------

        composable("child_settings/{childId}") { backStackEntry ->

            // Recupera el ID del niño.
            val childId = backStackEntry.arguments
                ?.getString("childId")
                ?.toLongOrNull()

            // Comprueba que el ID sea válido.
            if (childId != null) {

                ChildSettingsScreen(
                    childId = childId,

                    // Se ejecuta después de eliminar.
                    onChildDeleted = {

                        // Regresa a la lista de perfiles.
                        navController.navigate("child_profiles") {

                            // Retira la configuración del historial.
                            popUpTo("child_profiles") {
                                inclusive = true
                            }

                            // Evita duplicar el destino.
                            launchSingleTop = true

                        }

                    }
                )

            }

        } // Fin de child_settings.

        // -------------------------------------------------
        // 7. TIEMPO DE USO
        // -------------------------------------------------

        composable("screen_time") {

            ScreenTimeScreen(
                viewModel = viewModel(),

                // Continúa hacia protección.
                onContinueClick = { _ ->

                    navController.navigate("protection")

                }
            )

        } // Fin de screen_time.

        // -------------------------------------------------
        // 8. PROTECCIÓN INFANTIL
        // -------------------------------------------------

        composable("protection") {

            ProtectionScreen(
                viewModel = viewModel()
            )

        } // Fin de protection.

    } // Fin de NavHost.

} // Fin de ParentPatrolNavHost.
