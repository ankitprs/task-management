package com.aicallvaani.taskmanagement.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingSheet(
    onCrashDatabase: () -> Unit,
    onSwitchApiList: () -> Unit,
    onDeleteAllTasks: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        SettingItem(
            title = "Crash Room Database",
            description = "Simulate a crash for testing purposes.",
            onClick = onCrashDatabase
        )

        SettingItem(
            title = "Switch API List",
            description = "Toggle between API lists for debugging.",
            onClick = onSwitchApiList
        )

        SettingItem(
            title = "Delete All Tasks",
            description = "Delete all tasks from the database.",
            onClick = onDeleteAllTasks
        )

        SettingItem(
            title = "Share This App",
            description = "Share this app with your friends.",
            onClick = {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Check out this amazing app!")
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share App"))
            }
        )

        SettingItem(
            title = "Contact Me",
            description = "Reach out for support or feedback.",
            onClick = {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:ankitpr2001@gmail.com")
                    putExtra(Intent.EXTRA_SUBJECT, "Support Request")
                }
                context.startActivity(emailIntent)
            }
        )
    }
}

@Composable
fun SettingItem(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}