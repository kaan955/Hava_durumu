package com.titanium.kaanb.volleytry

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Welcome_page : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_page)
        val izinGps = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        if(isOnline() == true)
        {



        if (izinGps != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@Welcome_page, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
        }else
        {
            Handler().postDelayed({
                val intent = Intent(this@Welcome_page, MainActivity::class.java)
                startActivity(intent)
            }, 1000)

        }
        }
        else
        {
            val dialog = Dialog(this@Welcome_page)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.internet_popup)
            val textView19 = dialog.findViewById(R.id.textView19) as TextView

            val button4 = dialog.findViewById(R.id.button4) as Button
            button4.setOnClickListener {
                val intent = Intent(this@Welcome_page, Welcome_page::class.java)
                dialog.dismiss()
                startActivity(intent)


            }
            dialog.show()
            Toast.makeText(this@Welcome_page,"HATALI",Toast.LENGTH_LONG).show()

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100)
        {
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Handler().postDelayed({
                    val intent = Intent(this@Welcome_page, MainActivity::class.java)
                    startActivity(intent)
                }, 1000)

            }
            else
            {
                val prefences = getSharedPreferences("your", Context.MODE_PRIVATE)
                val name = prefences.getString("cityname","")
                if(!name.equals("")) {

                    Handler().postDelayed({
                        val intent = Intent(this@Welcome_page, MainActivity::class.java)
                        startActivity(intent)
                    }, 1000)
                }else {
                    Handler().postDelayed({
                        val intent = Intent(this@Welcome_page, Gps_izin_olmayan::class.java)
                        startActivity(intent)
                    }, 1000)
                }

            }
        }
    }

    fun isOnline(): Boolean? {
        try {
            val p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com")
            val returnVal = p1.waitFor()
            return returnVal == 0
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return false
    }

    }



