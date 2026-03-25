package mx.edu.itson.aplicacion_estancia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun PantallaFormularioMMSE(nombrePaciente: String, navController: NavHostController) {

    var r1 by remember { mutableStateOf(false) } // Año
    var r2 by remember { mutableStateOf(false) } // Estación
    var r3 by remember { mutableStateOf(false) } // Día del mes
    var r4 by remember { mutableStateOf(false) } // Mes
    var r5 by remember { mutableStateOf(false) } // Día de la semana

    // Cálculo automático del puntaje
    val puntajeTotal = listOf(r1, r2, r3, r4, r5).count { it }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.lavanda_nieve))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        // Encabezado
        Text(text = "Evaluación MMSE", fontSize = 14.sp, color = Color.Gray)
        Text(
            text = nombrePaciente,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.berenjena_suave)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Barra de Puntaje en tiempo real
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.amatista_suave))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Puntaje Orientación:", color = Color.White, fontWeight = FontWeight.Bold)
                Text("$puntajeTotal / 5", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "I. ORIENTACIÓN TEMPORAL",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.lavanda_profundo)
        )
        Text("Pregunte al paciente las siguientes cuestiones:", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de preguntas
        PreguntaSwitch(stringResource(R.string.preguntaMMSE1), r1) { r1 = it }
        PreguntaSwitch(stringResource(R.string.preguntaMMSE2), r2) { r2 = it }
        PreguntaSwitch(stringResource(R.string.preguntaMMSE3), r3) { r3 = it }
        PreguntaSwitch(stringResource(R.string.preguntaMMSE4), r4) { r4 = it }
        PreguntaSwitch(stringResource(R.string.preguntaMMSE5), r5) { r5 = it }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate("pantallaResumen/$nombrePaciente/MMSE/$puntajeTotal")
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.berenjena_suave)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Siguiente Sección (Espacial)", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun PreguntaSwitch(pregunta: String, estado: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = pregunta, modifier = Modifier.weight(1f), fontSize = 16.sp)
            Switch(
                checked = estado,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(id = R.color.lavanda_brillante),
                    checkedTrackColor = colorResource(id = R.color.amatista_suave)
                )
            )
        }
    }
}