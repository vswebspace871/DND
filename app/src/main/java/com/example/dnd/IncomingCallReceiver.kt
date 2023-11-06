package com.example.dnd

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.dnd.utils.SharedPrefManager

class IncomingCallReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {

        val state = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (state != null) {
            Log.e("TAG", "STATE PHONE = "+state)
        }
        if (!TextUtils.isEmpty(state) && TextUtils.equals(state, "RINGING")) {
            Log.d(TAG, "onReceive: ${SharedPrefManager(context!!).getStatusActivate()}")
            if (SharedPrefManager(context).getStatusActivate()) {
                val msg = context?.let { SharedPrefManager(it).getUserStatus() }
                if (msg != null) {
                    if (msg.isNotEmpty()) {
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
        //val incomingCallerNum = intent?.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
        if (state == "IDLE") {
            Log.e("TAG", "IDLE"+state)

        }
        if (state == "OFFHOOK") {
            Log.e("TAG", "OFFHOOK Calling")
        }
    }
}
