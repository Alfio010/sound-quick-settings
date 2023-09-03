package com.alftendev.simplesoundquicksettings

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alftendev.simplesoundquicksettings.Utils.isDoNotDisturbPermissionGranted
import com.alftendev.simplesoundquicksettings.Utils.requestDoNotDisturbPermission

class MainActivity : AppCompatActivity() {
    private fun setTextView() {
        val textView = findViewById<TextView>(R.id.tvDND)

        textView.text = if (isDoNotDisturbPermissionGranted(this)) {
            getString(R.string.completed)
        } else {
            getString(R.string.not_completed)
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