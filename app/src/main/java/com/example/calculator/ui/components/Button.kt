package com.example.calculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.view.ButtonGrid

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    symbol: String,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .aspectRatio(1f)
            .clickable(onClick = onClick)
            .background(backgroundColor)

    ) { Text(
        text = symbol,
        fontSize = 36.sp,
        color = Color.White
    ) }
}


@Preview
@Composable
private fun ButtonGridPreview() {
}