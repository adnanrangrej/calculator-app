package com.example.calculator.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.calculator.data.model.CalculatorAction
import com.example.calculator.data.model.CalculatorButton
import com.example.calculator.data.model.CalculatorOperation
import com.example.calculator.data.model.CalculatorState
import com.example.calculator.data.repository.CalculatorRepository
import com.example.calculator.ui.theme.DarkGray
import com.example.calculator.ui.theme.LightGray
import com.example.calculator.ui.theme.Orange

class CalculatorViewModel(private val repository: CalculatorRepository) : ViewModel(){
    private val _state = mutableStateOf(CalculatorState())
    val state: State<CalculatorState> get() = _state

    private val _buttons = listOf(
        CalculatorButton(label = "AC", CalculatorAction.Clear, backgroundColor = LightGray),
        CalculatorButton(label = "C", CalculatorAction.Delete, backgroundColor = LightGray),
        CalculatorButton(label = "%", CalculatorAction.Percentage, backgroundColor = LightGray),
        CalculatorButton(label = "/", CalculatorAction.Operation(CalculatorOperation.DIVIDE), backgroundColor = Orange),
        CalculatorButton(label = "7", CalculatorAction.Number(7), backgroundColor = DarkGray),
        CalculatorButton(label = "8", CalculatorAction.Number(8), backgroundColor = DarkGray),
        CalculatorButton(label = "9", CalculatorAction.Number(9), backgroundColor = DarkGray),
        CalculatorButton(label = "X", CalculatorAction.Operation(CalculatorOperation.MULTIPLY), backgroundColor = Orange),
        CalculatorButton(label = "4", CalculatorAction.Number(4), backgroundColor = DarkGray),
        CalculatorButton(label = "5", CalculatorAction.Number(5), backgroundColor = DarkGray),
        CalculatorButton(label = "6", CalculatorAction.Number(6), backgroundColor = DarkGray),
        CalculatorButton(label = "-", CalculatorAction.Operation(CalculatorOperation.SUBTRACT), backgroundColor = Orange),
        CalculatorButton(label = "1", CalculatorAction.Number(1), backgroundColor = DarkGray),
        CalculatorButton(label = "2", CalculatorAction.Number(2), backgroundColor = DarkGray),
        CalculatorButton(label = "3", CalculatorAction.Number(3), backgroundColor = DarkGray),
        CalculatorButton(label = "+", CalculatorAction.Operation(CalculatorOperation.ADD), backgroundColor = Orange),
        CalculatorButton(label = "+/-", CalculatorAction.Toggle, backgroundColor = DarkGray),
        CalculatorButton(label = "0", CalculatorAction.Number(0), backgroundColor = DarkGray),
        CalculatorButton(label = ".", CalculatorAction.Decimal, backgroundColor = DarkGray),
        CalculatorButton(label = "=", CalculatorAction.Equals, backgroundColor = Orange),
    )
    val buttons:List<CalculatorButton> get() = _buttons

    fun onAction(action: CalculatorAction){
        when(action){
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Equals -> calculate()
            is CalculatorAction.Delete -> performDeletion()
            is CalculatorAction.Clear -> performClear()
            is CalculatorAction.Percentage -> calculatePercentage()
            is CalculatorAction.Toggle -> toggleValue()
        }
    }

    private fun toggleValue() {
            when {
                state.value.num2.isNotBlank() -> {
                    val num2Value = state.value.num2.toDoubleOrNull()
                    if (num2Value != null) {
                        // Toggle the sign of num2
                        _state.value = state.value.copy(num2 = (-num2Value).toString())
                    }
                }
                state.value.num1.isNotBlank() && state.value.operation == null -> {
                    val num1Value = state.value.num1.toDoubleOrNull()
                    if (num1Value != null) {
                        // Toggle the sign of num1
                        _state.value = state.value.copy(num1 = (-num1Value).toString())
                    }
                }
                else -> return
            }

    }

    private fun calculatePercentage() {
        when{
            state.value.num2.isNotBlank() -> {
                _state.value = state.value.copy(num2 = (state.value.num2.toDouble() / 100).toString())
            }
            state.value.num1.isNotBlank() && state.value.operation == null -> {
                _state.value = state.value.copy(num1 = (state.value.num1.toDouble() / 100).toString())
            }
            else -> {return}
        }

    }

    private fun calculate() {
        if (state.value.isError){
            resetCalculator()
        }
        if (state.value.num2.isNotBlank()) {
            val result = state.value.operation?.let {
                repository.calculate(
                    num1 = state.value.num1.toDouble(),
                    num2 = state.value.num2.toDouble(),
                    operation = it
                )
            }
            if (result != null) {
                _state.value = state.value.copy(
                    num1 = result.toString(),
                    num2 = "",
                    result = result.toString(),
                    operation = null
                )
            } else {
                _state.value = state.value.copy(isError = true)
                return
            }
        }

    }

    private fun performDeletion() {
        if (state.value.isError){
            resetCalculator()
            return
        }
        when{
            state.value.num2.isNotBlank() -> {
                _state.value = state.value.copy(num2 = state.value.num2.dropLast(1))
            }
            state.value.operation!=null -> {
                _state.value = state.value.copy(operation = null)
            }
            state.value.num1.isNotBlank() -> {
                _state.value = state.value.copy(num1 = state.value.num1.dropLast(1))
            }
        }
    }

    private fun performClear() {
        resetCalculator()
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.value.isError){
            resetCalculator()
        }
        if (state.value.num1.isNotBlank() && state.value.operation == null){
            _state.value = state.value.copy(operation = operation)
        }
        else if (state.value.num2.isNotBlank()){
            val result = state.value.operation?.let { repository.calculate(num1 = state.value.num1.toDouble(), num2 = state.value.num2.toDouble(), operation = it) }
            if (result !=null){
                _state.value = state.value.copy(
                    num1 = result.toString(),
                    num2 = "",
                    operation = operation,
                    result = result.toString()
                )
            } else{
                _state.value = state.value.copy(isError = true)
                return
            }
        }
    }

    private fun enterDecimal() {
        if (state.value.isError){
            resetCalculator()
        }
        if (state.value.num2.isNotBlank() && !state.value.num2.contains(".")){
            //append decimal in number 2
            _state.value = state.value.copy(num2 = state.value.num2 + ".")
        }
        else if (state.value.num1.isNotBlank() && !state.value.num1.contains(".") && state.value.operation == null){
            //append decimal in number 1
            _state.value = state.value.copy(num1 = state.value.num1 + ".")
        }

    }

    private fun enterNumber(number: Int) {
        if (state.value.isError){
            resetCalculator()
            return
        }
        if (state.value.operation != null) {
            //remove leading zeros in num2
            if (state.value.num2 == "0") {
                _state.value = state.value.copy(num2 = number.toString()) //remove leading zeros
            } else {
                //append entered number in num2
                _state.value = state.value.copy(num2 = state.value.num2 + number.toString())
            }
        } else {
            //remove leading zeros in num1
            if (state.value.num1 == "0") {
                _state.value = state.value.copy(num1 = number.toString())
            } else {
                //append entered number in num1
                _state.value = state.value.copy(num1 = state.value.num1 + number.toString())
            }
        }
    }

    private fun resetCalculator() {
        _state.value = CalculatorState()
    }
}
