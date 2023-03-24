package com.kaisha.bearpilldiary

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kaisha.bearpilldiary.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharePreferences: SharedPreferences
    private lateinit var sfEdittor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val utilsClass = Utils(this, binding)

        sharePreferences = getSharedPreferences("mySharedPreferences", MODE_PRIVATE)
        sfEdittor = sharePreferences.edit()

        binding.btnCalculate.setOnClickListener {
            utilsClass.calculate()
        }
        binding.btnReset.setOnClickListener {
            utilsClass.reset()
        }
    }

    // Data Persistence with SharedPreferences (key-value pairs - small data)
    override fun onPause() {
        super.onPause()
        val currentWeight = binding.inputWeight.text.toString().toFloat()
        val currentHeight = binding.inputHeight.text.toString().toFloat()
        val currentResultIndex = binding.resultIndex.text.toString().toFloat()
        val currentResultDescription = binding.resultDescription.text.toString()
        val currentResultInfo = binding.resultInfo.text.toString()

        sfEdittor.apply() {
            putFloat("sf_weight", currentWeight)
            putFloat("sf_height", currentHeight)
            putFloat("sf_resultIndex", currentResultIndex)
            putString("sf_resultDescription", currentResultDescription)
            putString("sf_resultInfo", currentResultInfo)
            commit()
        }
    }

    // Data Persistence with SharedPreferences (key-value pairs - small data)
    override fun onResume() {
        super.onResume()
        val currentWeight = sharePreferences.getFloat("sf_weight", 0.0F)
        val currentHeight = sharePreferences.getFloat("sf_height", 0.0F)
        val currentResultIndex = sharePreferences.getFloat("sf_resultIndex", 0.0F)
        val currentResultDescription = sharePreferences.getString("sf_resultDescription", "")
        val currentResultInfo = sharePreferences.getString("sf_resultInfo", "")

        binding.inputWeight.setText(currentWeight.toString())
        binding.inputHeight.setText(currentHeight.toString())
        binding.resultIndex.setText(currentResultIndex.toString())
        binding.resultDescription.setText(currentResultDescription)
        binding.resultInfo.setText(currentResultInfo)
    }

}
