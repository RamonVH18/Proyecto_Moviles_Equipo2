package mx.edu.itson.aplicacion_estancia.entidades

data class RegistroEvaluacion(
    val fecha: String,
    val tipo: String,
    val puntaje: Int,
    val total: Int,
    val tendencia: String
)