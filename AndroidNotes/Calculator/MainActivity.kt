package com.example.calcpractise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.app.*
import android.content.Context
import android.os.Build
import androidx.core.app.*

import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonSubtract = findViewById<Button>(R.id.buttonSubtract)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        val buttonClear = findViewById<Button>(R.id.buttonClear)

        button0.setOnClickListener { appendToInput("0") }
        button1.setOnClickListener { appendToInput("1") }
        button2.setOnClickListener { appendToInput("2") }
        button3.setOnClickListener { appendToInput("3") }
        button4.setOnClickListener { appendToInput("4") }
        button5.setOnClickListener { appendToInput("5") }
        button6.setOnClickListener { appendToInput("6") }
        button7.setOnClickListener { appendToInput("7") }
        button8.setOnClickListener { appendToInput("8") }
        button9.setOnClickListener { appendToInput("9") }

        buttonAdd.setOnClickListener { appendToInput("+") }
        buttonSubtract.setOnClickListener { appendToInput("-") }
        buttonMultiply.setOnClickListener { appendToInput("*") }
        buttonDivide.setOnClickListener { appendToInput("/") }

        // Set click listener for equal button
        buttonEquals.setOnClickListener { evaluateExpression() }

        // Set click listener for clear button
        buttonClear.setOnClickListener { clearInput() }
        buttonEquals.setOnLongClickListener {
            var inputTextView = findViewById<TextView>(R.id.inputTextView)
            val expression = inputTextView.text.toString()

            try {
                val result = evaluate(expression)
                inputTextView.text = result.toString()

                // Send notification on long press
                sendNotification(result.toString())
            } catch (e: Exception) {
                inputTextView.text = e.toString()
            }
            true
        }
    }
    private fun sendNotification(message: String) {
        createNotificationChannel()

        val builder = NotificationCompat.Builder(this, "Calculator")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Calculation Result")
            .setContentText(message)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }

    private fun createNotificationChannel() {
            val name = "Calc Channel"
            val descriptionText = "Notification Channel for Calculator"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Calculator", name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

    }
    private fun appendToInput(value: String) {
        val inputTextView = findViewById<TextView>(R.id.inputTextView)
        val currentInput = inputTextView.text.toString()
        if (currentInput == "0"){
            inputTextView.text = value
        }
        else {
            inputTextView.text = currentInput + value
        }
    }

    private fun evaluateExpression() {
        val inputTextView = findViewById<TextView>(R.id.inputTextView)
        val expression = inputTextView.text.toString()

        try {
            val result = evaluate(expression)
            inputTextView.text = result.toString()
        } catch (e: Exception) {
            inputTextView.text = e.toString()
        }
    }
    private fun clearInput() {
        val inputTextView = findViewById<TextView>(R.id.inputTextView)
        inputTextView.text = ""
    }

    private fun evaluate(expression: String): Double {
        val operatorRegex = Regex("[+\\-*/]")
        val contents = expression.split(operatorRegex)
        var result = contents[0].toDouble()
        var rightOp = contents[1].toDouble()
        var operator = operatorRegex.find(expression)?.value
        when(operator){
            "+"-> result+=rightOp
            "-"-> result-=rightOp
            "*"-> result*=rightOp
            "/"-> result/=rightOp
        }
        return result
    }

}