package com.example.pingio

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pingio.ConfigCatManager.getWebViewUrl
import kotlinx.coroutines.launch
import okhttp3.internal.connection.Exchange

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

        lifecycleScope.launch{
            if(checknetwork(this@MainActivity)){
                if(ConfigCatManager.isFeatureEnabled()) {
                    refresh.isRefreshing = true
                    try {
                        val url = getWebViewUrl()

                        webView.loadUrl(url)

                    }   catch (e: Exception) {
                        refresh.isRefreshing = false
                        Toast.makeText(
                            this@MainActivity,
                            "Failed to load URL: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }else{
                    setContentView(R.layout.maintainence)
                }

            }else{
                Toast.makeText(this@MainActivity,"Connect To Network", Toast.LENGTH_LONG).show()
            }

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