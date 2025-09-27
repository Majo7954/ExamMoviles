package com.calyrsoft.ucbp1.features.dollar.data.database.dao

import androidx.room.*
import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity

@Dao
interface DollarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(rate: DollarEntity)

    @Query("SELECT * FROM dollar_rates ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestRate(): DollarEntity?

    @Query("SELECT * FROM dollar_rates ORDER BY timestamp DESC")
    suspend fun getHistory(): List<DollarEntity>
}