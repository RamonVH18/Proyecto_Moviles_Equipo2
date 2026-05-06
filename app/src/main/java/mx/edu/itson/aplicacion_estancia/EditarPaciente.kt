package mx.edu.itson.aplicacion_estancia

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.database.FirebaseDatabase
import mx.edu.itson.aplicacion_estancia.entidades.Paciente

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEditarPaciente(navController: NavHostController, idPaciente: String) {
    val context = LocalContext.current
    val database = FirebaseDatabase.getInstance().getReference("Pacientes").child(idPaciente)

    var nombre by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(idPaciente) {
        database.get().addOnSuccessListener { snapshot ->
            val paciente = snapshot.getValue(Paciente::class.java)
            if (paciente != null) {
                nombre = paciente.nombre
                apellidoPaterno = paciente.apellidoPaterno
                apellidoMaterno = paciente.apellidoMaterno
                fechaNacimiento = paciente.fechaNacimiento
                telefono = paciente.contacto
            }
            isLoading = false
        }.addOnFailureListener {
            Toast.makeText(context, "Error al cargar datos", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Paciente", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.berenjena_suave)
                )
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = colorResource(id = R.color.berenjena_suave))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(colorResource(id = R.color.lavanda_nieve))
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text(stringResource(R.string.form_label_nombre)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = apellidoPaterno,
                    onValueChange = { apellidoPaterno = it },
                    label = { Text(stringResource(R.string.form_label_apellido_paterno)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = apellidoMaterno,
                    onValueChange = { apellidoMaterno = it },
                    label = { Text(stringResource(R.string.form_label_apellido_materno)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = fechaNacimiento,
                    onValueChange = { fechaNacimiento = it },
                    label = { Text(stringResource(R.string.form_label_fecha_nacimiento)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text(stringResource(R.string.form_label_telefono)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(stringResource(R.string.btnCancelar), fontSize = 16.sp, color = Color.White)
                    }

                    Button(
                        onClick = {
                            if (nombre.isNotEmpty() && apellidoPaterno.isNotEmpty() && apellidoMaterno.isNotEmpty() && fechaNacimiento.isNotEmpty() && telefono.isNotEmpty()) {
                                // Usamos un Map y updateChildren para actualizar solo los campos de información personal
                                // y evitar que se borren otros nodos hijos (como 'resultados')
                                val actualizaciones = hashMapOf<String, Any>(
                                    "nombre" to nombre,
                                    "apellidoPaterno" to apellidoPaterno,
                                    "apellidoMaterno" to apellidoMaterno,
                                    "fechaNacimiento" to fechaNacimiento,
                                    "contacto" to telefono
                                )
                                
                                database.updateChildren(actualizaciones).addOnSuccessListener {
                                    Toast.makeText(context, "Paciente actualizado", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }.addOnFailureListener {
                                    Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.berenjena_suave)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(stringResource(R.string.btnGuardar), fontSize = 16.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
