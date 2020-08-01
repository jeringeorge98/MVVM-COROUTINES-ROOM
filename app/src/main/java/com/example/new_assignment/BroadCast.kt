//package com.example.new_assignment
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.net.ConnectivityManager
//import android.net.Network
//import android.os.Build
//import androidx.annotation.RequiresApi
//
//class Connectivity: BroadcastReceiver() {
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun onReceive(context: Context?, intent: Intent?) {
//        if(connectivityReceiverListener != null){
//            connectivityReceiverListener!!.onNetworkConnectionChanged(isConnecting(context!!))
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    fun isConnecting(context: Context): Boolean{
//        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo = connMgr.activeNetwork ?: return false
//        return true
//    }
//
//    interface ConnectivityReceiverListener {
//        fun onNetworkConnectionChanged(isConnected: Boolean)
//    }
//
//    companion object{
//        var connectivityReceiverListener: ConnectivityReceiverListener? = null
//    }
//}