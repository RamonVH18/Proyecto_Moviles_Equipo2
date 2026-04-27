package mx.edu.itson.aplicacion_estancia

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mx.edu.itson.aplicacion_estancia.entidades.Paciente

@Composable
fun PantallaListaPacientes(navController: NavHostController) {
    val pacientes = listOf(
        Paciente("Ramon", "Flores", "Vasquez", "15/05/1945", "6441234567"),
        Paciente("Abraham", "Tovar", "Guerrero", "22/10/1950", "6449876543"),
        Paciente("Daniel", "Coronel", "Miramontes", "03/01/1948", "6445551234"),
        Paciente("Sebas", "Tortellini", "Borquez", "12/12/1952", "6441112233"),
        Paciente("Jaime Eduardo", "Lerma", "Cuevas", "30/07/1947", "6444449988")
    )

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate("registroPaciente") },
                containerColor = colorResource(id = R.color.berenjena_suave),
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                icon = { Icon(Icons.Default.Add, contentDescription = "Agregar") },
                text = { Text("Nuevo Paciente") }
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
            Text(
                text = "Directorio de Pacientes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.berenjena_suave),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(pacientes) { paciente ->
                    CardPaciente(paciente, navController)
                }
            }
        }
    }
}

@Composable
fun CardPaciente(paciente: Paciente, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val nombreCompleto = "${paciente.nombre} ${paciente.apellidoPaterno} ${paciente.apellidoMaterno}"
                navController.navigate("seguimiento/$nombreCompleto")
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
                    text = paciente.nombre.take(1),
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

                Text(
                    text = "Nació: ${paciente.fechaNacimiento}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Text(
                    text = "Tel: ${paciente.contacto}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.lavanda_profundo)
                )
            }
        }
    }
}
