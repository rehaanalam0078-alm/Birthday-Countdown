package com.example.waittillbday

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var countdownText: TextView
    private var notified = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countdownText = findViewById(R.id.countdownText)

        createNotificationChannel()

        val targetDate = SimpleDateFormat(
            "dd/MM/yyyy HH:mm:ss",
            Locale.getDefault()
        ).parse("15/08/2026 00:00:00")!!.time

        val remaining = targetDate - System.currentTimeMillis()

        object : CountDownTimer(remaining, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                val sec = millisUntilFinished / 1000 % 60
                val min = millisUntilFinished / (1000 * 60) % 60
                val hr = millisUntilFinished / (1000 * 60 * 60) % 24
                val day = millisUntilFinished / (1000 * 60 * 60 * 24)

                countdownText.text = "$day d $hr h $min m $sec s"

                if (millisUntilFinished <= 10000 && !notified) {
                    sendNotification()
                    notified = true
                }
            }

            override fun onFinish() {
                startActivity(Intent(this@MainActivity, BirthdayActivity::class.java))
                finish()
            }
        }.start()
    }

    private fun sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        val builder = NotificationCompat.Builder(this, "birthday_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("🎉 Almost Time!")
            .setContentText("Only 10 seconds left...")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "birthday_channel",
                "Birthday Countdown",
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}