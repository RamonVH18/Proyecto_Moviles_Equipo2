package mx.edu.itson.aplicacion_estancia

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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

@Composable
fun PantallaListaEdicionPacientes(navController: NavHostController) {
    val database = FirebaseDatabase.getInstance().getReference("Pacientes")
    var pacientes by remember { mutableStateOf<List<Paciente>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaTemporal = mutableListOf<Paciente>()
                for (pacienteSnapshot in snapshot.children) {
                    val paciente = pacienteSnapshot.getValue(Paciente::class.java)
                    if (paciente != null) {
                        paciente.id = pacienteSnapshot.key ?: ""
                        listaTemporal.add(paciente)
                    }
                }
                pacientes = listaTemporal
                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                isLoading = false
            }
        }
        database.addValueEventListener(listener)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(id = R.color.lavanda_nieve))
                .padding(16.dp)
        ) {
            Text(
                text = "Seleccione Paciente para Editar",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.berenjena_suave),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorResource(id = R.color.berenjena_suave))
                }
            } else if (pacientes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No hay pacientes registrados", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(pacientes) { paciente ->
                        CardPacienteEdicion(paciente, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun CardPacienteEdicion(paciente: Paciente, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("editarPaciente/${paciente.id}")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(colorResource(id = R.color.amatista_suave), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (paciente.nombre.isNotEmpty()) paciente.nombre.take(1) else "?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "${paciente.nombre} ${paciente.apellidoPaterno} ${paciente.apellidoMaterno}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.berenjena_suave)
                )
            }
        }
    }
}
