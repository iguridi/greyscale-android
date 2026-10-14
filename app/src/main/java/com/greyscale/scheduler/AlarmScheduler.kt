package com.greyscale.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

object AlarmScheduler {
    
    private const val ACTION_ENABLE = "com.greyscale.scheduler.ENABLE"
    private const val ACTION_DISABLE = "com.greyscale.scheduler.DISABLE"
    
    private const val REQUEST_CODE_ENABLE = 1001
    private const val REQUEST_CODE_DISABLE = 1002
    
    fun scheduleAllAlarms(context: Context) {
        scheduleAlarm(context, 21, 0, ACTION_ENABLE, REQUEST_CODE_ENABLE)
        scheduleAlarm(context, 5, 0, ACTION_DISABLE, REQUEST_CODE_DISABLE)
    }
    
    private fun scheduleAlarm(context: Context, hour: Int, minute: Int, action: String, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = getNextAlarmTime(hour, minute)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntentFor(context, action, requestCode)
        )
    }
    
    private fun pendingIntentFor(context: Context, action: String, requestCode: Int): PendingIntent {
        val intent = Intent(context, GrayscaleReceiver::class.java).apply {
            this.action = action
        }
        return PendingIntent.getBroadcast(
            context, requestCode, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
    
    private fun getNextAlarmTime(hour: Int, minute: Int): Calendar {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        
        // If the time has already passed today, schedule for tomorrow
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        
        return calendar
    }
    
    fun cancelAllAlarms(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntentFor(context, ACTION_ENABLE, REQUEST_CODE_ENABLE))
        alarmManager.cancel(pendingIntentFor(context, ACTION_DISABLE, REQUEST_CODE_DISABLE))
    }
}