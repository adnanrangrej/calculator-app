package com.example.calculator.data.repository

import com.example.calculator.data.model.CalculatorOperation
class CalculatorRepository {
    fun calculate(num1: Double, num2: Double, operation: CalculatorOperation): Double? {
        return when (operation) {
            CalculatorOperation.ADD -> num1 + num2
            CalculatorOperation.SUBTRACT -> num1 - num2
            CalculatorOperation.MULTIPLY -> num1 * num2
            CalculatorOperation.DIVIDE -> {
                if (num2 == 0.0){
                    null
                }
                else
                num1 / num2
            }
        }
    }
}