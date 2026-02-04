package com.alftendev.simplesoundquicksettings

import android.app.Application
import android.os.Build

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            registerActivityLifecycleCallbacks(MyActivityLifecycleCallbacks())
        }
    }
}