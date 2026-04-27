package mx.edu.itson.aplicacion_estancia

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable ("inicio") {
            InicioSesionScreen(navController = navController)
        }

        composable("formulario") {
            FormularioDeContacto(navController = navController)
        }

        composable("menuPrincipal") {
            PantallaMenuPrincipal(navController = navController)
        }

        composable ("listaPacientes") {
            PantallaListaPacientes(navController = navController)
        }

        composable("registroPaciente") {
            PantallaFormularioPaciente(navController = navController)
        }

        composable("seguimiento/{nombre}") { backStackEntry ->
            val nombreSeleccionado = backStackEntry.arguments?.getString("nombre") ?: "Paciente"
            PantallaSeguimientoResultados(nombreSeleccionado)
        }

        composable ("selectorEvaluacion"){
            PantallaSelectorEvaluacion(navController = navController)
        }

        composable(
            route = "formularioMMSE/{pacienteNombre}",
            arguments = listOf(navArgument("pacienteNombre") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("pacienteNombre") ?: "Paciente"
            PantallaFormularioMMSE(nombre, navController)
        }

        composable(
            route = "formularioTinetti/{pacienteNombre}",
            arguments = listOf(navArgument("pacienteNombre") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("pacienteNombre") ?: "Paciente"
            PantallaFormularioTinetti(nombre, navController)
        }

        composable(
            route = "pantallaResumen/{nombre}/{prueba}/{puntos}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("prueba") { type = NavType.StringType },
                navArgument("puntos") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val prueba = backStackEntry.arguments?.getString("prueba") ?: ""
            val puntos = backStackEntry.arguments?.getInt("puntos") ?: 0

            PantallaResumenEvaluacion(navController, nombre, prueba, puntos)
        }
    }
}