package com.example.androidb2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNumber: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var buttonShow: Button
    private lateinit var listView: ListView
    private lateinit var textViewError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNumber = findViewById(R.id.editTextNumber)
        radioGroup = findViewById(R.id.radioGroup)
        buttonShow = findViewById(R.id.buttonShow)
        listView = findViewById(R.id.listView)
        textViewError = findViewById(R.id.textViewError)

        buttonShow.setOnClickListener {
            textViewError.text = ""
            val input = editTextNumber.text.toString()

            if (input.isEmpty()) {
                textViewError.text = getString(R.string.error_empty_input)
                return@setOnClickListener
            }

            val n: Int
            try {
                n = input.toInt()
                if (n < 1) {
                    textViewError.text = getString(R.string.error_positive_input)
                    return@setOnClickListener
                }
            } catch (e: NumberFormatException) {
                textViewError.text = getString(R.string.error_invalid_data)
                return@setOnClickListener
            }

            val results = mutableListOf<String>()
            val selectedId = radioGroup.checkedRadioButtonId

            when (selectedId) {
                R.id.radioEven -> {
                    for (i in 0..n step 2) {
                        results.add(i.toString())
                    }
                }
                R.id.radioOdd -> {
                    for (i in 1..n step 2) {
                        results.add(i.toString())
                    }
                }
                R.id.radioPerfectSquare -> {
                    results.addAll(generatePerfectSquares(n))
                }
                else -> {
                    textViewError.text = getString(R.string.error_select_type)
                    return@setOnClickListener
                }
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, results)
            listView.adapter = adapter
        }
    }

    private fun generatePerfectSquares(n: Int): List<String> {
        return (0..Math.sqrt(n.toDouble()).toInt()).map { (it * it).toString() }
    }
}
