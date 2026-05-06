package mx.edu.itson.aplicacion_estancia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaSeguimientoResultados(idPaciente: String, nombrePaciente: String, navController: NavHostController) {
    val database = FirebaseDatabase.getInstance().getReference("Pacientes").child(idPaciente).child("resultados")
    var historial by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(idPaciente) {
        val query = database.orderByChild("fecha")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = mutableListOf<Map<String, Any>>()
                for (resSnapshot in snapshot.children) {
                    val map = resSnapshot.value as? Map<String, Any>
                    if (map != null) {
                        lista.add(map)
                    }
                }
                // Ordenar por fecha descendente (más reciente primero)
                historial = lista.sortedByDescending { it["fecha"] as? Long ?: 0L }
                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                isLoading = false
            }
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seguimiento de Paciente", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.berenjena_suave)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(id = R.color.lavanda_nieve))
                .padding(16.dp)
        ) {
            Text(text = "Paciente:", fontSize = 14.sp, color = Color.Gray)
            Text(
                text = nombrePaciente,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.berenjena_suave)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Historial de Pruebas",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colorResource(R.color.lavanda_profundo)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colorResource(id = R.color.berenjena_suave))
                }
            } else if (historial.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay evaluaciones registradas para este paciente.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(historial) { registro ->
                        CardResultado(registro)
                    }
                }
            }
        }
    }
}

@Composable
fun CardResultado(registro: Map<String, Any>) {
    val tipo = registro["tipo"] as? String ?: "Prueba"
    val puntaje = (registro["puntaje"] as? Long)?.toInt() ?: 0
    val fechaTimestamp = registro["fecha"] as? Long ?: 0L
    
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val fechaFormateada = if (fechaTimestamp != 0L) sdf.format(Date(fechaTimestamp)) else "Fecha desconocida"

    // Definir el total según el tipo de prueba
    val total = if (tipo == "MMSE") 30 else if (tipo == "Tinetti") 28 else 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = fechaFormateada, fontSize = 12.sp, color = Color.Gray)
                Text(
                    text = tipo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.berenjena_suave)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = if (total > 0) "$puntaje / $total" else "$puntaje pts",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.lavanda_profundo)
                )
            }
        }
    }
}
