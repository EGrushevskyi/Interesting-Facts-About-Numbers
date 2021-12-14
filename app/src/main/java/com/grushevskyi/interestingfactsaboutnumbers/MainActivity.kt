package com.grushevskyi.interestingfactsaboutnumbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.grushevskyi.interestingfactsaboutnumbers.databinding.ActivityMainBinding
import android.content.Intent
import android.util.Log
import android.widget.EditText
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rvAdapter: RVAdapter? = null
    private lateinit var requestQueue: RequestQueue
    private lateinit var factText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        initRV()

        //val view = inflater.inflate(R.layout.fragment_first, container, false)
        requestQueue = Volley.newRequestQueue(this)
        loadText(binding.editTextEnterNumber.text.toString())
        binding.buttonGetRandom.setOnClickListener {
            //loadText(binding.editTextEnterNumber)
            val myIntent = Intent(this@MainActivity, SecondaryActivity::class.java)
            myIntent.putExtra("number", binding.editTextEnterNumber.text.toString())
            myIntent.putExtra("fact", factText)
            this@MainActivity.startActivity(myIntent)
        }
        //return view
    }

    private fun initRV() {
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = rvAdapter

        binding.rvList.setOnClickListener {
            val myIntent = Intent(this@MainActivity, SecondaryActivity::class.java)

            this@MainActivity.startActivity(myIntent)
        }

    }

    private fun apiCall(editText: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            var apiUrl: String?
            if (editText.isBlank()) {
                apiUrl = "http://numbersapi.com/random/"
            } else { apiUrl = "http://numbersapi.com/${editText}/" }

            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, apiUrl, null, {
                factText = it.getString("text") // -> Will Get Our Intersting fact about random number

            }, {
                Log.d("API Request Error", "${it.printStackTrace()}")
            })
            requestQueue.add(jsonObjectRequest) // -> Added jsonObjectRequest To requestQueue
        }
    }
    private fun loadText(editText: String){
        lifecycleScope.launch(Dispatchers.Main){
            apiCall(editText)
        }
    }


}