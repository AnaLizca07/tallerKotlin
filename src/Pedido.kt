class Pedido (
    val cliente : Cliente,
    var descuento: Double = 0.0
) {
    private val items = mutableListOf<MenuItem>()

    fun agregarItem(item: MenuItem){
        items.add(item)
    }

    fun eliminarItem(id: Int){
        var i = 0
        while (i < items.size) {
            if (items[i].id == id) {
                items.removeAt(i)
                break
            }
            i++
        }
    }

    fun subtotal(): Double {
        var total = 0.0
        var i = 0
        while (i < items.size) {
            total += items[i].precio
            i ++
        }
        return total
    }

    fun aplicarDescuento(porcentaje: Double){
        descuento = porcentaje
    }

    fun montoDescuento(): Double{
        val subtotal = subtotal()
        return subtotal * (descuento / 100)
    }

    fun total(): Double{
        val subtotal = subtotal()
        val montoDesc = montoDescuento()
        return subtotal - montoDesc
    }

    fun listaItems(): List<MenuItem>{
        return items.toList()
    }

    fun cantidadItems(): Int{
        return items.size
    }

    override fun toString(): String {
        var resultado = "===== PEDIDO =====\n"
        resultado = resultado + "Cliente: " + cliente.nombre + "\n"
        resultado = resultado + "Cedula: " + cliente.cedula + "\n"
        resultado = resultado + "Telefono: " + cliente.telefono + "\n\n"
        resultado += "Items:\n"

        if (items.isEmpty()) {
            resultado = "$resultado  (Vacio)\n"
        } else {
            var i = 0
            while (i < items.size) {
                val item = items[i]
                resultado = resultado + "  " + item.id + ". " + item.nombre + ": $" + item.precio + "\n"
                i++
            }
        }

        val subtotal = subtotal()
        val montoDesc = montoDescuento()
        val totalFinal = total()

        resultado = "$resultado\nSubtotal: $$subtotal\n"
        if (descuento > 0) {
            resultado = resultado + "Descuento: " + descuento + "%\n"
            resultado = resultado + "Monto Descuento: $" + montoDesc + "\n"
        }
        resultado = resultado + "Total: $" + totalFinal

        return resultado
    }

}