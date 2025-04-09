package com.example.pingio

import com.configcat.ConfigCatClient
import com.configcat.LogLevel

object ConfigCatManager {
    val client: ConfigCatClient = ConfigCatClient.get("configcat-sdk-1/RnfdCFYWXEmCytrDdXlNaQ/vMLJQUi4s0a-6oQMgjcS-Q") {

    }

    fun isFeatureEnabled(): Boolean {
        return client.getValue(Boolean::class.java, "isMyFirstFeatureEnabled", false)
    }
}
