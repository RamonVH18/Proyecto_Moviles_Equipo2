package mx.edu.itson.aplicacion_estancia.entidades

data class Paciente(
    var id: String = "",
    val nombre: String = "",
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val fechaNacimiento: String = "",
    val contacto: String = ""
)
