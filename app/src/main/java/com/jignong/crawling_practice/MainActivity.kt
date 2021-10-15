package com.jignong.crawling_practice

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.os.bundleOf
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
    private val covid19Url = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=%EC%BD%94%EB%A1%9C%EB%82%98+%ED%99%95%EC%A7%84%EC%9E%90&oquery=%EC%A7%80%EC%97%AD+%EB%82%A0%EC%94%A8&tqi=hS%2FEPdprvhGss5t4i6Nssssss6C-509210"
    private val citycovidUrl = "http://ncov.mohw.go.kr/"

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

        var city = resources.getStringArray(R.array.city)
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, city)
        activityMainBinding.weatherSpinner.adapter = adapter
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

        getCovid(citycovidUrl, totalcovid_textview)
        Log.d(TAG, "onCreate: 수정!")

    }

    private fun getWeather(Url : String, city : String, textView: TextView){
        CoroutineScope(Dispatchers.IO).launch {
            val doc = Jsoup.connect(Url + city + "날씨").get()
            val temple = doc.select(".temperature_text")
            val tem = temple.get(0).text().substring(5)

            CoroutineScope(Dispatchers.Main).launch {
                textView.text = "$city 날씨는 : $tem"
                //Log.d(TAG, "select : $temple")
                Log.d(TAG, "$city : $tem")
            }
        }
    }

    // 0 서울
    // 1 부산
    // 2 대구
    // 3 인천
    // 4 광주
    // 5 대전
    // 6 울산
    // 7 세종
    // 8 경기
    // 9 강원
    // 10 충북
    // 11 충남
    // 12 전북
    // 13 전남
    // 14 경북
    // 15 경남
    // 16 제주
    // 17 검역

    private fun getCovid(Url : String , textView: TextView){
        CoroutineScope(Dispatchers.IO).launch {
            val doc = Jsoup.connect(Url).get()
            val total = doc.select("#content > div.container > div > div.liveboard_layout > div.liveNumOuter > div.liveNum > ul > li:nth-child(1) > span.num")
            val totalcovid = total.text().substring(4)
            val today = doc.select("#content > div.container > div > div.liveboard_layout > div.liveNumOuter > div.liveNum > ul > li:nth-child(1) > span.before")
            val todaycovid = today.text().split(" ",")")
            val temple = doc.select(".rpsam_graph > #main_maplayout2 > button")
//
            for (i in 0 until 18){
                val city = temple.get(i).text().substring(2)
                cityarray.set(i, city)
            }

            CoroutineScope(Dispatchers.Main).launch {
                textView.text = "총 확진자 :$totalcovid\n오늘의 확진자 :${todaycovid[2]}"
                Log.d(TAG, "ex:${cityarray[17]}")
                Log.d(TAG, "총 확진자: $totalcovid")
                Log.d(TAG, "오늘의 확진자: ${todaycovid[2]}")

            }
        }
    }
}
