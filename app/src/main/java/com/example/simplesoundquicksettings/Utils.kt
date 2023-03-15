package com.example.simplesoundquicksettings

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings

object Utils {
    fun requestDoNotDisturbPermissionOrSetDoNotDisturbApi23AndUp(applicationContext: Context) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (!notificationManager.isNotificationPolicyAccessGranted) {
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent)
        }
    }
}