package com.titanium.kaanb.volleytry

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.view.Window
import android.widget.*
import com.titanium.kaanb.volleytry.R.id.button
import kotlinx.android.synthetic.main.gps_izin_olmayan.*
import android.content.SharedPreferences



class Gps_izin_olmayan : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gps_izin_olmayan)

        val button = findViewById(R.id.button) as Button
        button.setOnClickListener {
            val izinGps = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

            if (izinGps != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this@Gps_izin_olmayan,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    100
                )
            } else {
                Handler().postDelayed({
                    val intent = Intent(this@Gps_izin_olmayan, MainActivity::class.java)
                    startActivity(intent)
                }, 1000)

            }
        }
        val button2 = findViewById(R.id.button2) as Button
        button2.setOnClickListener {
            val prefences = getSharedPreferences("com.titanium.kaanb.volleytry.MainActivity", Context.MODE_PRIVATE)
            val editor = prefences.edit()
            var city_name_choose = prefences.getString("city_name","Istanbul")
            if(city_name_choose.equals("")) {
                Handler().postDelayed({
                    val intent = Intent(this@Gps_izin_olmayan, MainActivity::class.java)
                    startActivity(intent)
                }, 1000)
            }else {
                val intent = Intent(this@Gps_izin_olmayan, Konum_yazma::class.java)
                startActivity(intent)
            }
        }

    }
        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == 100) {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Handler().postDelayed({
                        val intent = Intent(this@Gps_izin_olmayan, MainActivity::class.java)
                        startActivity(intent)
                    }, 1000)

                }
                        Handler().postDelayed({
                            val intent = Intent(this@Gps_izin_olmayan, Gps_izin_olmayan::class.java)
                            startActivity(intent)
                        }, 1000)
                    }

                }
            }










