package com.greyscale.scheduler

import android.content.Context
import android.provider.Settings

object GrayscaleController {
    
    private const val DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled"
    private const val DALTONIZER_MODE = "accessibility_display_daltonizer"
    
    // Grayscale mode value (12 = monochromacy/grayscale on most devices)
    private const val MODE_GRAYSCALE = 12
    
    fun setGrayscale(context: Context, enabled: Boolean): Boolean {
        return try {
            val cr = context.contentResolver
            if (enabled) {
                Settings.Secure.putInt(cr, DALTONIZER_ENABLED, 1)
                Settings.Secure.putInt(cr, DALTONIZER_MODE, MODE_GRAYSCALE)
            } else {
                Settings.Secure.putInt(cr, DALTONIZER_ENABLED, 0)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}