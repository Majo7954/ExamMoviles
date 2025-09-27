package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DollarScreen(viewModel: DollarViewModel) {
    val state by viewModel.uiState.collectAsState()

    when (val uiState = state) {
        is DollarViewModel.DollarUIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is DollarViewModel.DollarUIState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = uiState.message, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.refreshData() }) {
                    Text("Reintentar")
                }
            }
        }

        is DollarViewModel.DollarUIState.Success -> {
            val data = uiState.data
            val history = uiState.history

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = "Cotización del Dólar",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    DollarRateCard(
                        title = "Oficial",
                        buy = data.dollarOfficialBuy,
                        sell = data.dollarOfficialSell
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DollarRateCard(
                        title = "Paralelo",
                        buy = data.dollarParallelBuy,
                        sell = data.dollarParallelSell
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Última actualización: ${data.lastUpdate ?: "No disponible"}",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.refreshData() }) {
                        Text("Actualizar")
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Historial",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                items(history) { item ->
                    DollarRateCard(
                        title = "Registro",
                        buy = item.dollarOfficialBuy,
                        sell = item.dollarOfficialSell,
                        parallelBuy = item.dollarParallelBuy,
                        parallelSell = item.dollarParallelSell,
                        date = item.lastUpdate
                    )
                }
            }
        }
    }
}

@Composable
fun DollarRateCard(
    title: String,
    buy: String?,
    sell: String?,
    parallelBuy: String? = null,
    parallelSell: String? = null,
    date: String? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Compra: ${buy ?: "-"}")
            Text(text = "Venta: ${sell ?: "-"}")

            parallelBuy?.let {
                Text(text = "Paralelo Compra: $it")
            }
            parallelSell?.let {
                Text(text = "Paralelo Venta: $it")
            }
            date?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Fecha: $it", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
