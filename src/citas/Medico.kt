package citas

class Medico(
    val nombre: String,
    val especialidad: String,
    val horariosDisponibles: MutableList<Int>
) {
    override fun toString(): String {
        return "$nombre - $especialidad"
    }
}