package com.example.networkdemo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.networkdemo.api.RetrofitClient
import com.example.networkdemo.model.Todo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private lateinit var textViewResult: TextView
    private lateinit var fetchDataButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewResult = findViewById(R.id.textViewResult)
        fetchDataButton = findViewById(R.id.buttonFetchData)

        fetchDataButton.setOnClickListener {
            if (isInternetAvailable()) {
                fetchData()
            } else {
                Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchData() {
        val call = RetrofitClient.apiService.getTodo()

        call.enqueue(object : Callback<Todo> {
            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                try {
                    if (response.isSuccessful) {
                        val todo = response.body()
                        textViewResult.text = "ID: ${todo?.id}\nTitle: ${todo?.title}\nCompleted: ${todo?.completed}"
                    } else {
                        throw Exception("Server error: ${response.code()}") // Throws exception if response is unsuccessful
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Failed to fetch data! ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Todo>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network request failed!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}