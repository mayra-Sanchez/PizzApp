package com.example.pizzapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.util.Base64

fun decodeJWT(token: String): Map<String, Any>? {
    try {
        val split = token.split(".")
        val payload = split[1]
        val json = String(Base64.decode(payload, Base64.URL_SAFE), charset("UTF-8"))

        val type = object : TypeToken<Map<String, Any>>() {}.type
        return Gson().fromJson<Map<String, Any>>(json, type)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}


