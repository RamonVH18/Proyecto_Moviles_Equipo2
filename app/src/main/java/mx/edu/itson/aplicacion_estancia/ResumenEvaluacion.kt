package mx.edu.itson.aplicacion_estancia

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun PantallaResumenEvaluacion(
    navController: NavHostController,
    nombrePaciente: String,
    tipoPrueba: String, // "MMSE" o "Tinetti"
    puntajeObtenido: Int
) {
    // Lógica de interpretación basada en manuales clínicos
    val (diagnostico, colorAlerta) = when (tipoPrueba) {
        "MMSE" -> when {
            puntajeObtenido >= 24 -> "Sin Deterioro Cognitivo" to colorResource(R.color.verde_fuerte)
            puntajeObtenido in 19..23 -> "Deterioro Cognitivo Leve" to colorResource(R.color.amarillo)
            puntajeObtenido in 14..18 -> "Deterioro Moderado" to colorResource(R.color.naranja)
            else -> "Deterioro Severo" to colorResource(R.color.rojo)
        }
        "Tinetti" -> when {
            puntajeObtenido >= 24 -> "Riesgo Mínimo de Caída" to colorResource(R.color.verde_fuerte)
            puntajeObtenido in 19..23 -> "Riesgo Moderado de Caída" to colorResource(R.color.amarillo)
            else -> "Riesgo Alto de Caída" to colorResource(R.color.rojo)
        }
        else -> "Resultado No Clasificado" to Color.Gray
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.lavanda_nieve))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icono de Éxito / Finalización
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = colorResource(id = R.color.amatista_suave),
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Evaluación Finalizada",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.berenjena_suave)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Card de Resultado Principal
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = nombrePaciente, fontSize = 18.sp, color = Color.Gray)
                Text(text = tipoPrueba, fontSize = 16.sp, fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.height(16.dp))

                // Círculo de Puntaje
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(colorAlerta.copy(alpha = 0.1f), CircleShape)
                        .border(4.dp, colorAlerta, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$puntajeObtenido",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Black,
                        color = colorAlerta
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Texto del Diagnóstico
                Text(
                    text = diagnostico,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorAlerta,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Botones de Acción
        Button (
            onClick = { navController.navigate("menuPrincipal") },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.berenjena_suave)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Regresar al Menú", fontWeight = FontWeight.Bold)
        }

        TextButton (
            onClick = { navController.navigate("seguimiento/$nombrePaciente")},
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Ver historial de $nombrePaciente", color = colorResource(id = R.color.lavanda_profundo))
        }
    }
}