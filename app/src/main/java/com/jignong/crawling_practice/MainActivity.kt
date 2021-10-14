package com.jignong.crawling_practice

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    lateinit var textView: TextView
    lateinit var textView2: TextView
    lateinit var textView3: TextView
    val cityarray = arrayOfNulls<String>(18)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        textView = activityMainBinding.textview
        textView2 = activityMainBinding.textview2
        textView3 = activityMainBinding.textview3

        getWeather(baseUrl, "서울", textView)
        getWeather(baseUrl, "춘천", textView2)
        getCityCovid(citycovidUrl, textView3)

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

    private fun getCovid19(Url : String , textView: TextView){
        CoroutineScope(Dispatchers.IO).launch {
            val doc = Jsoup.connect(Url).get()
            val temple = doc.select("#_cs_production_type > div > div.main_tab_area > div > div > ul > li.info_01")

            val totalcovidcase = temple.get(0).text().substring(5,12)
            val todaycovidcase = temple.get(0).text().substring(12)
            Log.d(TAG, "getCovid19: $totalcovidcase")
            Log.d(TAG, "getCovid19: $todaycovidcase")

            CoroutineScope(Dispatchers.Main).launch {
                textView.text = "총 확진자는 : $totalcovidcase\n 오늘의 확진자는 : $todaycovidcase"
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

    private fun getCityCovid(Url : String , textView: TextView){
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
                textView.text = "총 확진자 :$totalcovid\n오늘의 확진자 :${todaycovid[2]}\n인천 확진자 :${cityarray[3]}"
                Log.d(TAG, "ex:${cityarray[17]}")
                Log.d(TAG, "총 확진자: $totalcovid")
                Log.d(TAG, "오늘의 확진자: ${todaycovid[2]}")

            }
        }
    }
}
