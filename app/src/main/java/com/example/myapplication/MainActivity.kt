package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.web.WebContentService
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://quotes.rest/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()


        val service: WebContentService = retrofit.create(WebContentService::class.java)

        service.getQuote().enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                response.body()?.let {
                    val json = it.string()
                    val query = ".contents.quotes[0].quote"
                    val path_author = ".contents.quotes[0].author"
                    val config: Configuration = Configuration.builder().build()

                    val ip = JsonPath.parse(json, config).read(query, List::class.java)
                    val author = JsonPath.parse(json, config).read(path_author, List::class.java)
                    val message: String
                    val auth: String

                    if (ip == null || author == null) {
                        message = "What a lovely day today, Im so happy I am I am"
                        auth = "Conan 'O Brian"
                    } else {
                        message = ip[0].toString()
                        auth = author[0].toString()
                    }

                    Toast.makeText(applicationContext, "${message} - ${auth}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(applicationContext, "What a lovely day today, Im so happy I am I am", Toast.LENGTH_SHORT).show()
            }
        })

    }
}