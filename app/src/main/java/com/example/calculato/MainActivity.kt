package com.example.calculato

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
 class MainActivity : AppCompatActivity() {
            private lateinit var tvResult: TextView
            private var firstNumber = 0.0
            private var operation = ""
            private var isNewOperation = true
            private val calculationHistory = mutableListOf<String>()

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                tvResult = findViewById(R.id.tvresult)


                setupNumberButtons()
                setupOperationButtons()
                setupNavigationButtons()
            }

            private fun setupNumberButtons() {
                val numberButtons = listOf(
                    R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                    R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
                )

                numberButtons.forEach { buttonId ->
                    findViewById<Button>(buttonId).setOnClickListener {
                        onNumberClick((it as Button).text.toString())
                    }
                }
            }

            private fun setupOperationButtons() {

                findViewById<Button>(R.id.btnPlus).setOnClickListener { onOperationClick("+") }
                findViewById<Button>(R.id.btnMinus).setOnClickListener { onOperationClick("-") }
                findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperationClick("×") }
                findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperationClick("÷") }
                findViewById<Button>(R.id.btnEquals).setOnClickListener { calculateResult() }
                findViewById<Button>(R.id.btnClear).setOnClickListener { clearCalculator() }
            }

            private fun setupNavigationButtons() {
                findViewById<Button>(R.id.btnHistory).setOnClickListener {
                    startActivity(Intent(this, HistoryActivity::class.java).apply {
                        putStringArrayListExtra("history", ArrayList(calculationHistory))
                    })
                }

                findViewById<Button>(R.id.btnSettings).setOnClickListener {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
            }

            private fun onNumberClick(number: String) {
                if (isNewOperation) {
                    tvResult.text = number
                    isNewOperation = false
                } else {
                    tvResult.append(number)
                }
            }

            private fun onOperationClick(op: String) {
                firstNumber = tvResult.text.toString().toDouble()
                operation = op
                isNewOperation = true
            }

            private fun calculateResult() {
                val secondNumber = tvResult.text.toString().toDouble()
                val result = when (operation) {
                    "+" -> firstNumber + secondNumber
                    "-" -> firstNumber - secondNumber
                    "×" -> firstNumber * secondNumber
                    "÷" -> firstNumber / secondNumber
                    else -> secondNumber
                }

                tvResult.text = result.toString()


                val calculation = "$firstNumber $operation $secondNumber = $result"
                calculationHistory.add(calculation)

                isNewOperation = true
            }

            private fun clearCalculator() {
                tvResult.text = "0"
                firstNumber = 0.0
                operation = ""
                isNewOperation = true
            }
 }

