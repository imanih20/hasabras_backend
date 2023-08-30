package com.mohyeddin.store_accountent.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat

fun checkInternet(context: Context) : Boolean{
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        ?: return false
    return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}

fun checkAndRequestInternetPermission(
    context: Context,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
    val permissions = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE
    )
    if ( permissions.all {
          ContextCompat.checkSelfPermission(context,it) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        // Open camera because permission is already granted
    } else {
        // Request a permission
        launcher.launch(permissions)
    }
}