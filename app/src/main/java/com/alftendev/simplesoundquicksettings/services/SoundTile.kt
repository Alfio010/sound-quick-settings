package com.alftendev.simplesoundquicksettings.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.media.AudioManager
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.alftendev.simplesoundquicksettings.R
import com.alftendev.simplesoundquicksettings.utils.ImageUtils.getSoundStateDrawable
import com.alftendev.simplesoundquicksettings.utils.Utils

class SoundTile : TileService() {

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == AudioManager.RINGER_MODE_CHANGED_ACTION) {
                updateSoundTile()
            }
        }
    }

    private fun updateSoundTile() {
        if (qsTile == null) {
            return
        }

        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        if (qsTile == null) {
            return
        }

        qsTile.icon =
            Icon.createWithResource(this, getSoundStateDrawable(audioManager.ringerMode))

        when (audioManager.ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> {
                qsTile.label = getString(R.string.sound)
                qsTile.state = Tile.STATE_ACTIVE
            }

            AudioManager.RINGER_MODE_VIBRATE -> {
                qsTile.label = getString(R.string.vibration)
                qsTile.state = Tile.STATE_INACTIVE
            }

            AudioManager.RINGER_MODE_SILENT -> {
                qsTile.label = getString(R.string.silent)
                qsTile.state = Tile.STATE_INACTIVE
            }
        }

        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()

        if (!Utils.isDoNotDisturbPermissionGranted(this)) {
            Utils.requestDoNotDisturbPermission(this)
            return
        }

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

        updateSoundTile()
    }

    override fun onStartListening() {
        super.onStartListening()

        val filter = IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION)
        registerReceiver(broadcastReceiver, filter)

        updateSoundTile()
    }

    override fun onStopListening() {
        super.onStopListening()

        try {
            unregisterReceiver(broadcastReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onTileAdded() {
        super.onTileAdded()

        updateSoundTile()
    }

    override fun onTileRemoved() {
        super.onTileRemoved()

        if (qsTile == null) {
            return
        }

        qsTile.state = Tile.STATE_UNAVAILABLE
        qsTile.updateTile()
    }
}