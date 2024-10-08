package com.example.calculator.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.components.CalculatorButton
import com.example.calculator.ui.viewmodel.CalculatorViewModel

@Composable
fun ButtonGrid(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel,
) {
    val buttons = viewModel.buttons
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(buttons){ button ->
            CalculatorButton(
                backgroundColor = button.backgroundColor,
                symbol = button.label,
                onClick = { viewModel.onAction(button.action) }
            )
        }
    }
}
@Preview
@Composable
private fun ButtonGridPreview() {
}