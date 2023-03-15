package com.example.simplesoundquicksettings.services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.media.AudioManager
import android.service.quicksettings.TileService
import com.example.simplesoundquicksettings.R
import com.example.simplesoundquicksettings.Utils

class SoundTile : TileService() {
    private fun updateSoundTile() {
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        when (audioManager.ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> {
                qsTile.icon =
                    Icon.createWithResource(this, R.drawable.baseline_notifications_active_24)
                qsTile.label = getString(R.string.sound)
            }
            AudioManager.RINGER_MODE_VIBRATE -> {
                qsTile.icon = Icon.createWithResource(this, R.drawable.baseline_vibration_24)
                qsTile.label = getString(R.string.vibration)
            }
            AudioManager.RINGER_MODE_SILENT -> {
                qsTile.icon =
                    Icon.createWithResource(this, R.drawable.baseline_notifications_off_24)
                qsTile.label = getString(R.string.silent)
            }
        }
        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()

        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        when (audioManager.ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> audioManager.ringerMode =
                AudioManager.RINGER_MODE_VIBRATE
            AudioManager.RINGER_MODE_VIBRATE -> {
                audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
                val mNotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
            }
            AudioManager.RINGER_MODE_SILENT -> audioManager.ringerMode =
                AudioManager.RINGER_MODE_NORMAL
        }

        updateSoundTile()
    }

    override fun onStartListening() {
        super.onStartListening()
        updateSoundTile()
    }

    override fun onCreate() {
        super.onCreate()
        Utils.requestDoNotDisturbPermissionOrSetDoNotDisturbApi23AndUp(this)

        this.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == AudioManager.RINGER_MODE_CHANGED_ACTION) {
                    updateSoundTile() //todo save last state
                }
            }
        }, IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION))
    }
}