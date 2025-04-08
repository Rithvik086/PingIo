package com.example.pingio

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
webView = findViewById<WebView>(R.id.websiteview)

        webView.settings.javaScriptEnabled = true

        webView.webViewClient = WebViewClient()
        if(checknetwork(this)){
            webView.loadUrl("https://pingio.up.railway.app/")

        }else{
            Toast.makeText(this,"Connect To Network", Toast.LENGTH_LONG).show()
        }

    }

    private fun checknetwork(context: Context): Boolean{
        val connectManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectManager.activeNetwork?:return false
        val capabilities = connectManager.getNetworkCapabilities(network)?: return false
    return capabilities.hasCapability((NetworkCapabilities.NET_CAPABILITY_INTERNET))
    }
}