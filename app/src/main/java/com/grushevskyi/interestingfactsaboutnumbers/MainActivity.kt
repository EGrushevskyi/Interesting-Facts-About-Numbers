package com.grushevskyi.interestingfactsaboutnumbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.grushevskyi.interestingfactsaboutnumbers.databinding.ActivityMainBinding
import android.content.Intent
import android.util.Log
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
    private var factText: String = ""
    private var enterNumber: String = ""
    private val list  = MutableList<String>(0){""}
    val repo: FactRepository by lazy {
        FactRepository(this)
    }
    var fact: Fact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvList.layoutManager = LinearLayoutManager(this)
        rvAdapter = RVAdapter(ArrayList(list))
        binding.rvList.adapter = rvAdapter

        binding.editTextEnterNumber.showSoftInputOnFocus = false
    }

    override fun onResume() {
        super.onResume()

        requestQueue = Volley.newRequestQueue(this)
        val myIntent = Intent(this@MainActivity, SecondaryActivity::class.java)

        binding.editTextEnterNumber.doOnTextChanged { text, start, count, after ->
            enterNumber = text.toString()
        }

        binding.buttonGetFact.setOnClickListener{
            loadText(enterNumber)
            list.add(factText)
            fact?.let {
                val fact = Fact(
                    factId = it.factId,
                    factText = factText
                )
                repo.insertFact(fact)
            }
            myIntent.putExtra("number", factText.split(" ").get(0))
            myIntent.putExtra("fact", factText)
            startActivity(myIntent)
        }

        binding.buttonGetRandom.setOnClickListener {
            loadText("")
            list.add(factText)
            fact?.let {
                val fact = Fact(
                    factId = it.factId,
                    factText = factText
                )
                repo.insertFact(fact)
            }
            myIntent.putExtra("number", factText.split(" ").get(0))
            myIntent.putExtra("fact", factText)
            startActivity(myIntent)
        }
    }

    private fun apiCall(editText: String) {
        lifecycleScope.launch(Dispatchers.IO) {

            val apiUrl: String = if (editText.isNullOrBlank()) {
                "http://numbersapi.com/random/"
            } else {
                "http://numbersapi.com/${editText}/"
            }

            val stringRequest = StringRequest(Request.Method.GET, apiUrl, {
                factText = it
            }, {
                Log.d("API Request Error", "${it.printStackTrace()}")
            })

            requestQueue.add(stringRequest)
        }
    }
    private fun loadText(editText: String){
        lifecycleScope.launch(Dispatchers.Main){
            apiCall(editText)
        }
    }

    fun fetchFacts() {
        val allFacts = repo.getAllFacts()
        rvAdapter?.setFacts(allFacts)
    }
}