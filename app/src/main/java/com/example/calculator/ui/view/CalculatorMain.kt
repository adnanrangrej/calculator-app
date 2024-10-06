package com.example.calculator.ui.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.data.repository.CalculatorRepository
import com.example.calculator.ui.viewmodel.CalculatorViewModel

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    val repo = CalculatorRepository()
    val viewModel = viewModel<CalculatorViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CalculatorViewModel(
                    repo
                ) as T
            }

        }
    )
    CalculatorScreen(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        viewModel = viewModel
    )
}

@Preview
@Composable
private fun CalculatorPreview() {
    Calculator()
}