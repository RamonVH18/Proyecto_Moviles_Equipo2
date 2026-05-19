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
import com.google.firebase.Firebase
import com.google.firebase.database.database

@Composable
fun PantallaFormularioTinetti(idPaciente: String, nombrePaciente: String, navController: NavHostController) {
    // Estados para los puntajes de cada observación (Total 18 secciones detectadas)
    var p1 by remember { mutableIntStateOf(0) } // 1. Equilibrio sentado
    var p2 by remember { mutableIntStateOf(0) } // 2. Levantarse
    var p3 by remember { mutableIntStateOf(0) } // 3. Intentos para levantarse
    var p4 by remember { mutableIntStateOf(0) } // 4. Equilibrio inmediato
    var p5 by remember { mutableIntStateOf(0) } // 5. Equilibrio en bipedestación
    var p6 by remember { mutableIntStateOf(0) } // 6. Empujón
    var p7 by remember { mutableIntStateOf(0) } // 7. Ojos cerrados
    var p8 by remember { mutableIntStateOf(0) } // 8. Giro 360 (Pasos)
    var p9 by remember { mutableIntStateOf(0) } // 8. Giro 360 (Estabilidad)
    var p10 by remember { mutableIntStateOf(0) } // 9. Sentarse
    var p11 by remember { mutableIntStateOf(0) } // 10. Iniciación de la marcha
    var p12 by remember { mutableIntStateOf(0) } // 11. Paso derecho
    var p13 by remember { mutableIntStateOf(0) } // 11. Paso izquierdo
    var p14 by remember { mutableIntStateOf(0) } // 12. Simetría
    var p15 by remember { mutableIntStateOf(0) } // 13. Continuidad
    var p16 by remember { mutableIntStateOf(0) } // 14. Trayectoria
    var p17 by remember { mutableIntStateOf(0) } // 15. Estabilidad tronco
    var p18 by remember { mutableIntStateOf(0) } // 16. Postura

    val puntajeTotal = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9 + p10 + p11 + p12 + p13 + p14 + p15 + p16 + p17 + p18

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.lavanda_nieve))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(text = "Escala de Tinetti (Equilibrio y Marcha)", fontSize = 14.sp, color = Color.Gray)
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
                Text("Puntaje Total", color = Color.White, fontSize = 14.sp)
                Text("$puntajeTotal Puntos", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
                Text(
                    text = when {
                        puntajeTotal < 19 -> "Riesgo Alto de Caída"
                        puntajeTotal < 24 -> "Riesgo Moderado"
                        else -> "Riesgo Bajo"
                    },
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // SECCIÓN: EQUILIBRIO
        Text(text = "I. EVALUACIÓN DEL EQUILIBRIO", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.lavanda_profundo))

        SeccionTinetti(
            titulo = "1. Equilibrio sentado",
            opciones = listOf("Se inclina o desliza en la silla", "Seguro y firme"),
            seleccionado = p1,
            onOptionSelected = { p1 = it }
        )

        SeccionTinetti(
            titulo = "2. Levantarse",
            opciones = listOf("Incapaz sin ayuda", "Capaz con ayuda de brazos", "Capaz sin usar brazos"),
            seleccionado = p2,
            onOptionSelected = { p2 = it }
        )

        SeccionTinetti(
            titulo = "3. Intentos para levantarse",
            opciones = listOf("Incapaz sin ayuda", "Capaz pero necesita más de un intento", "Capaz de levantarse con un solo intento"),
            seleccionado = p3,
            onOptionSelected = { p3 = it }
        )

        SeccionTinetti(
            titulo = "4. Equilibrio inmediato al levantarse",
            opciones = listOf("Inestable / se tambalea", "Estable pero usa bastón/andador", "Completamente estable sin apoyos"),
            seleccionado = p4,
            onOptionSelected = { p4 = it }
        )

        SeccionTinetti(
            titulo = "5. Equilibrio en bipedestación",
            opciones = listOf("Inestable", "Apoyo amplio o usa ayuda", "Apoyo estrecho sin soporte"),
            seleccionado = p5,
            onOptionSelected = { p5 = it }
        )

        SeccionTinetti(
            titulo = "6. Empujón",
            opciones = listOf("Empieza a caerse", "Se tambalea / se agarra", "Se mantiene estable"),
            seleccionado = p6,
            onOptionSelected = { p6 = it }
        )

        SeccionTinetti(
            titulo = "7. Ojos cerrados",
            opciones = listOf("Inestable", "Estable"),
            seleccionado = p7,
            onOptionSelected = { p7 = it }
        )

        SeccionTinetti(
            titulo = "8a. Giro de 360 grados (Pasos)",
            opciones = listOf("Pasos discontinuos", "Pasos seguidos"),
            seleccionado = p8,
            onOptionSelected = { p8 = it }
        )

        SeccionTinetti(
            titulo = "8b. Giro de 360 grados (Estabilidad)",
            opciones = listOf("Inestable", "Estable"),
            seleccionado = p9,
            onOptionSelected = { p9 = it }
        )

        SeccionTinetti(
            titulo = "9. Sentarse",
            opciones = listOf("Inseguro / calcula mal la distancia", "Usa los brazos de forma brusca", "Se sienta con un movimiento suave y seguro"),
            seleccionado = p10,
            onOptionSelected = { p10 = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // SECCIÓN: MARCHA
        Text(text = "II. EVALUACIÓN DE LA MARCHA", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.lavanda_profundo))

        SeccionTinetti(
            titulo = "10. Iniciación de la marcha",
            opciones = listOf("Vacila o tiene múltiples intentos", "Empieza a caminar inmediatamente sin dudar"),
            seleccionado = p11,
            onOptionSelected = { p11 = it }
        )

        SeccionTinetti(
            titulo = "11a. Longitud y altura del paso (pie derecho)",
            opciones = listOf("No sobrepasa al izquierdo / no se separa del suelo", "Sobrepasa al izquierdo y se eleva completamente"),
            seleccionado = p12,
            onOptionSelected = { p12 = it }
        )

        SeccionTinetti(
            titulo = "11b. Longitud y altura del paso (pie izquierdo)",
            opciones = listOf("No sobrepasa al derecho / no se separa del suelo", "Sobrepasa al derecho y se eleva completamente"),
            seleccionado = p13,
            onOptionSelected = { p13 = it }
        )

        SeccionTinetti(
            titulo = "12. Simetría del paso",
            opciones = listOf("La longitud es diferente", "Ambos pasos miden lo mismo"),
            seleccionado = p14,
            onOptionSelected = { p14 = it }
        )

        SeccionTinetti(
            titulo = "13. Continuidad de los pasos",
            opciones = listOf("Detiene o interrumpe la marcha entre pasos", "Los pasos son continuos"),
            seleccionado = p15,
            onOptionSelected = { p15 = it }
        )

        SeccionTinetti(
            titulo = "14. Desviación de la trayectoria",
            opciones = listOf("Desviación severa / se va de lado", "Desviación moderada o usa ayuda", "Camina recto sin apoyo"),
            seleccionado = p16,
            onOptionSelected = { p16 = it }
        )

        SeccionTinetti(
            titulo = "15. Estabilidad del tronco",
            opciones = listOf("Balanceo marcado o usa ayuda", "Flexiona las rodillas/espalda o abre los brazos", "Tronco firme sin balanceo"),
            seleccionado = p17,
            onOptionSelected = { p17 = it }
        )

        SeccionTinetti(
            titulo = "16. Postura al caminar",
            opciones = listOf("Los talones se separan mucho al caminar", "Los talones casi se tocan al avanzar"),
            seleccionado = p18,
            onOptionSelected = { p18 = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                finalizarTinetti(idPaciente, "Tinetti", puntajeTotal, navController, nombrePaciente)
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.berenjena_suave)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Finalizar Evaluación", fontWeight = FontWeight.Bold)
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

fun finalizarTinetti(idPaciente: String, tipoExamen: String, puntajeFinal: Int, navController: NavHostController, nombrePaciente: String) {
    val dbRef = Firebase.database.getReference("Pacientes/$idPaciente/resultados")

    val datosExamen = mapOf(
        "tipo" to tipoExamen,
        "puntaje" to puntajeFinal,
        "fecha" to System.currentTimeMillis()
    )

    dbRef.push().setValue(datosExamen)
        .addOnSuccessListener {
            navController.navigate("pantallaResumen/$idPaciente/$nombrePaciente/Tinetti/$puntajeFinal")
        }
}
