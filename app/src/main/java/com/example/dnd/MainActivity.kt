package com.example.dnd

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dnd.databinding.ActivityMainBinding
import com.example.dnd.utils.SharedPrefManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var saved_status: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //showing already saved status
        sharedPrefManager = SharedPrefManager(this@MainActivity)
        saved_status = sharedPrefManager.getUserStatus()
        if (saved_status.isNotEmpty()) {
            binding.etStatus.setText(saved_status)
        }


        /** ask for permission */
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_PHONE_STATE),
                2
            )
        }

        startTheService()

        /** saving the status */
        binding.btnSave.setOnClickListener {
            var status = binding.etStatus.text.toString()
            sharedPrefManager.setUserStatus(status)
            if (binding.switch1.isChecked) {
                sharedPrefManager.setstatusActivate(true)
            }
            else{
                sharedPrefManager.setstatusActivate(false)
            }
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
        }

    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                Log.i("Service status", "Running")
                return true
            }
        }
        Log.i("Service status", "Not running")
        return false
    }

    private fun startTheService() {
        //val intent = Intent(this, DNDService::class.java)
        if (!isMyServiceRunning(DNDService::class.java)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this, DNDService::class.java))
            } else {
                startService(Intent(this, DNDService::class.java))
            }
        }
    }

}