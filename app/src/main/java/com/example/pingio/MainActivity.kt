package com.example.pingio

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var refresh: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
webView = findViewById<WebView>(R.id.websiteview)
        refresh = findViewById<SwipeRefreshLayout>(R.id.swiperefresh)

        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                refresh.isRefreshing = false
            }
        }

            if(checknetwork(this)){
                if(ConfigCatManager.isFeatureEnabled()) {
                    refresh.isRefreshing = true
                    webView.loadUrl("https://pingio.up.railway.app/")
                }else{
                    setContentView(R.layout.maintainence)
                }

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