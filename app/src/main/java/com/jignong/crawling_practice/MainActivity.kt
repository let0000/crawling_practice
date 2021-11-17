package com.jignong.crawling_practice

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.jignong.crawling_practice.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity() {

    companion object {
        private var TAG = "로그"
    }

    private val baseUrl = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query="
    private val covid19Url = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=%EC%BD%94%EB%A1%9C%EB%82%98&oquery=%EC%A7%80%EC%97%AD%EB%B3%84+%EC%BD%94%EB%A1%9C%EB%82%98&tqi=hiLQwsp0YidssE%2FntPVssssssEl-368603"

    lateinit var weather_textview: TextView
    lateinit var totalcovid_textview: TextView
    lateinit var covid_textview: TextView
    val cityarray = arrayOfNulls<String>(18)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        weather_textview = activityMainBinding.weatherTextview
        totalcovid_textview = activityMainBinding.totalcoivdTextview
        covid_textview = activityMainBinding.coivdTextview

        //날씨 스피너
        var weather_city = resources.getStringArray(R.array.weather_city)
        var weather_adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, weather_city)
        activityMainBinding.weatherSpinner.adapter = weather_adapter
        activityMainBinding.weatherSpinner.setSelection(0)
        activityMainBinding.weatherSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        getWeather(baseUrl, "서울", weather_textview)
                    }
                    1 -> {
                        getWeather(baseUrl, "부산", weather_textview)
                    }
                    2 -> {
                        getWeather(baseUrl, "대구", weather_textview)
                    }
                    3 -> {
                        getWeather(baseUrl, "인천", weather_textview)
                    }
                    4 -> {
                        getWeather(baseUrl, "광주", weather_textview)
                    }
                    5 -> {
                        getWeather(baseUrl, "대전", weather_textview)
                    }
                    6 -> {
                        getWeather(baseUrl, "울산", weather_textview)
                    }
                    7 -> {
                        getWeather(baseUrl, "세종", weather_textview)
                    }
                    8 -> {
                        getWeather(baseUrl, "경기", weather_textview)
                    }
                    9 -> {
                        getWeather(baseUrl, "강원", weather_textview)
                    }
                    10 -> {
                        getWeather(baseUrl, "충북", weather_textview)
                    }
                    11 -> {
                        getWeather(baseUrl, "충남", weather_textview)
                    }
                    12 -> {
                        getWeather(baseUrl, "전북", weather_textview)
                    }
                    13 -> {
                        getWeather(baseUrl, "전남", weather_textview)
                    }
                    14 -> {
                        getWeather(baseUrl, "경북", weather_textview)
                    }
                    15 -> {
                        getWeather(baseUrl, "경남", weather_textview)
                    }
                    16 -> {
                        getWeather(baseUrl, "제주", weather_textview)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        //코로나 스피너
        var covid_city = resources.getStringArray(R.array.covid_city)
        var covid_adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, covid_city)
        activityMainBinding.covidSpinner.adapter = covid_adapter
        activityMainBinding.covidSpinner.setSelection(0)
        activityMainBinding.covidSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        getCovidlocation(covid19Url, covid_textview, 1,1)
                    }
                    1 -> {
                        getCovidlocation(covid19Url, covid_textview, 1,5)
                    }
                    2 -> {
                        getCovidlocation(covid19Url, covid_textview, 1,4)
                    }
                    3 -> {
                        getCovidlocation(covid19Url, covid_textview, 1,3)
                    }
                    4 -> {
                        getCovidlocation(covid19Url, covid_textview, 2,6)
                    }
                    5 -> {
                        getCovidlocation(covid19Url, covid_textview, 2,2)
                    }
                    6 -> {
                        getCovidlocation(covid19Url, covid_textview, 2,7)
                    }
                    7 -> {
                        getCovidlocation(covid19Url, covid_textview, 3,2)
                    }
                    8 -> {
                        getCovidlocation(covid19Url, covid_textview, 1,2)
                    }
                    9 -> {
                        getCovidlocation(covid19Url, covid_textview, 2,3)
                    }
                    10 -> {
                        getCovidlocation(covid19Url, covid_textview, 2,1)
                    }
                    11 -> {
                        getCovidlocation(covid19Url, covid_textview, 1,7)
                    }
                    12 -> {
                        getCovidlocation(covid19Url, covid_textview, 2,5)
                    }
                    13 -> {
                        getCovidlocation(covid19Url, covid_textview, 2,8)
                    }
                    14 -> {
                        getCovidlocation(covid19Url, covid_textview, 1,8)
                    }
                    15 -> {
                        getCovidlocation(covid19Url, covid_textview, 1,6)
                    }
                    16 -> {
                        getCovidlocation(covid19Url, covid_textview, 3,1)
                    }
                    17 -> {
                        getCovidlocation(covid19Url, covid_textview, 2,4)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        getCovid(covid19Url, totalcovid_textview)
    }

    private fun getWeather(Url : String, city : String, textView: TextView){
        CoroutineScope(Dispatchers.IO).launch {
            val doc = Jsoup.connect(Url + city + "날씨").get()
            val temple = doc.select(".temperature_text")
            val tem = temple.get(0).text().substring(5)
            val biko = doc.select("#main_pack > section.sc_new.cs_weather_new._cs_weather > div._tab_flicking > div.content_wrap > div.open > div:nth-child(1) > div > div.weather_info > div > div.temperature_info > p")
            val summary = biko.get(0).text().substring(0)
            val summaryarray = biko.text().split(" ")

            CoroutineScope(Dispatchers.Main).launch {
                textView.text = "기온 $tem / ${summaryarray[3]} \n " +
                        "${summaryarray[0]} ${summaryarray[1]} ${summaryarray[2]}"
                //Log.d(TAG, "select : $temple")
                Log.d(TAG, "$summary")
                Log.d(TAG, "$city : $tem")
            }
        }
    }

    private fun getCovid(Url : String , textView: TextView ){
        CoroutineScope(Dispatchers.IO).launch {
            val doc = Jsoup.connect(Url).get()
            val covid = doc.select("#_cs_production_type > div > div.main_tab_area > div > div > ul > li.info_01")
            val total = covid.text().split(" ")
            CoroutineScope(Dispatchers.Main).launch {
                textView.text = "${total[0]}\n${total[1]}\n+${total[2]}"
                Log.d(TAG, "$total")
            }
        }
    }

    //1,1 에서 3,2 까지
    //1,8
    private fun getCovidlocation(Url : String, textView: TextView, x : Int, y : Int){
        CoroutineScope(Dispatchers.IO).launch {
            val doc = Jsoup.connect(Url).get()
            val seoul = doc.select("#_cs_production_type > div > div:nth-child(4) > div > div:nth-child(3) > div:nth-child($x) > div > table > tbody > tr:nth-child($y)")
            val total = seoul.text().split(" ")
            CoroutineScope(Dispatchers.Main).launch {
                textView.text = "${total[0]}\n${total[1]}\n+${total[2]}"
                Log.d(TAG, "${total}")
            }
        }
    }
}
