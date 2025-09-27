package com.calyrsoft.ucbp1.features.dollar.domain.model

data class DollarModel(
    var dollarOfficialBuy: String? = null,      // NUEVO: Compra oficial
    var dollarOfficialSell: String? = null,     // NUEVO: Venta oficial
    var dollarParallelBuy: String? = null,      // NUEVO: Compra paralelo
    var dollarParallelSell: String? = null,     // NUEVO: Venta paralelo
    var lastUpdate: String? = null,             // NUEVO: Fecha actualización
    var timestamp: Long = 0L
) {
    // Constructor secundario para mantener compatibilidad con Firebase
    constructor() : this(null, null, null, null, null, 0L)

    // Constructor compatible con tu código actual de Firebase
    constructor(
        official: String?,
        parallel: String?,
        timestamp: Long
    ) : this(
        dollarOfficialBuy = official ?: "0",
        dollarOfficialSell = official ?: "0",
        dollarParallelBuy = parallel ?: "0",
        dollarParallelSell = parallel ?: "0",
        lastUpdate = "Actualizado: ${java.text.SimpleDateFormat("HH:mm:ss").format(java.util.Date())}",
        timestamp = timestamp
    )
}