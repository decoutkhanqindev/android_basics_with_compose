package com.example.schedule_app.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun TimePickerDialog(
  onDismiss: () -> Unit,
  onConfirm: () -> Unit,
  content: @Composable () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    dismissButton = {
      TextButton(onClick = { onDismiss() }) {
        Text("Dismiss")
      }
    },
    confirmButton = {
      TextButton(onClick = { onConfirm() }) {
        Text("OK")
      }
    },
    text = { content() }
  )
}