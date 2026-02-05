package com.alftendev.simplesoundquicksettings.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings

class ProxySoundSettingsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(Settings.ACTION_SOUND_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        finish()
    }
}
