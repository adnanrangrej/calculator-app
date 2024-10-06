package com.example.calculator.data.model

sealed class CalculatorOperation(val symbol: String){
    object ADD: CalculatorOperation("+")
    object SUBTRACT: CalculatorOperation("-")
    object DIVIDE: CalculatorOperation("/")
    object MULTIPLY: CalculatorOperation("x")
}