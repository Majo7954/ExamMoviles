package com.calyrsoft.ucbp1.features.dollar.domain.usecase

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.repository.DollarRepository
import kotlinx.coroutines.flow.first

class FetchDollarUseCase(private val repository: DollarRepository) {
    suspend operator fun invoke(): DollarModel {
        // Como getDollar() retorna Flow, usamos .first() para obtener el primer valor
        return repository.getDollar().first()
    }
}