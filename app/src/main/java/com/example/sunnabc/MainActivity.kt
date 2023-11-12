package com.example.sunnabc

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData("arijit")

        // after typing enqueue its auto complete (ctrl + shift + space)
        retrofitData.enqueue(object : Callback<MyData?> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val dataList = response.body()?.data?.get(0)?.title
                // loop throguth all the data and store the title into an array
                val txt = findViewById<TextView>(R.id.txt)
                for(i in 0..response.body()?.data?.size!!-1){
                    txt.text = txt.text.toString() + "\n" + response.body()?.data?.get(i)?.title.toString()
                }
                Log.d("onProcess", dataList.toString())
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("Onfailur", t.message.toString())
            }
        })
    }
}