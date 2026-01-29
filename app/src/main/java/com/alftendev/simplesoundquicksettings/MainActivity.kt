package com.alftendev.simplesoundquicksettings

import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alftendev.simplesoundquicksettings.utils.ImageUtils.getDndStateDrawable
import com.alftendev.simplesoundquicksettings.utils.ImageUtils.getSoundStateDrawable
import com.alftendev.simplesoundquicksettings.utils.Utils.isDndActive
import com.alftendev.simplesoundquicksettings.utils.Utils.isDoNotDisturbPermissionGranted
import com.alftendev.simplesoundquicksettings.utils.Utils.requestDoNotDisturbPermission
import com.alftendev.simplesoundquicksettings.utils.Utils.toggleDoNotDisturb
import com.google.android.material.imageview.ShapeableImageView

class MainActivity : AppCompatActivity() {
    companion object {
        private const val SETTINGS_MENU_ID = 1
    }

    private fun setTextView() {
        val textView = findViewById<TextView>(R.id.tvDND)
        val ivSoundMode = findViewById<ShapeableImageView>(R.id.ivSoundMode)
        val ivDnd = findViewById<ShapeableImageView>(R.id.ivDoNotDisturb)

        textView.text = if (isDoNotDisturbPermissionGranted(this)) {
            getString(R.string.completed)
        } else {
            getString(R.string.not_completed)
        }

        val audio = getSystemService(AUDIO_SERVICE) as AudioManager

        ivSoundMode.setImageResource(getSoundStateDrawable(audio.ringerMode))

        ivDnd.setImageResource(getDndStateDrawable(isDndActive(this)))

        ivDnd.setOnClickListener {
            val result = toggleDoNotDisturb(this)
            ivDnd.setImageResource(getDndStateDrawable(result))
        }

        ivSoundMode.setOnClickListener {
            audio.ringerMode = when (audio.ringerMode) {
                AudioManager.RINGER_MODE_NORMAL -> {
                    AudioManager.RINGER_MODE_VIBRATE
                }

                AudioManager.RINGER_MODE_VIBRATE -> {
                    audio.ringerMode = AudioManager.RINGER_MODE_NORMAL
                    AudioManager.RINGER_MODE_SILENT
                }

                AudioManager.RINGER_MODE_SILENT -> {
                    AudioManager.RINGER_MODE_NORMAL
                }

                else -> {
                    return@setOnClickListener
                }
            }

            ivSoundMode.setImageResource(getSoundStateDrawable(audio.ringerMode))
        }
    }

    override fun onResume() {
        super.onResume()
        setTextView()
    }

    override fun onStart() {
        super.onStart()
        setTextView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!isDoNotDisturbPermissionGranted(this)) {
            requestDoNotDisturbPermission(applicationContext)
        }

        setTextView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val settingsItem: MenuItem? = menu?.add(
            Menu.NONE,
            SETTINGS_MENU_ID,
            Menu.NONE,
            getString(R.string.settings)
        )

        settingsItem?.setIcon(R.drawable.baseline_settings_24)

        settingsItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            SETTINGS_MENU_ID -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}