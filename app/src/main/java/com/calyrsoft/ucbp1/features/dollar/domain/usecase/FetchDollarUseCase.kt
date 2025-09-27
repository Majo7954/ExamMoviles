package com.calyrsoft.ucbp1.features.dollar.domain.usecase

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.repository.DollarRepository

class FetchDollarUseCase(private val repository: DollarRepository) {
    suspend operator fun invoke(): DollarModel {
        return repository.getDollarRates()
    }
}