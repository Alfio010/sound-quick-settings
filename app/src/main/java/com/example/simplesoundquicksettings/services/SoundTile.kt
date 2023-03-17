package com.example.simplesoundquicksettings.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.media.AudioManager
import android.service.quicksettings.TileService
import com.example.simplesoundquicksettings.R
import com.example.simplesoundquicksettings.Utils
import java.util.concurrent.atomic.AtomicBoolean

class SoundTile : TileService() {
    private var latestAudioStateUpdate: Int? = null
    private val isBroadcastRegistered: AtomicBoolean = AtomicBoolean(false)

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == AudioManager.RINGER_MODE_CHANGED_ACTION) {
                updateSoundTile()
            }
        }
    }

    private fun updateSoundTile() {
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        if (latestAudioStateUpdate == audioManager.ringerMode) {
            latestAudioStateUpdate = null
            return
        }

        when (audioManager.ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> {
                qsTile.label = getString(R.string.sound)
                qsTile.icon =
                    Icon.createWithResource(this, R.drawable.baseline_notifications_active_24)
            }

            AudioManager.RINGER_MODE_VIBRATE -> {
                qsTile.label = getString(R.string.vibration)
                qsTile.icon = Icon.createWithResource(this, R.drawable.baseline_vibration_24)
            }

            AudioManager.RINGER_MODE_SILENT -> {
                qsTile.label = getString(R.string.silent)
                qsTile.icon =
                    Icon.createWithResource(this, R.drawable.baseline_notifications_off_24)
            }
        }
        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()

        val audio = getSystemService(AUDIO_SERVICE) as AudioManager

        audio.ringerMode = when (audio.ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> AudioManager.RINGER_MODE_VIBRATE

            AudioManager.RINGER_MODE_VIBRATE -> {
                audio.ringerMode = AudioManager.RINGER_MODE_NORMAL
                AudioManager.RINGER_MODE_SILENT
            }

            AudioManager.RINGER_MODE_SILENT -> AudioManager.RINGER_MODE_NORMAL

            else -> {
                return
            }
        }

        latestAudioStateUpdate = audio.ringerMode

        updateSoundTile()
    }

    override fun onStartListening() {
        super.onStartListening()

        updateSoundTile()

        if (isBroadcastRegistered.compareAndSet(false, true)) {
            this.registerReceiver(
                broadcastReceiver,
                IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION)
            )
        }
    }

    override fun onStopListening() {
        super.onStopListening()

        try {
            if (isBroadcastRegistered.compareAndSet(true, false)) {
                this.unregisterReceiver(broadcastReceiver)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (!Utils.isDoNotDisturbPermissionGranted(this)) {
            Utils.requestDoNotDisturbPermission(this)
        }

        if (isBroadcastRegistered.compareAndSet(false, true)) {
            this.registerReceiver(
                broadcastReceiver,
                IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            if (isBroadcastRegistered.compareAndSet(true, false)) {
                this.unregisterReceiver(broadcastReceiver)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onTileAdded() {
        super.onTileAdded()

        if (isBroadcastRegistered.compareAndSet(false, true)) {
            this.registerReceiver(
                broadcastReceiver,
                IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION)
            )
        }
    }

    override fun onTileRemoved() {
        super.onTileRemoved()

        try {
            if (isBroadcastRegistered.compareAndSet(true, false)) {
                this.unregisterReceiver(broadcastReceiver)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}