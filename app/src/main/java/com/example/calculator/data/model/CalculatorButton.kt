package com.example.calculator.data.model

import androidx.compose.ui.graphics.Color

data class CalculatorButton(
    val label: String,
    val action: CalculatorAction,
    val backgroundColor: Color = Color.DarkGray
    )
