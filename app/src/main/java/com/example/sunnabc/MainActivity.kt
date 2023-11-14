package com.shanu.sunnabc

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shanu.sunnabc.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var myRecyclerView : RecyclerView
    lateinit var myAdapter: MyAdapter
    lateinit var search: EditText
    lateinit var searchBtn: ImageButton
    lateinit var searchResult: TextView
    @SuppressLint("MissingInflatedId", "ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRecyclerView = findViewById(R.id.recyclerView)
        search = findViewById(R.id.searchBar)
        searchBtn = findViewById(R.id.btnSearch)
        searchResult = findViewById(R.id.result)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        searchBtn.setOnClickListener {
            fetchData(search.text.toString(), retrofitBuilder)
            // close the keyboard
            search.clearFocus()
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

        // Initial data load with a default query
        fetchData("Arijit Singh", retrofitBuilder)
    }

    private fun fetchData(query: String, retrofitBuilder: ApiInterface) {
        val retrofitData = retrofitBuilder.getData(query)

        retrofitData.enqueue(object : Callback<MyData?> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val dataList = response.body()?.data
                myAdapter = MyAdapter(this@MainActivity, dataList!!)
                myRecyclerView.adapter = myAdapter
                myRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                searchResult.text = "Search Result for $query"
                Log.d("onProcess", dataList.toString())
                search.setText("")
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("OnFailure", t.message.toString())
                searchResult.text = "No Result Found"
                search.setText("")
            }
        })
    }

}