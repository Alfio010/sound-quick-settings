package com.alftendev.simplesoundquicksettings

import android.media.AudioManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alftendev.simplesoundquicksettings.Utils.isDoNotDisturbPermissionGranted
import com.alftendev.simplesoundquicksettings.Utils.requestDoNotDisturbPermission
import com.google.android.material.imageview.ShapeableImageView

class MainActivity : AppCompatActivity() {
    private fun setTextView() {
        val textView = findViewById<TextView>(R.id.tvDND)
        val imageView = findViewById<ShapeableImageView>(R.id.ivSoundMode)

        textView.text = if (isDoNotDisturbPermissionGranted(this)) {
            getString(R.string.completed)
        } else {
            getString(R.string.not_completed)
        }

        val audio = getSystemService(AUDIO_SERVICE) as AudioManager

        imageView.setImageResource(
            when (audio.ringerMode) {
                AudioManager.RINGER_MODE_NORMAL -> {
                    R.drawable.baseline_notifications_active_24
                }

                AudioManager.RINGER_MODE_VIBRATE -> {
                    R.drawable.baseline_vibration_24
                }

                AudioManager.RINGER_MODE_SILENT -> {
                    R.drawable.baseline_notifications_off_24
                }

                else -> {
                    R.drawable.baseline_vibration_24
                }

            }
        )

        imageView.setOnClickListener {
            audio.ringerMode = when (audio.ringerMode) {
                AudioManager.RINGER_MODE_NORMAL -> {
                    imageView.setImageResource(R.drawable.baseline_vibration_24)
                    AudioManager.RINGER_MODE_VIBRATE
                }

                AudioManager.RINGER_MODE_VIBRATE -> {
                    imageView.setImageResource(R.drawable.baseline_notifications_off_24)
                    audio.ringerMode = AudioManager.RINGER_MODE_NORMAL
                    AudioManager.RINGER_MODE_SILENT
                }

                AudioManager.RINGER_MODE_SILENT -> {
                    imageView.setImageResource(R.drawable.baseline_notifications_active_24)
                    AudioManager.RINGER_MODE_NORMAL
                }

                else -> {
                    return@setOnClickListener
                }
            }
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
}