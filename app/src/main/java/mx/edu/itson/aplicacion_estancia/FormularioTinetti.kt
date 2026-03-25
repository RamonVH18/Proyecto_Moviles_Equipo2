package mx.edu.itson.aplicacion_estancia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun PantallaFormularioTinetti(nombrePaciente: String, navController: NavHostController) {
    // Estados para los puntajes de cada observación
    var p1 by remember { mutableIntStateOf(0) } // Equilibrio sentado
    var p2 by remember { mutableIntStateOf(0) } // Levantarse
    var p3 by remember { mutableIntStateOf(0) } // Intentos para levantarse

    val puntajeTotal = p1 + p2 + p3

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.lavanda_nieve))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(text = "Escala de Tinetti (Equilibrio)", fontSize = 14.sp, color = Color.Gray)
        Text(
            text = nombrePaciente,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.berenjena_suave)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Card de Puntaje Dinámico
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.amatista_suave))
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Riesgo de Caída", color = Color.White, fontSize = 14.sp)
                Text("$puntajeTotal Puntos", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
                Text(
                    text = when {
                        puntajeTotal < 2 -> "Riesgo Alto"
                        else -> "Riesgo Bajo (En esta sección)"
                    },
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Preguntas tipo Observación
        SeccionTinetti(
            titulo = "1. Equilibrio sentado",
            opciones = listOf("Se inclina o desliza en la silla (0)", "Seguro y firme (1)"),
            seleccionado = p1,
            onOptionSelected = { p1 = it }
        )

        SeccionTinetti(
            titulo = "2. Levantarse",
            opciones = listOf("Incapaz sin ayuda (0)", "Capaz con ayuda de brazos (1)", "Capaz sin usar brazos (2)"),
            seleccionado = p2,
            onOptionSelected = { p2 = it }
        )

        SeccionTinetti(
            titulo = "3. Intentos para levantarse",
            opciones = listOf("Incapaz sin ayuda (0)", "Capaz pero necesita más de un intento (1)", "Capaz de levantarse con un solo intento (2)"),
            seleccionado = p3,
            onOptionSelected = { p3 = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate("pantallaResumen/$nombrePaciente/Tinetti/$puntajeTotal")
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.berenjena_suave)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Siguiente: Evaluación de Marcha", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SeccionTinetti(titulo: String, opciones: List<String>, seleccionado: Int, onOptionSelected: (Int) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(text = titulo, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.lavanda_profundo))
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column {
                opciones.forEachIndexed { index, texto ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (seleccionado == index),
                                onClick = { onOptionSelected(index) }
                            )
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (seleccionado == index),
                            onClick = { onOptionSelected(index) },
                            colors = RadioButtonDefaults.colors(selectedColor = colorResource(id = R.color.amatista_suave))
                        )
                        Text(text = texto, fontSize = 14.sp, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    }
}