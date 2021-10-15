package com.example.notificationcustomview

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_normal.*


class MainActivity : AppCompatActivity() {
    //there can be multiple notifications so it can be used as notification identity
    private val CHANNEL_ID = "channel_id01"
    val NOTIFICATION_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showNotificationBtn.setOnClickListener {
            showNotificationWithCustomView()
        }
    }

    private fun showNotificationWithCustomView() {
        //notification channel needs to be created to support
        // notifications on api 26 and higher
        createNotificationChannelAPI26NdHigher()
        val remoteCustomNormalView = RemoteViews(packageName, R.layout.custom_normal)

            val intent = Intent(baseContext, NotificationReceiver::class.java)               // 3
            val pendingIntent = PendingIntent.getBroadcast(baseContext, 1, intent, 0)  // 2
            remoteCustomNormalView.setOnClickPendingIntent(R.id.button12, pendingIntent)


            val remoteCustomBigView = RemoteViews(packageName, R.layout.custom_expanded)

            val mainIntent = Intent(this, MainActivity::class.java)
            mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            //triggered when user taps the notification
            val mainPIntent = PendingIntent.getActivity(
                this, 0,
                mainIntent, PendingIntent.FLAG_ONE_SHOT
            )

            val mainPIntent2 = PendingIntent.getService(
                this, 0,
                intent, 0
            )

            //send notifications
            val notificationBuilder = NotificationCompat.Builder(
                this.applicationContext, CHANNEL_ID
            )
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                //priority to manage notifications
                .setPriority(NotificationCompat.PRIORITY_MAX)
                //default collapsed view
                .setCustomContentView(remoteCustomNormalView)
//                //expanded big view
                .setCustomBigContentView(remoteCustomBigView)
                //decorated style for custom layout
//                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
//                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setContentIntent(mainPIntent)
            //get the notification manager and send the notification
//
//        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(0, notificationBuilder.build())

            NotificationManagerCompat.from(applicationContext)
                .notify(NOTIFICATION_ID, notificationBuilder.build())
        }

        private fun createNotificationChannelAPI26NdHigher() {
            //channel only required for api >=26
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val name = getString(R.string.recent_claim)
                val description = "My notification description"
                //importance of your notification
                val importance = NotificationManager.IMPORTANCE_HIGH


                val notificationChannel = NotificationChannel(
                    CHANNEL_ID,
                    name,
                    importance
                ).apply {
                    this.description = description
                }

                val notificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
    }

