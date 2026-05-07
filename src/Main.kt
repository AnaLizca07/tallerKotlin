fun main() {

    // Crear el menu
    val menu = crearMenu()

    // Lista para almacenar todos los pedidos
    val pedidos = mutableListOf<Pedido>()

    // Variable para controlar el bucle principal
    var continuarPedidos = true

    println("==============================================")
    println("   BIENVENIDO AL SISTEMA DE PEDIDOS")
    println("==============================================")

    // Bucle principal para multiples pedidos
    while (continuarPedidos) {
        println("\n--- Crear nuevo pedido ---\n")

        // Registrar cliente
        val cliente = registrarCliente()

        // Crear pedido para el cliente
        val pedido = Pedido(cliente)

        // Variable para el menu del pedido
        var continuarAgregarProductos = true

        // Bucle para agregar/eliminar productos al pedido
        while (continuarAgregarProductos) {
            println("\n========== MENU DEL PEDIDO ==========")
            println("1. Ver menu completo")
            println("2. Filtrar productos por precio")
            println("3. Agregar producto al pedido")
            println("4. Ver pedido actual")
            println("5. Eliminar producto del pedido")
            println("6. Finalizar pedido")
            println("====================================")

            print("Seleccione una opcion (1-6): ")
            val opcion = readlnOrNull() ?: "0"

            when (opcion) {
                "1" -> {
                    // Ver menu completo
                    mostrarMenu(menu)
                }
                "2" -> {
                    // Filtrar por precio
                    print("Ingrese precio minimo: ")
                    val precioMinStr = readlnOrNull() ?: "0"
                    val precioMin = try {
                        precioMinStr.toDouble()
                    } catch (e: Exception) {
                        println("Error: Precio invalido.\n")
                        0.0
                    }

                    print("Ingrese precio maximo: ")
                    val precioMaxStr = readlnOrNull() ?: "100000"
                    val precioMax = try {
                        precioMaxStr.toDouble()
                    } catch (e: Exception) {
                        println("Error: Precio invalido.\n")
                        100000.0
                    }

                    val filtrados = filtrarPorPrecio(menu, precioMin, precioMax)
                    mostrarProductosFiltrados(filtrados)
                }
                "3" -> {
                    // Agregar producto
                    agregarItem(pedido, menu)
                }
                "4" -> {
                    // Ver pedido actual
                    mostrarResumenPedido(pedido)
                }
                "5" -> {
                    // Eliminar producto
                    eliminarItem(pedido, menu)
                }
                "6" -> {
                    // Finalizar pedido
                    val cantItems = pedido.cantidadItems()

                    if (cantItems == 0) {
                        println("Error: El pedido esta vacio. Agregue productos antes de finalizar.\n")
                    } else {
                        // Aplicar descuento automatico
                        aplicarDescuento(pedido)

                        // Agregar pedido a la lista
                        pedidos.add(pedido)

                        // Mostrar resumen final
                        println("\n========== PEDIDO FINALIZADO ==========")
                        mostrarResumenPedido(pedido)

                        // Preguntar si desea continuar
                        print("Desea crear otro pedido? (si/no): ")
                        val respuesta = readlnOrNull() ?: "no"

                        if (respuesta == "si" || respuesta == "SI" || respuesta == "s" || respuesta == "S") {
                            continuarAgregarProductos = false
                        } else {
                            continuarAgregarProductos = false
                            continuarPedidos = false
                        }
                    }
                }
                else -> {
                    println("Opcion no valida. Intente de nuevo.\n")
                }
            }
        }
    }

    // Mostrar reporte general
    if (pedidos.isNotEmpty()) {
        generarReporte(pedidos)
    } else {
        println("\nNo se registraron pedidos.\n")
    }

    println("Gracias por usar nuestro sistema de pedidos.")
}

fun crearMenu(): List<MenuItem> {
    val menu = listOf(
        MenuItem(1, "Hamburguesa Sencilla", "Carne molida, pan, lechuga", 15000.0),
        MenuItem(2, "Hamburguesa Doble", "Doble carne, queso, bacon", 22000.0),
        MenuItem(3, "Pizza Margarita", "Queso, tomate, oregano", 25000.0),
        MenuItem(4, "Pizza Pepperoni", "Queso, pepperoni, tomate", 28000.0),
        MenuItem(5, "Ensalada Verde", "Lechuga, tomate, cebolla", 12000.0),
        MenuItem(6, "Ensalada Cesar", "Lechuga, queso, aderezo especial", 18000.0),
        MenuItem(7, "Refresco 2L", "Bebida carbonatada", 8000.0),
        MenuItem(8, "Jugo Natural", "Jugo de naranja fresco", 6000.0),
        MenuItem(9, "Postre Brownie", "Chocolate derretido, nueces", 12000.0),
        MenuItem(10, "Postre Helado", "Helado variado, frutas", 9000.0)
    )
    return menu
}

