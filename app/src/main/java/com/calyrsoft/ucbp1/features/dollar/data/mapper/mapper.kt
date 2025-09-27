package com.calyrsoft.ucbp1.features.dollar.data.mapper

import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel

// Mapper de Domain Model a Entity (para guardar en Room)
fun DollarModel.toEntity(): DollarEntity {
    return DollarEntity(
        officialBuy = dollarOfficialBuy?.toDoubleOrNull() ?: 0.0,
        officialSell = dollarOfficialSell?.toDoubleOrNull() ?: 0.0,
        parallelBuy = dollarParallelBuy?.toDoubleOrNull() ?: 0.0,
        parallelSell = dollarParallelSell?.toDoubleOrNull() ?: 0.0,
        lastUpdate = lastUpdate ?: "No disponible",
        timestamp = timestamp
    )
}

// Mapper de Entity a Domain Model
fun DollarEntity.toDomain(): DollarModel {
    return DollarModel(
        dollarOfficialBuy = officialBuy.toString(),
        dollarOfficialSell = officialSell.toString(),
        dollarParallelBuy = parallelBuy.toString(),
        dollarParallelSell = parallelSell.toString(),
        lastUpdate = lastUpdate,
        timestamp = timestamp
    )
}