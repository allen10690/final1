package com.example.final1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

class GpsActivity : AppCompatActivity() {
    lateinit var thisView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)

        thisView = window.decorView
        val button : Button = findViewById(R.id.where_button)
        button.setOnClickListener {
            permission()
        }
    }

    fun permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 0)
        } else startIntent()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startIntent()
            } else {
                val snackBar = Snackbar.make(thisView, "無定位功能無法執行程序", Snackbar.LENGTH_INDEFINITE)
                snackBar.setAction("OK", object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        snackBar.setText("aaaaaaaa")
//                        snackBar.dismiss()
                    }
                })
                        .setActionTextColor(Color.LTGRAY)
                        .show()
            }
        }
    }

    fun startIntent() {
        val intent = Intent(this, Gps2Activity::class.java)
        startActivity(intent)
    }
}
