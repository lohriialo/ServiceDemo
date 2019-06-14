package com.example.servicedemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var myServiceIntent = Intent()
    private var myService : MyService? = null
    var isServiceBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myServiceBinder = service as MyService.MyServiceBinder
            myService = myServiceBinder.service
            isServiceBound = true
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logMessage("Thread Id is: " + Thread.currentThread().id.toString())
        myServiceIntent = Intent(applicationContext, MyService::class.java)

        btn_startService.setOnClickListener {
            startService(myServiceIntent)
        }
        btn_stopService.setOnClickListener {
            stopService(myServiceIntent)
        }
        btn_bindService.setOnClickListener {
            bindService(myServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
        btn_unbindService.setOnClickListener {
            unbindService(serviceConnection)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logMessage("onDestroy")
        stopService(myServiceIntent)
    }

    fun logMessage(msg: String){
        Log.i("MainActivity: ", msg)
    }
}
