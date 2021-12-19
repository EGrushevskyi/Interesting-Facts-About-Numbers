package com.grushevskyi.interestingfactsaboutnumbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.grushevskyi.interestingfactsaboutnumbers.databinding.ActivityMainBinding
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.grushevskyi.interestingfactsaboutnumbers.db.Fact
import com.grushevskyi.interestingfactsaboutnumbers.db.FactRepository
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rvAdapter: RVAdapter? = null
    private lateinit var requestQueue: RequestQueue
    private val repo: FactRepository by lazy {
        FactRepository(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvList.layoutManager = LinearLayoutManager(this)
        rvAdapter = RVAdapter()
        binding.rvList.adapter = rvAdapter
    }

    override fun onResume() {
        super.onResume()
        requestQueue = Volley.newRequestQueue(this)

        binding.buttonGetFact.setOnClickListener{
            if (binding.editTextEnterNumber.text.isBlank())
                Toast.makeText(this, "Please enter number", Toast.LENGTH_SHORT).show()
            else loadText(binding.editTextEnterNumber.text.toString())
        }

        binding.buttonGetRandom.setOnClickListener {
            loadText("")
        }
    }

    private fun loadText(editText: String){
        lifecycleScope.launch(Dispatchers.Main) {
            val apiUrl: String = if (editText.isNullOrBlank()) "http://numbersapi.com/random/" else
                "http://numbersapi.com/${editText}/"

            val stringRequest = StringRequest(Request.Method.GET, apiUrl, {
                val fact = Fact(factText = it)
                fact.let { repo.insertFact(it) }
                val myIntent = Intent(this@MainActivity, SecondaryActivity::class.java)
                myIntent.putExtra("number", fact.factText.split(" ")?.get(0))
                myIntent.putExtra("fact", fact.factText)
                startActivity(myIntent)
                fetchFacts()
            }, {
                Log.d("API Request Error", "${it.printStackTrace()}")
            })
            requestQueue.add(stringRequest)
        }
    }

    private fun fetchFacts() {
        val allFacts = repo.getAllFacts().toMutableList()
        rvAdapter?.setFacts(allFacts)
    }
}