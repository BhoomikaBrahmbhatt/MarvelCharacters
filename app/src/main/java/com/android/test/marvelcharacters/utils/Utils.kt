package com.android.test.marvelcharacters.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast

object Utils {

    //https://gateway.marvel.com:443/v1/public/characters?apikey=5bd1eb18d414cff0fbe03da663d3fd2c&ts=1&hash=2b58aaee8278f900219dfb64d0eb6dc5
    const val value_ts=1
    const val value_hash="2b58aaee8278f900219dfb64d0eb6dc5"
    const val value_apikey="5bd1eb18d414cff0fbe03da663d3fd2c"
    var limitCharacters: Int=0

    infix fun <T> Boolean.then(value: T?) = TernaryExpression(this, value)

    class TernaryExpression<out T>(val flag: Boolean, val truly: T?) {
        infix fun <T> or(falsy: T?) = if (flag) truly else falsy
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }

    fun showLog(tag : String, message:String){
        Log.d(tag,message)
    }
    fun showToast(context: Context,message:String){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }
}