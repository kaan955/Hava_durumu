package com.titanium.kaanb.volleytry

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Visibility
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.konum_yazma.*


class Konum_yazma : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.konum_yazma)

        val prefences = getSharedPreferences("your", Context.MODE_PRIVATE)
        var url2 = ""


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

        button3.setOnClickListener {
            var editText = myEditText.text.toString().trim()
            val editor = prefences.edit()
            var url4 =
                "http://api.openweathermap.org/data/2.5/weather?q=" + editText + "&APPID=0049a287c12eda387240fdf736f5cc56&lang=tr&units=metric"



            if (isOnline() != true) {
                val dialog = Dialog(this@Konum_yazma)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.internet_popup)
                val textView19 = dialog.findViewById(R.id.textView19) as TextView

                val button4 = dialog.findViewById(R.id.button4) as Button
                button4.setOnClickListener {
                    val intent = Intent(this@Konum_yazma, Konum_yazma::class.java)
                    dialog.dismiss()
                    startActivity(intent)
                }
                dialog.show()
            } else {


                val jsonObjectRequest4 = JsonObjectRequest(
                    Request.Method.GET, url4, null,
                    Response.Listener { response ->

                        if (response?.getJSONObject("main") != null) {
                            myEditText.setHint("")
                            imageView5.visibility = View.GONE


                            editor.putString("cityname", editText)
                            editor.apply()

                            val intent = Intent(this@Konum_yazma, MainActivity::class.java)
                            startActivity(intent)


                        } else {
                            imageView5.visibility = View.VISIBLE
                            myEditText.setText("")
                            myEditText.setHint("Farklı bir konum deneyin..")


                        }


                    },
                    Response.ErrorListener {

                        imageView5.visibility = View.VISIBLE
                        myEditText.setText("")
                        myEditText.setHint("Farklı bir konum deneyin..")
                    }
                )



                MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest4)


            }


        }
    }
}