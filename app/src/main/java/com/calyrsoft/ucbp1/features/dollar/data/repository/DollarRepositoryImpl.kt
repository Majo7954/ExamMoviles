package com.calyrsoft.ucbp1.features.dollar.data.repository

import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.calyrsoft.ucbp1.features.dollar.data.datasource.RealTimeRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class DollarRepositoryImpl(
    private val remoteDataSource: RealTimeRemoteDataSource,
    private val localDataSource: DollarLocalDataSource
) : DollarRepository {

    override suspend fun getDollarRates(): DollarModel {
        try {
            // Usar el método correcto: getDollarUpdates() que retorna Flow
            val remoteRates = remoteDataSource.getDollarUpdates().first()

            // Guardar en local para histórico
            localDataSource.saveDollarRate(remoteRates)
            return remoteRates
        } catch (e: Exception) {
            // Fallback a base de datos local
            return localDataSource.getLatestDollarRate() ?: throw e
        }
    }

    // Si necesitas el Flow para updates en tiempo real
    fun getDollarRatesFlow(): Flow<DollarModel> = remoteDataSource.getDollarUpdates()

    override suspend fun saveDollarRates(rates: DollarModel) {
        localDataSource.saveDollarRate(rates)
    }

    override suspend fun getDollarHistory(): List<DollarModel> {
        return localDataSource.getDollarHistory()
    }
}