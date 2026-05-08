package citas

fun main() {
    val medicos = crearMedicos()
    val citas = mutableListOf<Cita>()
    var continuar = true

    println("========== SISTEMA DE CITAS MEDICAS ==========")

    while (continuar) {
        println("\n--- MENU PRINCIPAL ---")
        println("1. Agendar cita")
        println("2. Cancelar cita")
        println("3. Ver resumen de citas")
        println("4. Salir")
        print("Seleccione opcion: ")

        val opcion = readln()

        when (opcion) {
            "1" -> {
                val paciente = registrarPaciente()

                val especialidades = obtenerEspecialidades(medicos)
                mostrarEspecialidades(especialidades)

                print("Seleccione especialidad: ")
                val numEspecialidad = readln().toIntOrNull() ?: 0

                if (numEspecialidad < 1 || numEspecialidad > especialidades.size) {
                    println("Opcion invalida")
                } else {
                    val especialidadSeleccionada = especialidades[numEspecialidad - 1]
                    val medicosPorEspecialidad = obtenerMedicosPorEspecialidad(medicos, especialidadSeleccionada)

                    mostrarMedicos(medicosPorEspecialidad)

                    print("Seleccione medico: ")
                    val numMedico = readln().toIntOrNull() ?: 0

                    if (numMedico < 1 || numMedico > medicosPorEspecialidad.size) {
                        println("Opcion invalida")
                    } else {
                        val medicoSeleccionado = medicosPorEspecialidad[numMedico - 1]

                        mostrarHorariosDisponibles(medicoSeleccionado)

                        print("Seleccione hora: ")
                        val hora = readln().toIntOrNull() ?: 0

                        agendarCita(paciente, medicoSeleccionado, hora, citas)
                    }
                }
            }
            "2" -> {
                print("Ingrese documento del paciente: ")
                val documento = readln()

                val cita = buscarCitaPorDocumento(citas, documento)
                if (cita != null) {
                    println("\nCita encontrada:")
                    println(cita.toString())
                    print("Confirmar cancelacion (si/no): ")
                    val confirmar = readln()
                    if (confirmar == "si" || confirmar == "SI") {
                        cancelarCita(citas, documento)
                    }
                } else {
                    println("No se encontro cita")
                }
            }
            "3" -> {
                generarResumenCitas(citas)
            }
            "4" -> {
                continuar = false
            }
            else -> {
                println("Opcion invalida")
            }
        }
    }

    println("Gracias por usar el sistema")
}