package mx.edu.itson.aplicacion_estancia.entidades

data class Usuario(
    var id: String = "",
    val nombre: String = "",
    val correo: String = "",
    val rol: String = "DOCTOR"
)
