package com.slash.batterychargelimit

import android.os.Build

object MotorolaSafety {

    /**
     * Detect Motorola devices using Android build properties.
     */
    fun isMotorolaDevice(): Boolean {
        return Build.MANUFACTURER.equals("motorola", ignoreCase = true) ||
                Build.BRAND.equals("motorola", ignoreCase = true)
    }
}
