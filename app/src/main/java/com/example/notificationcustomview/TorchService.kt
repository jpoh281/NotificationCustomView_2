package com.example.notificationcustomview

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TorchService : Service() {         // 1

    private val torch: Torch by lazy {   // 2
        Torch(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when( intent?.action ) {         // 3
            // 앱에서 실행할 경우
            "torch" -> {
                if(Torch.state)torch.flashOff()
                else{torch.flashOn()}
            }
        }
        return super.onStartCommand(intent, flags, startId) // 4
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}