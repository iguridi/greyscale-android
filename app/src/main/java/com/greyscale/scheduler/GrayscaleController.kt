package com.greyscale.scheduler

import android.content.Context
import android.provider.Settings

object GrayscaleController {
    
    private const val DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled"
    private const val DALTONIZER_MODE = "accessibility_display_daltonizer"
    
    // Grayscale mode value (0 = monochromacy/grayscale)
    private const val MODE_GRAYSCALE = 0
    
    fun enableGrayscale(context: Context): Boolean {
        return try {
            val contentResolver = context.contentResolver
            Settings.Secure.putInt(contentResolver, DALTONIZER_ENABLED, 1)
            Settings.Secure.putInt(contentResolver, DALTONIZER_MODE, MODE_GRAYSCALE)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    fun disableGrayscale(context: Context): Boolean {
        return try {
            val contentResolver = context.contentResolver
            Settings.Secure.putInt(contentResolver, DALTONIZER_ENABLED, 0)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    fun isGrayscaleEnabled(context: Context): Boolean {
        return try {
            val contentResolver = context.contentResolver
            Settings.Secure.getInt(contentResolver, DALTONIZER_ENABLED, 0) == 1
        } catch (e: Exception) {
            false
        }
    }
}