package aakash.com.pagingexample.common.extension

import android.content.Context
import android.net.ConnectivityManager

fun Context?.isInternetAvailable(): Boolean {
    return try {
        val connectivityManager = this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo != null &&
                (connectivityManager.activeNetworkInfo.isConnected)
    } catch (ex: Exception) {
        ex.printStackTrace()
        false
    }
}