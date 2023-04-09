package com.example.simplesoundquicksettings

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alftendev.simplesoundquicksettings.R
import com.example.simplesoundquicksettings.Utils.isDoNotDisturbPermissionGranted
import com.example.simplesoundquicksettings.Utils.requestDoNotDisturbPermission

class MainActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()

        val textView = findViewById<TextView>(R.id.tvDND)

        textView.text = if (isDoNotDisturbPermissionGranted(this)) {
            getString(R.string.completed)
        } else {
            getString(R.string.not_completed)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!isDoNotDisturbPermissionGranted(this)) {
            requestDoNotDisturbPermission(applicationContext)
        }

        val textView = findViewById<TextView>(R.id.tvDND)

        textView.text = if (isDoNotDisturbPermissionGranted(this)) {
            getString(R.string.completed)
        } else {
            getString(R.string.not_completed)
        }
    }
}