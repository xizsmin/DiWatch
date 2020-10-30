package com.example.diwatch

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

private val mTimerHandler = Handler()

public var count = 0


var mContext: Context?=null

class TimerObj: Runnable {

    override fun run() {
        val curTimeCount = DateUtils.formatElapsedTime(count.toLong())
        //clock.text = curTimeCount
        count += 1

        Intent().also {
            it.setAction(TimerService.BROADCAST_ACTION_UPDATE_COUNT)
            it.putExtra("count", count)
            mContext?.sendBroadcast(it)

        }
    }
}
private  val timerRunnable = TimerObj()

class TimerService: Service() {
    companion object {
        public val BROADCAST_ACTION_UPDATE_COUNT = "android.intent.action.BROADCAST_ACTION_UPDATE_COUNT"

    }


    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService() : TimerService = this@TimerService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("TimerService", "onStartCommand *********************************")
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show()
        mContext = this
        mTimerHandler.postDelayed(timerRunnable, 1000)
        return START_NOT_STICKY
    }

    override fun onCreate() {
//        mContext = this.applicationContext as Nothing?
        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show()
    }
    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onDestroy() {
        Log.i("TimerService", "onDestroy *********************************")
    }
}

