package citas

class Cita(
    val paciente: Paciente,
    val medico: Medico,
    val hora: Int
) {
    override fun toString(): String {
        return "Paciente: " + paciente.nombre + " | Medico: " + medico.nombre +
                " | Especialidad: " + medico.especialidad + " | Hora: " + hora.toString() + ":00"
    }
}