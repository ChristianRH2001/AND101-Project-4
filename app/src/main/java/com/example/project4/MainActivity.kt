package com.example.project4

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var baseAmount: EditText
    private lateinit var tipPercentLabel: TextView
    private lateinit var tipAmount: TextView
    private lateinit var totalAmount: TextView
    private lateinit var seekBar: SeekBar

    private var tipPercent = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseAmount = findViewById(R.id.baseAmount)
        tipPercentLabel = findViewById(R.id.tipPercentLabel)
        tipAmount = findViewById(R.id.tipAmount)
        totalAmount = findViewById(R.id.totalAmount)
        seekBar = findViewById(R.id.tipBar)

        // Update tip percentage label and recalculate when SeekBar changes
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tipPercent = progress
                tipPercentLabel.text = "$tipPercent%"
                calculateTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Recalculate whenever user changes the bill amount
        baseAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calculateTipAndTotal()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun calculateTipAndTotal() {
        val baseText = baseAmount.text.toString()
        if (baseText.isEmpty()) {
            tipAmount.text = "$0.00"
            totalAmount.text = "$0.00"
            return
        }

        val base = baseText.toDoubleOrNull() ?: 0.0
        val tip = base * tipPercent / 100
        val total = base + tip

        tipAmount.text = String.format("$%.2f", tip)
        totalAmount.text = String.format("$%.2f", total)
    }
}