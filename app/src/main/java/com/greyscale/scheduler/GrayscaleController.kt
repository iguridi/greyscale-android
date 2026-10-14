package com.greyscale.scheduler

import android.content.Context
import android.provider.Settings

object GrayscaleController {
    
    private const val DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled"
    private const val DALTONIZER_MODE = "accessibility_display_daltonizer"
    
    // Android's monochromacy (grayscale) daltonizer mode.
    private const val MODE_GRAYSCALE = 0
    
    fun setGrayscale(context: Context, enabled: Boolean): Boolean {
        return try {
            val cr = context.contentResolver
            if (enabled) {
                Settings.Secure.putInt(cr, DALTONIZER_MODE, MODE_GRAYSCALE)
                Settings.Secure.putInt(cr, DALTONIZER_ENABLED, 1)
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