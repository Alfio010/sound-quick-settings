package com.alftendev.simplesoundquicksettings.utils

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.alftendev.simplesoundquicksettings.R

object Utils {
    fun isDoNotDisturbPermissionGranted(applicationContext: Context): Boolean {
        return (applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).isNotificationPolicyAccessGranted
    }

    fun requestDoNotDisturbPermission(applicationContext: Context) {
        Toast.makeText(
            applicationContext,
            applicationContext.getString(R.string.toast_text),
            Toast.LENGTH_LONG
        ).show()

        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        applicationContext.startActivity(intent)
    }

    fun toggleDoNotDisturb(
        context: Context
    ): Boolean {
        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java)

        if (notificationManager?.isNotificationPolicyAccessGranted != true) {
            requestDoNotDisturbPermission(context)
            return isDndActive(context)
        }

        val dndIsActive = isDndActive(context)

        if (dndIsActive) {
            changeDoNotDisturb(context, NotificationManager.INTERRUPTION_FILTER_ALL)
            return false
        } else {
            changeDoNotDisturb(context, NotificationManager.INTERRUPTION_FILTER_PRIORITY)
            return true
        }
    }

    fun isDndActive(context: Context): Boolean {
        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java)
        val currentFilter = notificationManager?.currentInterruptionFilter
        return currentFilter != null && currentFilter != NotificationManager.INTERRUPTION_FILTER_ALL
    }

    fun changeDoNotDisturb(context: Context, mode: Int) {
        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java)
        if (notificationManager?.isNotificationPolicyAccessGranted == true) {
            notificationManager.setInterruptionFilter(mode)
        }
    }

    fun openLink(context: Context, uri: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri.toUri()))
    }
}