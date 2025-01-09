package com.toanitdev.taskmanager.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun InputText(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    keyboardOptions: KeyboardOptions? = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
) {
    Column {

        Text(label, fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))
        Row(
            Modifier
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(corner = CornerSize(12.dp))
                )
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = value,
                onValueChange = { onValueChange(it) },
                textStyle = TextStyle(fontSize = 15.sp),
                keyboardOptions = keyboardOptions ?: KeyboardOptions.Default,
                modifier = Modifier.fillMaxWidth()
                )
        }
    }
}
