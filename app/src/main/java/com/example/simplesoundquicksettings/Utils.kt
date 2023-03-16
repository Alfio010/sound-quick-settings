package com.example.simplesoundquicksettings

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast

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
}