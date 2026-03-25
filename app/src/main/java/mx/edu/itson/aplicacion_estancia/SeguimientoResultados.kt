package mx.edu.itson.aplicacion_estancia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.itson.aplicacion_estancia.entidades.RegistroEvaluacion

@Composable
fun PantallaSeguimientoResultados(nombrePaciente: String) {
    // Simulamos que obtenemos los datos específicos de este paciente
    val historialPaciente = when (nombrePaciente) {
        "Ramon Flores Vasquez" -> listOf(
            RegistroEvaluacion("20/03/2026", "MMSE", 22, 30, "Estable"),
            RegistroEvaluacion("10/11/2025", "MMSE", 24, 30, "Mejora")
        )
        "Sebas Tortellini Borquez" -> listOf(
            RegistroEvaluacion("15/02/2026", "Tinetti", 12, 28, "Declive"),
            RegistroEvaluacion("01/10/2025", "Tinetti", 18, 28, "Estable")
        )
        else -> emptyList() // Datos por defecto para los demás
    }

    val estadoClinico = when (nombrePaciente) {
        "Ramon Flores Vasquez" -> "Deterioro Cognitivo Leve - Riesgo Moderado"
        "Sebas Tortellini Borquez" -> "Riesgo de Caída Alto - Requiere Asistencia"
        else -> "Sin evaluación reciente"
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.lavanda_nieve))
            .padding(16.dp)
    ) {
        Text(text = "Seguimiento Individual", fontSize = 14.sp, color = Color.Gray)
        Text(
            text = nombrePaciente,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.berenjena_suave)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta de Estado Clínico (Consulta Rápida)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.amatista_suave))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Resumen Clínico", color = Color.White, fontWeight = FontWeight.Bold)
                Text(estadoClinico, color = Color.White.copy(alpha = 0.8f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Comparación de Evaluaciones
        Text("Historial de Pruebas", fontWeight = FontWeight.Bold, color = colorResource(R.color.lavanda_profundo))

        if (historialPaciente.isEmpty()) {
            Text("No hay registros para este paciente.", modifier = Modifier.padding(top = 20.dp))
        } else {
            LazyColumn (verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(historialPaciente) { registro ->
                    ItemComparacion(registro)
                }
            }
        }
    }
}

@Composable
fun ItemComparacion(registro: RegistroEvaluacion) {
    // Definimos los colores según la tendencia (Mejora, Estable, Declive)
    val colorTendencia = when (registro.tendencia) {
        "Mejora" -> colorResource(R.color.verde_fuerte) // Verde
        "Declive" -> colorResource(R.color.rojo) // Rojo
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row (
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = registro.fecha, fontSize = 12.sp, color = Color.Gray)
                Text(
                    text = registro.tipo,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.berenjena_suave)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${registro.puntaje} / ${registro.total}",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.lavanda_profundo)
                )
                Text(
                    text = registro.tendencia,
                    fontSize = 12.sp,
                    color = colorTendencia,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

