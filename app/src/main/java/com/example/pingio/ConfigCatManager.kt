package com.example.pingio

import com.configcat.ConfigCatClient
import com.configcat.LogLevel
import kotlinx.coroutines.suspendCancellableCoroutine

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object ConfigCatManager {
    val client: ConfigCatClient = ConfigCatClient.get("configcat-sdk-1/RnfdCFYWXEmCytrDdXlNaQ/vMLJQUi4s0a-6oQMgjcS-Q") {

    }

    fun isFeatureEnabled(): Boolean {
        return client.getValue(Boolean::class.java, "isMyFirstFeatureEnabled", false)
    }


    suspend fun getWebViewUrl(): String = suspendCancellableCoroutine { cont ->
        val future = client.getValueAsync(String::class.java, "webview_url", "https://pingio.onrender.com/")
        future.whenComplete { result, error ->
            if (error != null) {
                cont.resumeWithException(error)
            } else {
                cont.resume(result)
            }
        }
    }
}
