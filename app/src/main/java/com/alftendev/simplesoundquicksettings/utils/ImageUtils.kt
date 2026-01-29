package com.alftendev.simplesoundquicksettings.utils

import android.media.AudioManager
import com.alftendev.simplesoundquicksettings.R

object ImageUtils {
    fun getSoundStateDrawable(ringerMode: Int): Int {
        when (ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> {
                return R.drawable.baseline_notifications_active_24
            }

            AudioManager.RINGER_MODE_VIBRATE -> {
                return R.drawable.baseline_vibration_24
            }

            AudioManager.RINGER_MODE_SILENT -> {
                return R.drawable.baseline_notifications_off_24
            }

            else -> {
                return R.drawable.baseline_notifications_active_24
            }
        }
    }

    fun getDndStateDrawable(active: Boolean): Int {
        return if (active) {
            R.drawable.outline_do_not_disturb_on_24
        } else {
            R.drawable.outline_do_not_disturb_off_24
        }
    }
}