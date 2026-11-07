package com.slash.batterychargelimit.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.slash.batterychargelimit.Constants
import com.slash.batterychargelimit.Utils
import eu.chainfire.libsuperuser.Shell
import android.util.Log

/**
 * Created by Michael on 20.04.2017.
 *
 * Triggered when the phone finished booting.
 * Checks whether power supply is attached and starts the foreground service if necessary.
 */
class BootReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "BatteryChargeLimitBootReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            Log.i(TAG, "BOOT_COMPLETED received")
            Log.i(TAG, "Charge limit enabled=${Utils.getSettings(context).getBoolean(Constants.CHARGE_LIMIT_ENABLED, false)}")
            Utils.setVoltageThreshold(null, true, context, null)
            Utils.startServiceIfLimitEnabled(context)
            Utils.suShell.addCommand("cat ${Utils.getVoltageFile()}", 0, object : Shell.OnCommandResultListener {
                override fun onCommandResult(commandCode: Int, exitCode: Int, output: List<String>) {
                    if (output.size != 0 ) {
                        Utils.getSettings(context).edit().putString(Constants.DEFAULT_VOLTAGE_LIMIT, output[0]).apply()
                    }
                }
            })
        }
    }
}
