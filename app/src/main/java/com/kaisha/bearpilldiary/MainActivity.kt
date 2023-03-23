package com.kaisha.bearpilldiary

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    private lateinit var btnCalculate: Button
    private lateinit var btnReset: Button
    private lateinit var inputWeight: EditText
    private lateinit var inputHeight: EditText
    private lateinit var resultIndex: TextView
    private lateinit var resultDescription: TextView
    private lateinit var resultInfo: TextView
    private lateinit var sharePreferences: SharedPreferences
    private lateinit var sfEdittor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val utilsClass = Utils(this)
        btnCalculate = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)
        inputWeight = findViewById(R.id.inputWeight)
        inputHeight = findViewById(R.id.inputHeight)
        resultIndex = findViewById(R.id.resultIndex)
        resultDescription = findViewById(R.id.resultDescription)
        resultInfo = findViewById(R.id.resultInfo)
        sharePreferences = getSharedPreferences("mySharedPreferences", MODE_PRIVATE)
        sfEdittor = sharePreferences.edit()

        btnCalculate.setOnClickListener {
            val weight = inputWeight.text.toString()
            val height = inputHeight.text.toString()

            if (utilsClass.validateInput(weight, height)) {
                val bmi = utilsClass.calculateBMI(weight, height)
                utilsClass.disyplayResult(bmi, resultIndex, resultDescription, resultInfo)
                btnCalculate.visibility = View.INVISIBLE
                btnReset.visibility = View.VISIBLE
            }
        }

        btnReset.setOnClickListener {
            btnCalculate.visibility = View.VISIBLE
            btnReset.visibility = View.INVISIBLE
            inputWeight.text.clear()
            inputHeight.text.clear()
            resultIndex.text = ""
            resultDescription.text = ""
            resultInfo.text = ""
        }
    }

    // Data Persistence with SharedPreferences (key-value pairs - small data)
    override fun onPause() {
        super.onPause()
        val currentWeight = inputWeight.text.toString().toFloat()
        val currentHeight = inputHeight.text.toString().toFloat()
        val currentResultIndex = resultIndex.text.toString().toFloat()
        val currentResultDescription = resultDescription.text.toString()
        val currentResultInfo = resultInfo.text.toString()

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

        inputWeight.setText(currentWeight.toString())
        inputHeight.setText(currentHeight.toString())
        resultIndex.setText(currentResultIndex.toString())
        resultDescription.setText(currentResultDescription)
        resultDescription.setText(currentResultInfo)
    }
}