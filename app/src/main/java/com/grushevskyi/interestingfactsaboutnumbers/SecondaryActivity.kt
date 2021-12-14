package com.grushevskyi.interestingfactsaboutnumbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grushevskyi.interestingfactsaboutnumbers.databinding.ActivitySecondaryBinding


class SecondaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewChosenNumber.text = intent.getStringExtra("number")
        binding.textViewFact.text = intent.getStringExtra("fact")

        binding.buttonGoBack.setOnClickListener {
            finish()
        }
    }
}