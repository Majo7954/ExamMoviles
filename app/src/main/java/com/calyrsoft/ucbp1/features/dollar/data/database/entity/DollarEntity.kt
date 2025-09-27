package com.calyrsoft.ucbp1.features.dollar.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dollar_rates")
data class DollarEntity(
    @PrimaryKey val id: Int = 1,
    val officialBuy: Double,
    val officialSell: Double,
    val parallelBuy: Double,
    val parallelSell: Double,
    val lastUpdate: String,
    val timestamp: Long = System.currentTimeMillis()
)