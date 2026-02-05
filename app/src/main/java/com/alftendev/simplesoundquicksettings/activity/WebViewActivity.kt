package com.alftendev.simplesoundquicksettings.activity

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)

        setContentView(webView)

        val filePath = intent.extras?.getString("filePath")

        webView.webChromeClient = WebChromeClient()
        webView.settings.setSupportZoom(true)

        if (filePath != null) {
            webView.loadUrl(filePath)
        }
    }
}