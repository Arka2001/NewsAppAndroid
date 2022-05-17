package com.example.newsapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter : NewsItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRecyclerView : RecyclerView = findViewById(R.id.NewsList)

        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsItemAdapter(this)

        newsRecyclerView.adapter = mAdapter

    }

    private fun fetchData() {
        val url = "https://newsapi.org/v2/everything?q=tesla&from=2022-04-17&sortBy=publishedAt&apiKey=b311f0ec4fe84f3593404841f36731e6"

        val jsonObjectRequest = JsonObjectRequest(
            url,
            {
                val jsonObjectArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()

                for(i in 0 until jsonObjectArray.length()) {
                    val newsJsonObject = jsonObjectArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                    )
                    newsArray.add(news)
                }

                mAdapter.updateNews(newsArray)
            },
            {
                Toast.makeText(this, "Unexpected Error Occurred", Toast.LENGTH_SHORT).show()
            }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        Toast.makeText(this, "Clicked Item is $item", Toast.LENGTH_SHORT).show()
    }
}