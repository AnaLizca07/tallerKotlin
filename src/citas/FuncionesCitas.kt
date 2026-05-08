package citas

fun crearMedicos(): List<Medico> {
    val horarios = mutableListOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17)

    val medicos = listOf(
        Medico("Dr. Carlos Lopez", "Cardiologia", horarios.toMutableList()),
        Medico("Dra. Maria Garcia", "Cardiologia", horarios.toMutableList()),
        Medico("Dr. Juan Rodriguez", "Dermatologia", horarios.toMutableList()),
        Medico("Dra. Ana Martinez", "Ginecologia", horarios.toMutableList()),
        Medico("Dr. Pedro Sanchez", "Neurologia", horarios.toMutableList())
    )
    return medicos
}

fun registrarPaciente(): Paciente {
    println("\n--- Registro de Paciente ---")
    print("Nombre: ")
    val nombre = readln()

    print("Documento: ")
    val documento = readln()

    print("EPS: ")
    val eps = readln()

    return Paciente(nombre, documento, eps)
}

fun obtenerEspecialidades(medicos: List<Medico>): List<String> {
    val especialidades = mutableListOf<String>()
    var i = 0
    while (i < medicos.size) {
        val especialidad = medicos[i].especialidad
        var existe = false
        var j = 0
        while (j < especialidades.size) {
            if (especialidades[j] == especialidad) {
                existe = true
                break
            }
            j++
        }
        if (!existe) {
            especialidades.add(especialidad)
        }
        i++
    }
    return especialidades
}

fun mostrarEspecialidades(especialidades: List<String>) {
    println("\n--- Especialidades Disponibles ---")
    var i = 0
    while (i < especialidades.size) {
        println((i + 1).toString() + ". " + especialidades[i])
        i++
    }
}

fun obtenerMedicosPorEspecialidad(medicos: List<Medico>, especialidad: String): List<Medico> {
    val medicosEspecialidad = mutableListOf<Medico>()
    var i = 0
    while (i < medicos.size) {
        if (medicos[i].especialidad == especialidad) {
            medicosEspecialidad.add(medicos[i])
        }
        i++
    }
    return medicosEspecialidad
}

fun mostrarMedicos(medicos: List<Medico>) {
    println("\n--- Medicos Disponibles ---")
    var i = 0
    while (i < medicos.size) {
        println((i + 1).toString() + ". " + medicos[i].nombre)
        i++
    }
}

fun mostrarHorariosDisponibles(medico: Medico) {
    println("\n--- Horarios Disponibles para " + medico.nombre + " ---")
    if (medico.horariosDisponibles.isEmpty()) {
        println("No hay horarios disponibles")
    } else {
        var i = 0
        while (i < medico.horariosDisponibles.size) {
            println(medico.horariosDisponibles[i].toString() + ":00")
            i++
        }
    }
}

fun agendarCita(paciente: Paciente, medico: Medico, hora: Int, citas: MutableList<Cita>): Boolean {
    var horaExiste = false
    var i = 0
    while (i < medico.horariosDisponibles.size) {
        if (medico.horariosDisponibles[i] == hora) {
            horaExiste = true
            break
        }
        i++
    }

    if (!horaExiste) {
        println("Hora no disponible")
        return false
    }

    val cita = Cita(paciente, medico, hora)
    citas.add(cita)

    medico.horariosDisponibles.remove(hora)

    println("Cita agendada exitosamente")
    return true
}

fun buscarCitaPorDocumento(citas: MutableList<Cita>, documento: String): Cita? {
    var i = 0
    while (i < citas.size) {
        if (citas[i].paciente.documento == documento) {
            return citas[i]
        }
        i++
    }
    return null
}

fun cancelarCita(citas: MutableList<Cita>, documento: String): Boolean {
    var i = 0
    while (i < citas.size) {
        if (citas[i].paciente.documento == documento) {
            val cita = citas[i]
            cita.medico.horariosDisponibles.add(cita.hora)
            citas.removeAt(i)
            println("Cita cancelada exitosamente")
            return true
        }
        i++
    }
    println("No se encontro cita para ese documento")
    return false
}

fun generarResumenCitas(citas: List<Cita>) {
    if (citas.isEmpty()) {
        println("No hay citas registradas")
        return
    }

    println("\n========== RESUMEN DE CITAS ==========")
    var i = 0
    while (i < citas.size) {
        println((i + 1).toString() + ". " + citas[i].toString())
        i++
    }
    println("=====================================\n")
}