package com.kaisha.bearpilldiary

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.kaisha.bearpilldiary.databinding.ActivityMainBinding


class Utils(private val context: Context, private val binding: ActivityMainBinding) {
    fun calculate() {
        val weight = binding.inputWeight.text.toString()
        val height = binding.inputHeight.text.toString()

        if (validateInput(weight, height)) {
            val bmi = calculateBMI(weight, height)
            disyplayResult(bmi, binding.resultIndex, binding.resultDescription, binding.resultInfo)
            binding.btnCalculate.visibility = View.INVISIBLE
            binding.btnReset.visibility = View.VISIBLE
        }
    }

    fun reset() {
        binding.inputWeight.text.clear()
        binding.inputHeight.text.clear()
        binding.resultIndex.text = ""
        binding.resultDescription.text = ""
        binding.resultInfo.text = ""
        binding.btnCalculate.visibility = View.VISIBLE
        binding.btnReset.visibility = View.INVISIBLE
    }

    fun validateInput(weight: String?, height: String?) : Boolean {
        return when {
            weight.isNullOrEmpty() -> {
                Toast.makeText(context, "Weight is empty", Toast.LENGTH_LONG).show()
                false
            }
            height.isNullOrEmpty() -> {
                Toast.makeText(context, "Height is empty", Toast.LENGTH_LONG).show()
                false
            }
            else -> {
                true
            }
        }
    }

    fun calculateBMI(weight: String, height: String) : Float {
        val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
        // get result with two decimal places
        val bmi2Digits = String.format("%.2f", bmi).toFloat()
        return bmi2Digits
    }

    fun disyplayResult(bmi: Float, resultIndex: TextView, resultDescription: TextView, resultInfo: TextView) {
        resultIndex.text = bmi.toString()
        var resultText = ""

        when {
            bmi < 18.5 -> {
                resultText = "underweight"
            }
            bmi in 18.50..24.99->{
                resultText = "healthy :)"
            }
            bmi in 25.00..29.99->{
                resultText = "overweight"
            }
            bmi > 29.99 -> {
                resultText ="obese"
            }
        }

        resultDescription.text = resultText
        resultInfo.text = context.getString(R.string.normal_range)
    }




}