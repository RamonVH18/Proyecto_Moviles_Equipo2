package mx.edu.itson.aplicacion_estancia.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "evaluaciones",
    foreignKeys = [
        ForeignKey(
            entity = Paciente::class,
            parentColumns = ["id"],
            childColumns = ["pacienteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"]
        )
    ]
)
data class Evaluacion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pacienteId: Int,
    val usuarioId: Int,
    val tipoInstrumento: String, // "MMSE" o "TINETTI", etc etc
    val fechaAplicacion: Long,
    val puntajeTotal: Int,
    val observaciones: String
)
