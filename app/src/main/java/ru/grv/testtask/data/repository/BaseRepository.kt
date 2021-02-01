package ru.grv.testtask.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import ru.grv.testtask.data.Constants.INTERNAL_BACKEND_ERROR_TYPE
import ru.grv.testtask.data.Constants.NETWORK_UNAVAILABLE_ERROR_TYPE
import ru.grv.testtask.data.Constants.TOKEN_EXPIRED_TYPE
import ru.grv.testtask.data.exception.InternalBackendException
import ru.grv.testtask.data.exception.NetworkUnavailableException
import ru.grv.testtask.data.exception.TokenExpiredException

open class BaseRepository() {
    open fun definitionError(reason: String?) {
        when(reason) {
            TOKEN_EXPIRED_TYPE -> throw TokenExpiredException()
            INTERNAL_BACKEND_ERROR_TYPE -> throw InternalBackendException()
            NETWORK_UNAVAILABLE_ERROR_TYPE -> throw NetworkUnavailableException()
            else -> throw RuntimeException()
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo?
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}