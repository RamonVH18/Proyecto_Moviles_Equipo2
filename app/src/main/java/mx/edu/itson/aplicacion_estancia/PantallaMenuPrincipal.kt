package mx.edu.itson.aplicacion_estancia

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

data class OpcionMenu(val titulo: String, val icono: ImageVector, val color: Color)

@Composable
fun PantallaMenuPrincipal(navController: NavHostController) {
    val context = LocalContext.current

    // Definimos las opciones (Aquí puedes usar tus colores de res/colors.xml)
    val opciones = listOf(
        OpcionMenu(stringResource(R.string.btnPacientes), Icons.Default.AccountBox,
            colorResource(R.color.lavanda_brillante)),
        OpcionMenu(stringResource(R.string.btnEvaluaciones), Icons.Default.Star,
            colorResource(R.color.lavanda_brillante)),
        OpcionMenu(stringResource(R.string.btnHistorial), Icons.Default.DateRange,
            colorResource(R.color.lavanda_brillante)),
        OpcionMenu(stringResource(R.string.btnPendientes), Icons.Default.Settings,
            colorResource(R.color.lavanda_brillante))
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.lavanda_nieve))
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.tituloMenu),
            fontSize = 20.sp,
            color = Color.Gray
        )
        Text(
            text = stringResource(R.string.nombreEstancia),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.berenjena_suave) // Berenjena
        )

        Spacer(modifier = Modifier.height(128.dp))

        LazyVerticalGrid (
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(opciones) { opcion ->
                TarjetaMenu(navController, opcion) {

                }
            }
        }
    }
}

@Composable
fun TarjetaMenu(navController:NavHostController, opcion: OpcionMenu, onClick: () -> Unit) {
    val textoPacientes = stringResource(R.string.btnPacientes)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick  = {
            when (opcion.titulo) {
                textoPacientes -> navController.navigate("listaPacientes")
                else -> println("Easter Lerma") // El 'else' es el 'default'
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = opcion.icono,
                contentDescription = null,
                tint = opcion.color,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = opcion.titulo,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.black_gray),
                fontSize = 16.sp
            )

        }
    }
}