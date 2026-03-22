package mx.edu.itson.aplicacion_estancia.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room3.ForeignKey

@Entity(
    tableName = "detalle_evaluaciones",
    foreignKeys = [
        ForeignKey(
            entity = Evaluacion::class,
            parentColumns = ["id"],
            childColumns = ["evaluacionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DetalleEvaluacion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val evaluacionId: Int,
    val seccion: String, // Ej: "Orientación", "Memoria", "Equilibrio"
    val puntajeObtenido: Int,
    val puntajeMaximo: Int
)
