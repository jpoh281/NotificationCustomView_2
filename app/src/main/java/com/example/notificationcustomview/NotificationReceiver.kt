package com.example.notificationcustomview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("asd","asdasdasdas")
        Log.d("asd","asdasdasdas")
        val notificationManager = NotificationManagerCompat.from(context!!)
        var torch : Torch =  Torch(context)
        if(Torch.state) torch.flashOff()
        else torch.flashOn()

//        notificationManager.cancel(1)
    }

}

