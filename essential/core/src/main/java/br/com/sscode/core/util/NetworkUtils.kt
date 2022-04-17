package br.com.sscode.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telecom.ConnectionService

fun Context.isNetworkConnected(): Boolean = with(
    getSystemService(ConnectionService.CONNECTIVITY_SERVICE) as ConnectivityManager
) {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = activeNetwork ?: return false
        val capabilities = getNetworkCapabilities(network)
        capabilities?.let { capability ->
            with(capability) {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            }
        } ?: false
    } else {
        activeNetworkInfo?.let { info ->
            with(info) {
                type == ConnectivityManager.TYPE_WIFI ||
                type == ConnectivityManager.TYPE_ETHERNET ||
                type == ConnectivityManager.TYPE_MOBILE
            }
        } ?: false
    }
}