package com.calyrsoft.ucbp1.features.dollar.data.datasource

import com.calyrsoft.ucbp1.features.dollar.data.database.dao.DollarDao
import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.data.mapper.toDomain
import com.calyrsoft.ucbp1.features.dollar.data.mapper.toEntity

class DollarLocalDataSource(private val dollarDao: DollarDao) {

    suspend fun saveDollarRate(rates: DollarModel) {
        dollarDao.insertRate(rates.toEntity())
    }

    suspend fun getLatestDollarRate(): DollarModel? {
        return dollarDao.getLatestRate()?.toDomain()
    }

    suspend fun getDollarHistory(): List<DollarModel> {
        return dollarDao.getHistory().map { it.toDomain() }
    }
}