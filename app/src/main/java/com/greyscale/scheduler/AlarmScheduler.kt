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
        scheduleEnableAlarm(context)
        scheduleDisableAlarm(context)
    }
    
    private fun scheduleEnableAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, GrayscaleReceiver::class.java).apply {
            action = ACTION_ENABLE
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_ENABLE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Schedule daily at 8:00 PM (20:00)
        val calendar = getNextAlarmTime(20, 0) // 8:00 PM
        
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
    
    private fun scheduleDisableAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, GrayscaleReceiver::class.java).apply {
            action = ACTION_DISABLE
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_DISABLE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Schedule daily at 6:00 AM (06:00)
        val calendar = getNextAlarmTime(6, 0) // 6:00 AM
        
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
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
        
        val enableIntent = Intent(context, GrayscaleReceiver::class.java).apply {
            action = ACTION_ENABLE
        }
        val disableIntent = Intent(context, GrayscaleReceiver::class.java).apply {
            action = ACTION_DISABLE
        }
        
        val enablePendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_ENABLE,
            enableIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val disablePendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_DISABLE,
            disableIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        alarmManager.cancel(enablePendingIntent)
        alarmManager.cancel(disablePendingIntent)
    }
}