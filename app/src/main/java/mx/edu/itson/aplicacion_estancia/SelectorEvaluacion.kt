package mx.edu.itson.aplicacion_estancia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mx.edu.itson.aplicacion_estancia.entidades.Paciente
import mx.edu.itson.aplicacion_estancia.entidades.PacienteId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaSelectorEvaluacion(navController: NavHostController) {
    val database = FirebaseDatabase.getInstance().getReference("Pacientes")

    // Estados para los datos de Firebase
    val listaPacientesId = remember { mutableStateListOf<PacienteId>() }
    var isLoading by remember { mutableStateOf(true) }

    // Listas de opciones fijas
    val pruebas = listOf("MMSE (Mini-Mental)", "Escala de Tinetti")

    // Estados para los selectores
    var pacienteSeleccionado by remember { mutableStateOf<PacienteId?>(null) }
    var pruebaSeleccionada by remember { mutableStateOf("") }
    var expandidoPacientes by remember { mutableStateOf(false) }
    var expandidoPruebas by remember { mutableStateOf(false) }

    // Cargar pacientes desde Firebase en tiempo real
    LaunchedEffect(Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaPacientesId.clear()
                for (child in snapshot.children) {
                    val id = child.key ?: ""
                    val paciente = child.getValue(Paciente::class.java)
                    paciente?.let {
                        val nombreCompleto = "${it.nombre} ${it.apellidoPaterno} ${it.apellidoMaterno}"
                        listaPacientesId.add(PacienteId(id, nombreCompleto))
                    }
                }
                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                isLoading = false
            }
        })
    }

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
            onExpandedChange = { if (!isLoading) expandidoPacientes = !expandidoPacientes },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = if (isLoading) "Cargando..." else (pacienteSeleccionado?.nombre ?: ""),
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Elegir paciente...") },
                trailingIcon = { 
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                    } else {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoPacientes)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White, 
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            
            if (!isLoading && listaPacientesId.isNotEmpty()) {
                ExposedDropdownMenu (
                    expanded = expandidoPacientes,
                    onDismissRequest = { expandidoPacientes = false }
                ) {
                    listaPacientesId.forEach { p ->
                        DropdownMenuItem(
                            text = { Text(p.nombre) },
                            onClick = {
                                pacienteSeleccionado = p
                                expandidoPacientes = false
                            }
                        )
                    }
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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White, 
                    unfocusedContainerColor = Color.White
                ),
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
                val p = pacienteSeleccionado
                if (p != null && pruebaSeleccionada.isNotEmpty()) {
                    val ruta = if (pruebaSeleccionada.contains("MMSE")) "formularioMMSE" else "formularioTinetti"
                    navController.navigate("$ruta/${p.id}/${p.nombre}")
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.amatista_suave)),
            shape = RoundedCornerShape(12.dp),
            enabled = pacienteSeleccionado != null && pruebaSeleccionada.isNotEmpty()
        ) {
            Text("Comenzar Evaluación", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
