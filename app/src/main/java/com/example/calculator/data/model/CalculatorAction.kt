package com.example.calculator.data.model

sealed class CalculatorAction {
    data class Number(val number: Int) : CalculatorAction()
    object Clear : CalculatorAction()
    object Delete : CalculatorAction()
    object Decimal : CalculatorAction()
    data class Operation(val operation: CalculatorOperation) : CalculatorAction()
    object Equals : CalculatorAction()
    object Percentage: CalculatorAction()
    object Toggle: CalculatorAction()
}
