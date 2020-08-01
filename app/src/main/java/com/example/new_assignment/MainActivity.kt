package com.example.new_assignment

import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.new_assignment.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {


    val manager = supportFragmentManager
    var loaded=true
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val Mybtn = findViewById<Button>(R.id.button)

        showFragmentOne()
        Mybtn.setOnClickListener({if(loaded)
            showFragmentTwo()
        else
            showFragmentOne()
        })

//
        val repeatingReq= PeriodicWorkRequest.Builder(PeriodicCalls::class.java, 15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(PeriodicCalls.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, repeatingReq)

//       applicationContext.registerReceiver(Connectivity(), IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"), null, handler)

    }
//
//    override fun onNetworkConnectionChanged(isConnected: Boolean) {
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//         suspend { Connectivity.connectivityReceiverListener = this }
//    }


    fun showFragmentOne(){
        val transaction= manager.beginTransaction()
        val fragment=Fragmentone()
        transaction.replace(R.id.fragment_holder,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        loaded=true
    }
    fun showFragmentTwo(){
        val transaction= manager.beginTransaction()
        val fragment=Fragment_two()
        transaction.replace(R.id.fragment_holder,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        loaded=false
    }
    
}
