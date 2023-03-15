package com.example.simplesoundquicksettings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simplesoundquicksettings.Utils.requestDoNotDisturbPermissionOrSetDoNotDisturbApi23AndUp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestDoNotDisturbPermissionOrSetDoNotDisturbApi23AndUp(applicationContext)
    }
}