fun mostrarMenu(menu: List<MenuItem>) {
    println("Menú del súper restaurante :D")
    var i = 0
    while (i < menu.size) {
        val item = menu[i]
        println(item.id.toString() + ". " + item.nombre + " - $" + item.precio)
        println("   " + item.descripcion)
        i++
    }
    println()
}

fun filtrarPorPrecio(menu: List<MenuItem>, precioMin: Double, precioMax: Double): List<MenuItem> {
    val filtrados = mutableListOf<MenuItem>()
    var i = 0
    while (i < menu.size) {
        val item = menu[i]
        if (item.precio in precioMin..precioMax) {
            filtrados.add(item)
        }
        i++
    }
    return filtrados
}

fun buscarItemPorId(menu: List<MenuItem>, id: Int): MenuItem? {
    var i = 0
    while (i < menu.size) {
        if (menu[i].id == id) {
            return menu[i]
        }
        i++
    }
    return null
}

fun registrarCliente(): Cliente{
    println("Ingresa el nombre del cliente")
    val nombre = readln() ?: ""

    println("Ingresa la cédula del cliente")
    val cedula = readln() ?: ""

    println("Ingresa el telefono del cliente")
    val tel = readln() ?: ""

    return  Cliente(nombre, cedula, tel)
}

fun agregarItem(pedido: Pedido, menu: List<MenuItem>) {
    println("Ingresa el id del producto que quieres agregar, o presiona 0 pa salir")
    val entrada = readln() ?: ""

    val id = try{
        entrada.toInt()
    } catch (e: NumberFormatException) {
        println("Error, porfis ingresa un numero valido")
        return
    }

    if (id == 0){
        return
    }

    val item = buscarItemPorId(menu, id)

    if (item != null) {
        pedido.agregarItem(item)
        println("El producto se agregó re bien")
    } else{
        println("No existe un item con ese ID :((")
    }
}

fun eliminarItem(pedido: Pedido, menu: List<MenuItem>) {
    println("Ingresa el ID del producto que quieres eliminar o presiona 0 pa salir")
    val entrada = readln() ?: ""

    val id = try{
        entrada.toInt()
    } catch (e: NumberFormatException) {
        println("Error, porfis ingresa un numero valido")
        return
    }

    val item = buscarItemPorId(menu, id)
    if (item != null) {
        pedido.eliminarItem(item.id)
        println("El producto se eliminó re bien")
    } else{
        println("No existe un item con ese ID :((")
    }
}

fun aplicarDescuento(pedido: Pedido) {
    val subtotal = pedido.subtotal()
    if (subtotal > 100000.0){
        pedido.aplicarDescuento(10.0)
    }
}

// ============= FUNCIONES PARA REPORTES =============

// Mostrar resumen final de un pedido
fun mostrarResumenPedido(pedido: Pedido) {
    println(pedido)
    println()
}

// Generar reporte de multiples pedidos
fun generarReporte(pedidos: List<Pedido>) {
    println("\n\n============ REPORTE GENERAL DE PEDIDOS ============")

    var totalPedidos = 0
    var totalVentas = 0.0
    var totalItems = 0

    var i = 0
    while (i < pedidos.size) {
        val pedido = pedidos[i]
        val numItems = pedido.cantidadItems()
        val totalPedido = pedido.total()

        println("\nPedido " + (i + 1).toString() + ":")
        println("  Cliente: " + pedido.cliente.nombre)
        println("  Items: $numItems")
        println("  Total: $$totalPedido")

        totalPedidos += 1
        totalVentas += totalPedido
        totalItems += numItems

        i++
    }

    println("\n====================================================")
    println("Total de pedidos: $totalPedidos")
    println("Total de items: $totalItems")
    println("Venta total: $$totalVentas")
    println("====================================================\n")
}

fun mostrarProductosFiltrados(productos: List<MenuItem>) {
    if (productos.isEmpty()) {
        println("No hay productos en ese rango de precios.\n")
    } else {
        println("\n========== PRODUCTOS ENCONTRADOS ==========")
        var i = 0
        while (i < productos.size) {
            val item = productos[i]
            println(item.id.toString() + ". " + item.nombre + " - $" + item.precio + " COP")
            i++
        }
        println("===========================================\n")
    }
}