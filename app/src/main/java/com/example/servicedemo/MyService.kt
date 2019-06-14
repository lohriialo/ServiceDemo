package com.example.servicedemo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    internal inner class MyServiceBinder : Binder() {
        val service: MyService
            get() = this@MyService
    }

    private val mBinder = MyServiceBinder()

    override fun onBind(intent: Intent?): IBinder? {
        logMessage("onBind")
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        logMessage("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent == null) return super.onStartCommand(null, flags, startId)

        logMessage("Thread Id is: " + Thread.currentThread().id.toString())
        logMessage("onStartCommand")

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        logMessage("onDestroy")
    }

    fun logMessage(msg: String){
        Log.i("MyService: ", msg)
    }
}