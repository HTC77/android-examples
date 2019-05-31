package com.example.androidexamples

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient

class MyBrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_browser)
        val url = intent.data
        val webview = findViewById<View>(R.id.WebView01) as WebView
        webview.webViewClient = CallBack()
        webview.loadUrl(url.toString())
    }

    private inner class CallBack : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }
    }
}
