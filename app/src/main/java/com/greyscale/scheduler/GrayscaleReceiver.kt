package com.greyscale.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class GrayscaleReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        
        when (action) {
            "com.greyscale.scheduler.ENABLE" -> {
                // Check if today is Sunday-Thursday (enable days)
                val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                val shouldEnable = dayOfWeek in listOf(
                    Calendar.SUNDAY,
                    Calendar.MONDAY,
                    Calendar.TUESDAY,
                    Calendar.WEDNESDAY,
                    Calendar.THURSDAY
                )
                
                if (shouldEnable) {
                    GrayscaleController.enableGrayscale(context)
                }
            }
            
            "com.greyscale.scheduler.DISABLE" -> {
                // Check if today is Monday-Friday (disable days)
                val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                val shouldDisable = dayOfWeek in listOf(
                    Calendar.MONDAY,
                    Calendar.TUESDAY,
                    Calendar.WEDNESDAY,
                    Calendar.THURSDAY,
                    Calendar.FRIDAY
                )
                
                if (shouldDisable) {
                    GrayscaleController.disableGrayscale(context)
                }
            }
        }
    }
}