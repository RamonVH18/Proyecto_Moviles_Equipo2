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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.database.FirebaseDatabase
import mx.edu.itson.aplicacion_estancia.entidades.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEditarUsuario(navController: NavHostController, idUsuario: String) {
    val context = LocalContext.current
    val database = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuario)

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("DOCTOR") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(idUsuario) {
        database.get().addOnSuccessListener { snapshot ->
            val usuario = snapshot.getValue(Usuario::class.java)
            if (usuario != null) {
                nombre = usuario.nombre
                correo = usuario.correo
                rol = usuario.rol
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
                title = { Text("Editar Usuario", color = Color.White) },
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
                    label = { Text("Nombre Completo") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    enabled = false // El correo suele ser el identificador en Auth, mejor no editarlo aquí directamente
                )

                Text(
                    text = "Rol del Usuario",
                    modifier = Modifier.align(Alignment.Start),
                    fontWeight = FontWeight.Bold
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = rol == "DOCTOR", onClick = { rol = "DOCTOR" })
                    Text("Doctor")
                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(selected = rol == "ADMIN", onClick = { rol = "ADMIN" })
                    Text("Administrador")
                }

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
                        Text("Cancelar", color = Color.White)
                    }

                    Button(
                        onClick = {
                            if (nombre.isNotEmpty()) {
                                val updates = hashMapOf<String, Any>(
                                    "nombre" to nombre,
                                    "rol" to rol
                                )
                                database.updateChildren(updates).addOnSuccessListener {
                                    Toast.makeText(context, "Usuario actualizado", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }.addOnFailureListener {
                                    Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.berenjena_suave)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Guardar", color = Color.White)
                    }
                }
            }
        }
    }
}
