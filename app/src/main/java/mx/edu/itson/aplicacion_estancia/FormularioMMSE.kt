package mx.edu.itson.aplicacion_estancia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.database.database

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormularioMMSE(idPaciente: String, nombrePaciente: String, navController: NavHostController) {

    // I. Orientación
    var r1 by remember { mutableStateOf(false) }
    var r2 by remember { mutableStateOf(false) }
    var r3 by remember { mutableStateOf(false) }
    var r4 by remember { mutableStateOf(false) }
    var r5 by remember { mutableStateOf(false) }
    var r6 by remember { mutableStateOf(false) }
    var r7 by remember { mutableStateOf(false) }
    var r8 by remember { mutableStateOf(false) }
    var r9 by remember { mutableStateOf(false) }
    var r10 by remember { mutableStateOf(false) }

    // II. Fijación
    var r11 by remember { mutableStateOf(false) }
    var r12 by remember { mutableStateOf(false) }
    var r13 by remember { mutableStateOf(false) }

    // III. Atención y Cálculo
    var rCalculo by remember { mutableIntStateOf(0) }

    // IV. Memoria
    var rMemoria by remember { mutableIntStateOf(0) }

    // V. Lenguaje y Praxis
    var rNominacion by remember { mutableIntStateOf(0) }
    var rRepeticion by remember { mutableStateOf(false) }
    var rComprension by remember { mutableIntStateOf(0) }
    var rLectura by remember { mutableStateOf(false) }
    var rEscritura by remember { mutableStateOf(false) }
    var rCopia by remember { mutableStateOf(false) }

    // Cálculo automático del puntaje total actual
    val puntajeTotal = listOf(
        r1, r2, r3, r4, r5, r6, r7, r8, r9, r10,
        r11, r12, r13,
        rRepeticion, rLectura, rEscritura, rCopia
    ).count { it } + rCalculo + rMemoria + rNominacion + rComprension

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.lavanda_nieve))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(text = "Evaluación MMSE", fontSize = 14.sp, color = Color.Gray)
        Text(
            text = nombrePaciente,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.berenjena_suave)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.amatista_suave))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Puntaje Acumulado:", color = Color.White, fontWeight = FontWeight.Bold)
                Text("$puntajeTotal", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "I. ORIENTACIÓN", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.lavanda_profundo))
        Spacer(modifier = Modifier.height(16.dp))

        // --- Orientación Temporal ---
        PreguntaSwitch(stringResource(R.string.preguntaMMSE1), r1) { r1 = it }
        PreguntaSwitch(stringResource(R.string.preguntaMMSE2), r2) { r2 = it }
        PreguntaSwitch(stringResource(R.string.preguntaMMSE3), r3) { r3 = it }
        PreguntaSwitch(stringResource(R.string.preguntaMMSE4), r4) { r4 = it }
        PreguntaSwitch(stringResource(R.string.preguntaMMSE5), r5) { r5 = it }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Orientación Espacial ---
        PreguntaSwitch(stringResource(R.string.preguntaMMSE6), r6) { r6 = it }
        PreguntaSwitch(stringResource(R.string.preguntaMMSE7), r7) { r7 = it }
        PreguntaSwitch(stringResource(R.string.preguntaMMSE8), r8) { r8 = it }
        PreguntaSwitch(stringResource(R.string.preguntaMMSE9), r9) { r9 = it }
        PreguntaSwitch(stringResource(R.string.preguntaMMSE10), r10) { r10 = it }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "2. FIJACIÓN O REGISTRO", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.lavanda_profundo))
        Text("Repita: Pelota, Caballo, Manzana. 1 punto por cada una.", fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))

        PreguntaSwitch("Pelota", r11) { r11 = it }
        PreguntaSwitch("Caballo", r12) { r12 = it }
        PreguntaSwitch("Manzana", r13) { r13 = it }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "3. ATENCIÓN Y CÁLCULO", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.lavanda_profundo))
        Text("Reste de 7 en 7 desde 100 (5 veces).", fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))

        PreguntaPuntaje(
            etiqueta = "Número de restas correctas (100, 93, 86, 79, 72, 65)",
            maxPuntos = 5,
            puntajeActual = rCalculo,
            onPuntajeChange = { rCalculo = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "4. MEMORIA / RECUERDO DIFERIDO", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.lavanda_profundo))
        Spacer(modifier = Modifier.height(16.dp))

        PreguntaPuntaje(
            etiqueta = "Mencione las palabras que se le enseñaron en el paso 2",
            maxPuntos = 3,
            puntajeActual = rMemoria,
            onPuntajeChange = { rMemoria = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "5. LENGUAJE Y PRAXIS", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.lavanda_profundo))
        Text("Comandos verbales, escritos y motores.", fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))

        PreguntaPuntaje(
            etiqueta = "Nominación: Identificar Reloj y Lápiz (1 punto c/u)",
            maxPuntos = 2,
            puntajeActual = rNominacion,
            onPuntajeChange = { rNominacion = it }
        )

        PreguntaSwitch("Repetición: 'Ni sí, ni no, ni pero'", rRepeticion) { rRepeticion = it }

        PreguntaPuntaje(
            etiqueta = "Comprensión: Orden de 3 tiempos (Tomar papel, doblar, soltar)",
            maxPuntos = 3,
            puntajeActual = rComprension,
            onPuntajeChange = { rComprension = it }
        )

        PreguntaSwitch("Lectura: Ejecutar orden 'CIERRE LOS OJOS'", rLectura) { rLectura = it }

        PreguntaSwitch("Escritura: Escribir una frase con sentido", rEscritura) { rEscritura = it }

        PreguntaSwitch("Copia: Dibujar dos pentágonos cruzados", rCopia) { rCopia = it }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                finalizarMMSE(idPaciente, "MMSE", puntajeTotal, navController, nombrePaciente)
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.berenjena_suave)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Finalizar Evaluación", fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreguntaPuntaje(etiqueta: String, maxPuntos: Int, puntajeActual: Int, onPuntajeChange: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = etiqueta, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                (0..maxPuntos).forEach { punto ->
                    FilterChip(
                        selected = (puntajeActual == punto),
                        onClick = { onPuntajeChange(punto) },
                        label = { Text(punto.toString()) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorResource(id = R.color.amatista_suave),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
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

fun finalizarMMSE(idPaciente: String, tipoExamen: String, puntajeFinal: Int, navController: NavHostController, nombrePaciente: String) {
    val dbRef = Firebase.database.getReference("Pacientes/$idPaciente/resultados")
    val datosExamen = mapOf(
        "tipo" to tipoExamen,
        "puntaje" to puntajeFinal,
        "fecha" to System.currentTimeMillis()
    )
    dbRef.push().setValue(datosExamen)
        .addOnSuccessListener {
            navController.navigate("pantallaResumen/$idPaciente/$nombrePaciente/MMSE/$puntajeFinal")
        }
}
