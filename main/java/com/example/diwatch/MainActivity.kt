package com.example.diwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.tv.TvView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.format.DateUtils
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*



public val BROADCAST_ACTION_UPDATE_COUNT = "android.intent.action.BROADCAST_ACTION_UPDATE_COUNT"

class MainActivity : AppCompatActivity() {


    var clockTextView: TextView? = null
/*
    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {

            val curTime = SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().time)
            clock.text = curTime
        }
    }
*/



    private  val timerRunnable = TimerObj()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        registerReceiver(messageReceiver, intentFilter)

        timer_button.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(buttonView.context, "ON", Toast.LENGTH_SHORT).show()
                startService(Intent(this, TimerService::class.java))


//                mTimerHandler.postDelayed(timerRunnable, 1000)
            }

            else {
                Toast.makeText(buttonView.context, "OFF", Toast.LENGTH_SHORT).show()
                //stopService()
                stopService(Intent(this, TimerService::class.java))
 //               mTimerHandler.removeCallbacks(timerRunnable)
            }
        }



        /*

        mHandler = @SuppressLint("HandlerLeak")
        object: Handler() {
            override fun handleMessage(msg: Message) {
                val cal = Calendar.getInstance()
                val sdf = SimpleDateFormat("HH:mm:ss")

                val strTime = sdf.format(cal.time)
                clockTextView = findViewById(R.id.clock)
                clockTextView?.text = strTime

            }
        }

        thread(start = true) {
            while (true) {
                Thread.sleep(1000 )
                mHandler?.sendEmptyMessage(0)
            }
        }

         */
    }

    override fun onResume() {
        super.onResume()
        //LocalBroadcastManager.getInstance(this).registerReceiver()
    }

     class MessageReceiver: BroadcastReceiver() {
         override fun onReceive(context: Context?, intent: Intent?) {
             val count = intent?.getLongExtra("count", 0)
             Toast.makeText(context, "!!!" + count.toString(), Toast.LENGTH_SHORT).show()

         }
    }
    val messageReceiver = MessageReceiver()
    val intentFilter = IntentFilter(TimerService.BROADCAST_ACTION_UPDATE_COUNT)

}