package com.toanitdev.taskmanager.ui.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VSpacer(value: Dp = 8.dp) {
    Spacer(Modifier.height(value))
}

@Composable
fun HSpacer(value: Dp = 8.dp) {
    Spacer(Modifier.width(value))
}