package mx.edu.itson.aplicacion_estancia

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun FormularioDeContacto(navController: NavHostController) {

    var nombre by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val context = LocalContext.current



    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.amatista_suave))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))


        CampoDeEntrada(
            value = nombre,
            onValueChange = { nombre = it },
            label = stringResource(id = R.string.form_label_nombre),
            placeholder = stringResource(id = R.string.form_placeholder_nombre)
        )

        Spacer(modifier = Modifier.height(16.dp))


        CampoDeEntrada(
            value = fechaNacimiento,
            onValueChange = { fechaNacimiento = it },
            label = stringResource(id = R.string.form_label_fecha_nacimiento),
            placeholder = stringResource(id = R.string.form_placeholder_fecha_nacimiento)
        )

        Spacer(modifier = Modifier.height(16.dp))


        CampoDeEntrada(
            value = correo,
            onValueChange = { correo = it },
            label = stringResource(id = R.string.form_label_correo),
            placeholder = stringResource(id = R.string.form_placeholder_correo)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CampoDeEntrada(
            value = telefono,
            onValueChange = { telefono = it },
            label = stringResource(id = R.string.form_label_telefono),
            placeholder = stringResource(id = R.string.form_placeholder_telefono)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = mensaje,
            onValueChange = { mensaje = it },
            label = { Text(stringResource(id = R.string.form_label_mensaje)) },
            placeholder = { Text(stringResource(id = R.string.form_placeholder_mensaje)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button (
            onClick = {
                Toast.makeText(context, R.string.guardado, Toast.LENGTH_SHORT).show()
                      },
            modifier = Modifier
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.berenjena_suave)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.form_boton_enviar),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CampoDeEntrada(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White
        )
    )
}