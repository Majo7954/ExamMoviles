package com.calyrsoft.ucbp1.features.dollar.data.repository

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel

interface DollarRepository {
    suspend fun getDollarRates(): DollarModel
    suspend fun saveDollarRates(rates: DollarModel)
    suspend fun getDollarHistory(): List<DollarModel>

    fun getDollarRatesFlow(): kotlinx.coroutines.flow.Flow<DollarModel>
}