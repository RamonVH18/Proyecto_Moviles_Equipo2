package mx.edu.itson.aplicacion_estancia

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
            PantallaListaPacientes()
        }
    }
}