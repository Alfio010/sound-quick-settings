package com.alftendev.simplesoundquicksettings

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            setPadding(16.dpToPx(), 16.dpToPx(), 16.dpToPx(), 16.dpToPx())
        }

        val licenseOption = createSettingsItem("App license") {
            startActivity(
                Intent(
                    this,
                    WebViewActivity()::class.java
                ).setAction(Intent.ACTION_MAIN)
                    .putExtra("filePath", "file:///android_asset/gnu_license.html")
            )
        }
        rootLayout.addView(licenseOption)

        rootLayout.addView(createSeparator())

        val otherActivityOption = createSettingsItem("Open source licenses") {

            startActivity(
                Intent(
                    this,
                    WebViewActivity()::class.java
                ).setAction(Intent.ACTION_MAIN)
                    .putExtra("filePath", "file:///android_asset/open_source_licenses.html")
            )
        }
        rootLayout.addView(otherActivityOption)

        rootLayout.addView(createSeparator())

        val iconLicenseActivityOption = createSettingsItem("Icon licenses") {

            startActivity(
                Intent(
                    this,
                    WebViewActivity()::class.java
                ).setAction(Intent.ACTION_MAIN)
                    .putExtra("filePath", "file:///android_asset/material_icon_license.html")
            )
        }
        rootLayout.addView(iconLicenseActivityOption)

        setContentView(rootLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createSettingsItem(text: String, onClickAction: () -> Unit): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = 18f
            setPadding(8.dpToPx(), 16.dpToPx(), 8.dpToPx(), 16.dpToPx())
            isClickable = true
            isFocusable = true

            val outValue = TypedValue()
            context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
            setBackgroundResource(outValue.resourceId)

            setOnClickListener { onClickAction() }
        }
    }

    private fun createSeparator(): View {
        return View(this).apply {
            layoutParams =
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1.dpToPx())
                    .apply {
                        setMargins(0, 8.dpToPx(), 0, 8.dpToPx())
                    }
            setBackgroundColor(Color.LTGRAY)
        }
    }

    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}