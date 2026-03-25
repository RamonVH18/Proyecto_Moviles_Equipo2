package mx.edu.itson.aplicacion_estancia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaSelectorEvaluacion(navController: NavHostController) {
    // Listas de opciones
    val pacientes = listOf("Ramon Flores Vasquez", "Abraham Tovar Guerrero", "Daniel Coronel Miramontes", "Sebas Tortellini Borquez", "Jaime Lerma Cuevas")
    val pruebas = listOf("MMSE (Mini-Mental)", "Escala de Tinetti")

    // Estados para los selectores
    var pacienteSeleccionado by remember { mutableStateOf("") }
    var pruebaSeleccionada by remember { mutableStateOf("") }
    var expandidoPacientes by remember { mutableStateOf(false) }
    var expandidoPruebas by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.lavanda_nieve))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Nueva Evaluación",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.berenjena_suave)
        )
        Text(
            text = "Seleccione los datos para comenzar",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // 1. Selector de Paciente
        Text("Paciente:", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Medium)
        ExposedDropdownMenuBox (
            expanded = expandidoPacientes,
            onExpandedChange = { expandidoPacientes = !expandidoPacientes },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = pacienteSeleccionado,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Elegir paciente...") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoPacientes) },
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu (
                expanded = expandidoPacientes,
                onDismissRequest = { expandidoPacientes = false }
            ) {
                pacientes.forEach { nombre ->
                    DropdownMenuItem(
                        text = { Text(nombre) },
                        onClick = {
                            pacienteSeleccionado = nombre
                            expandidoPacientes = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Selector de Prueba
        Text("Instrumento Clínico:", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Medium)
        ExposedDropdownMenuBox(
            expanded = expandidoPruebas,
            onExpandedChange = { expandidoPruebas = !expandidoPruebas },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = pruebaSeleccionada,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Elegir prueba...") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoPruebas) },
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(
                expanded = expandidoPruebas,
                onDismissRequest = { expandidoPruebas = false }
            ) {
                pruebas.forEach { prueba ->
                    DropdownMenuItem(
                        text = { Text(prueba) },
                        onClick = {
                            pruebaSeleccionada = prueba
                            expandidoPruebas = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Botón para iniciar
        Button (
            onClick = {
                if (pacienteSeleccionado.isNotEmpty() && pruebaSeleccionada.isNotEmpty()) {
                    // Aquí navegaremos al formulario real
                    val ruta = if (pruebaSeleccionada.contains("MMSE")) "formularioMMSE" else "formularioTinetti"
                    navController.navigate("$ruta/$pacienteSeleccionado")
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.amatista_suave)),
            shape = RoundedCornerShape(12.dp),
            enabled = pacienteSeleccionado.isNotEmpty() && pruebaSeleccionada.isNotEmpty()
        ) {
            Text("Comenzar Evaluación", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}