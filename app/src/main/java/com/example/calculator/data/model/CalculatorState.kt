package com.example.calculator.data.model

data class CalculatorState (
    val num1: String = "",
    val num2: String = "",
    val operation: CalculatorOperation?= null,
    val result: String = "0",
    val isError: Boolean = false
)