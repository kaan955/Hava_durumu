package com.titanium.kaanb.volleytry

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.titanium.kaanb.volleytry.R.id.*
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    var state:Int = 1
    var location:SimpleLocation? = null

        var latitude: Double? = null
        var longitude: Double? = null
    val Aylar = arrayOf<String>("Ocak","Şubat","Mart","Nisan","Mayıs","Haziran", "Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val prefences = getSharedPreferences("your", Context.MODE_PRIVATE)
        val name = prefences.getString("cityname","")
        var city_name_choose:String = ""

       if (!name.equals(""))
        {
            city_name_choose = name

            state = 2
        }else
        {
            state = 1
        }





            var date = Date()
            val formatter = SimpleDateFormat("dd MMM - HH:mm")
                val hour_format = SimpleDateFormat("HH")
            val answer: String = formatter.format(date)
        val saat: String = hour_format.format(date)

            textView12.text = "" + answer


            var url =
                "https://api.openweathermap.org/data/2.5/weather?q=Istanbul&APPID=0049a287c12eda387240fdf736f5cc56&lang=tr&units=metric"
            var url2 =
                "http://api.openweathermap.org/data/2.5/forecast?q=Istanbul&APPID=0049a287c12eda387240fdf736f5cc56&lang=tr&units=metric"


            if(state ==1) {
                location = SimpleLocation(this)


                if (!location!!.hasLocationEnabled()) {

                    SimpleLocation.openSettings(this)
                }
                latitude = location?.getLatitude()
                longitude = location?.getLongitude()
                location?.setListener(object : SimpleLocation.Listener {
                    override fun onPositionChanged() {

                        Log.e("Enlem boylamdeneme", "" + latitude + "  " + longitude)

                    }

                })
                 url =
                    "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&APPID=0049a287c12eda387240fdf736f5cc56&lang=tr&units=metric"
                 url2 =
                    "http://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&APPID=0049a287c12eda387240fdf736f5cc56&lang=tr&units=metric"
            }else if(state == 2)
            {
                 url =
                    "https://api.openweathermap.org/data/2.5/weather?q="+city_name_choose+"&APPID=0049a287c12eda387240fdf736f5cc56&lang=tr&units=metric"
                 url2 =
                    "http://api.openweathermap.org/data/2.5/forecast?q="+city_name_choose+"&APPID=0049a287c12eda387240fdf736f5cc56&lang=tr&units=metric"
            }

            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->


                    var topicOfTemp = response?.getJSONObject("main")
                    var wind = response?.getJSONObject("wind")
                    var descriptionOfHava = response?.getJSONArray("weather")


                    var weather = descriptionOfHava?.getJSONObject(0)?.getString("description")
                    var temp = topicOfTemp?.getString("temp")
                    var hisset = topicOfTemp?.getString("feels_like")
                    var nem = topicOfTemp?.getString("humidity")
                    var rüzgarSpeed = wind?.getString("speed")
                    var rüzgarSpeedTr: Double? = (rüzgarSpeed?.toDouble()?.times(3.6))
                    var city = response?.getString("name")
                    val df = DecimalFormat("#.#")
                    df.roundingMode = RoundingMode.CEILING

                    toolbar.setTitle("" + city)
                    setSupportActionBar(toolbar)

                    textView.text = "" +  temp + "°"
                    textView2.text = "Hissedilen: " + hisset + "°"
                    textView3.text = "Nem: " + nem + "%"
                    textView4.text = "Durum: " + weather
                    if (weather.equals("açık")) {
                        if(saat.toInt() > 19)
                        {
                            imageView.setBackgroundResource(R.drawable.ay)
                        }
                        else
                        { imageView.setBackgroundResource(R.drawable.acik_hava)}
                    } else if (weather.equals("bulutlu") || weather.equals("parçalı bulutlu") || weather.equals("az bulutlu") || weather.equals(
                            "az parçalı bulutlu"
                        ) || weather.equals("parçalı az bulutlu")
                    ) {
                        imageView.setBackgroundResource(R.drawable.bulutlu_hava)
                    } else if (weather.equals("hafif yağmur") || weather.equals("orta şiddetli yağmur") || weather.equals(
                            "orta şiddetli yağmur"
                        )
                    ) {
                        imageView.setBackgroundResource(R.drawable.yagisli)
                    } else if (weather.equals("kapalı")) {
                        imageView.setBackgroundResource(R.drawable.kapali_hava)
                    }else if(weather.equals("hafif kar yağışlı") || weather.equals("kar yağışlı") ){
                        imageView.setBackgroundResource(R.drawable.karli)

                    }
                    else {
                        imageView.setBackgroundResource(R.drawable.acik_hava)
                    }


                    if (rüzgarSpeedTr != null) {
                        textView5.text = "Rüzgar Hızı: " + df.format(rüzgarSpeedTr) + "km/s"
                    }

                },
                Response.ErrorListener { error ->
                    Log.e("VOLLEY", error.message)
                    // TODO: Handle error
                }
            )

            val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
            val cardView = ArrayList<CardView>()

            val recyclerView_future = findViewById(R.id.recyclerView_future) as RecyclerView
            recyclerView_future.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            val cardView_future = ArrayList<CardView_future>()


            val jsonObjectRequest2 = JsonObjectRequest(Request.Method.GET, url2, null,
                Response.Listener { response2 ->
                    var x = 0
                    var idofImage: Int
                    var i = 0
                    while (x < 6) {



                        var descriptionOfHava = response2?.getJSONArray("list")
                        var tempOfFuture = descriptionOfHava?.getJSONObject(x)?.getJSONObject("main")?.getString("temp")
                        var tempOfHourinfo = descriptionOfHava?.getJSONObject(x)?.getString("dt_txt")
                        var weather2 = descriptionOfHava?.getJSONObject(x)?.getJSONArray("weather")?.getJSONObject(0)
                            ?.getString("description")

                        var parts = tempOfHourinfo?.split(" ", ":")

                        if (weather2.equals("açık")) {
                            idofImage = R.drawable.icon_acik_hava
                        } else if (weather2.equals("bulutlu") || weather2.equals("parçalı bulutlu") || weather2.equals("az bulutlu") || weather2.equals(
                                "azparçalı bulutlu"
                            ) || weather2.equals("parçalı az bulutlu")
                        ) {
                            idofImage = R.drawable.icon_bulutlu_hava
                        } else if (weather2.equals("hafif yağmur") || weather2.equals("orta şiddetli yağmur") || weather2.equals(
                                "orta şiddetli yağmur"
                            )
                        ) {
                            idofImage = R.drawable.icon_yagisli
                        } else if (weather2.equals("kapalı")) {
                            idofImage = R.drawable.icon_kapali_hava
                        }else if(weather2.equals("hafif kar yağışlı") || weather2.equals("kar yağışlı") ){
                            idofImage = R.drawable.icon_karli

                }else {
                            idofImage = R.drawable.acik_hava
                        }



                        if(i  >= 1) {
                            cardView.add(CardView("" + parts?.get(1) + ":00", "" + tempOfFuture + " °C", idofImage))

                        }
                            val adapter = CustomAdaptor(cardView)
                        recyclerView.adapter = adapter
                        i += 1
                        x += 1


                    }


                },
                Response.ErrorListener { error ->
                        Log.e("VOLLEY", error.message)


                }
            )

            val jsonObjectRequest3 = JsonObjectRequest(Request.Method.GET, url2, null,

                Response.Listener { response3 ->

                    response3.length()
                    var x = 0
                    var yarın_day = 0
                    var i = 0
                    var idofImage: Int
                    var state = "1"

                    while (x < response3?.getJSONArray("list")!!.length()) {


                        var descriptionOfHava = response3?.getJSONArray("list")
                        var tempOfFuture = descriptionOfHava?.getJSONObject(x)?.getJSONObject("main")?.getString("temp")
                        var tempOfHourinfo = descriptionOfHava?.getJSONObject(x)?.getString("dt_txt")
                        var weather2 = descriptionOfHava?.getJSONObject(x)?.getJSONArray("weather")?.getJSONObject(0)
                            ?.getString("description")


                        if (weather2.equals("açık")) {

                            idofImage = R.drawable.icon_acik_hava
                        } else if (weather2.equals("bulutlu") || weather2.equals("parçalı bulutlu") || weather2.equals("az bulutlu") || weather2.equals(
                                "az parçalı bulutlu"
                            ) || weather2.equals("parçalı az bulutlu")
                        ) {
                            idofImage = R.drawable.icon_bulutlu_hava
                        } else if (weather2.equals("hafif yağmur") || weather2.equals("orta şiddetli yağmur") || weather2.equals(
                                "orta şiddetli yağmur"
                            )
                        ) {
                            idofImage = R.drawable.icon_yagisli
                        } else if (weather2.equals("kapalı")) {
                            idofImage = R.drawable.icon_kapali_hava
                        } else if(weather2.equals("hafif kar yağışlı") || weather2.equals("kar yağışlı") ){
                            idofImage = R.drawable.icon_karli

                }else {
                            idofImage = R.drawable.acik_hava
                        }


                        var parts = tempOfHourinfo?.split("-", " ")
                        if (i < 1) {
                            yarın_day = parts!!.get(2).toInt()
                            i += 1
                        }
                        var deneme = parts?.get(1)?.toInt()
                        var deneme2 = parts?.get(2)?.toInt()


                        if (yarın_day < deneme2!!) {
                            yarın_day = deneme2
                            cardView_future.add(
                                CardView_future(
                                    "" + yarın_day + " " + Aylar[deneme!! - 1],
                                    tempOfFuture!! + "°C",
                                    weather2!!,
                                    idofImage
                                )
                            )

                            val adapter_future = CustomAdaptor_future(cardView_future)
                            recyclerView_future.adapter = adapter_future

                        }







                        x += 1


                    }


                },
                Response.ErrorListener { error ->
                    Toast.makeText(this@MainActivity, "Deneme", Toast.LENGTH_LONG).show()
                    Log.e("VOLLEY", error.message)
                    // TODO: Handle error
                }
            )


// Access the RequestQueue through your singleton class.
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest2)
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest3)


        //}else { }
    }

    override fun onResume() {
        super.onResume()

            location?.beginUpdates()

    }


    override fun onPause() {
            location?.endUpdates()
            super.onPause()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)

        return true


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id = item.itemId

        if(id == ara)
        {
            val intent = Intent(this@MainActivity, Konum_yazma::class.java)
            startActivity(intent)
        }else if(id == toolbar_hakkımızda)
        {
            val intent = Intent(this@MainActivity, Hakkimizda::class.java)
            startActivity(intent)

        }else if(id == toolbar_cikis)
        {
            moveTaskToBack(true);
            exitProcess(-1)
        }
        return super.onOptionsItemSelected(item)
    }

    }

