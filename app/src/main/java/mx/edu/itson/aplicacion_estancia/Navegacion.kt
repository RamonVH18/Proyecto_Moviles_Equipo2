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

        composable("login") {
            PantallaLogin(navController = navController)
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

        composable(
            route = "seguimiento/{idPaciente}/{nombre}",
            arguments = listOf(
                navArgument("idPaciente") { type = NavType.StringType },
                navArgument("nombre") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idPaciente = backStackEntry.arguments?.getString("idPaciente") ?: ""
            val nombreSeleccionado = backStackEntry.arguments?.getString("nombre") ?: "Paciente"
            PantallaSeguimientoResultados(idPaciente, nombreSeleccionado, navController)
        }

        composable ("selectorEvaluacion"){
            PantallaSelectorEvaluacion(navController = navController)
        }

        composable(
            route = "formularioMMSE/{idPaciente}/{pacienteNombre}",
            arguments = listOf(
                navArgument("idPaciente") {type = NavType.StringType},
                navArgument("pacienteNombre") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idPaciente = backStackEntry.arguments?.getString("idPaciente") ?: ""
            val nombre = backStackEntry.arguments?.getString("pacienteNombre") ?: "Paciente"
            PantallaFormularioMMSE(idPaciente,nombre, navController)
        }

        composable(
            route = "formularioTinetti/{idPaciente}/{pacienteNombre}",
            arguments = listOf(
                navArgument("idPaciente") {type = NavType.StringType},
                navArgument("pacienteNombre") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idPaciente = backStackEntry.arguments?.getString("idPaciente") ?: ""
            val nombre = backStackEntry.arguments?.getString("pacienteNombre") ?: "Paciente"
            PantallaFormularioTinetti(idPaciente,nombre, navController)
        }

        composable(
            route = "pantallaResumen/{idPaciente}/{nombre}/{prueba}/{puntos}",
            arguments = listOf(
                navArgument("idPaciente") { type = NavType.StringType },
                navArgument("nombre") { type = NavType.StringType },
                navArgument("prueba") { type = NavType.StringType },
                navArgument("puntos") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idPaciente = backStackEntry.arguments?.getString("idPaciente") ?: ""
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val prueba = backStackEntry.arguments?.getString("prueba") ?: ""
            val puntos = backStackEntry.arguments?.getInt("puntos") ?: 0

            PantallaResumenEvaluacion(navController,idPaciente, nombre, prueba, puntos)
        }

        composable("listaEdicionPacientes") {
            PantallaListaEdicionPacientes(navController = navController)
        }

        composable(
            route = "editarPaciente/{idPaciente}",
            arguments = listOf(navArgument("idPaciente") { type = NavType.StringType })
        ) { backStackEntry ->
            val idPaciente = backStackEntry.arguments?.getString("idPaciente") ?: ""
            PantallaEditarPaciente(navController, idPaciente)
        }

        composable("administrarUsuarios") {
            PantallaAdministrarUsuarios(navController = navController)
        }

        composable(
            route = "editarUsuario/{idUsuario}",
            arguments = listOf(navArgument("idUsuario") { type = NavType.StringType })
        ) { backStackEntry ->
            val idUsuario = backStackEntry.arguments?.getString("idUsuario") ?: ""
            PantallaEditarUsuario(navController, idUsuario)
        }
    }
}