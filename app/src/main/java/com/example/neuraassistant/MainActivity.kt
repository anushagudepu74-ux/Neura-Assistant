package com.example.neuraassistant

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var etMessage: EditText
    private lateinit var btnSend: TextView
    private lateinit var chatContainer: LinearLayout
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)
        chatContainer = findViewById(R.id.chatContainer)
        scrollView = findViewById(R.id.scrollView)

        btnSend.setOnClickListener {
            sendMessage()
        }

        findViewById<Button>(R.id.btnHelp).setOnClickListener {
            etMessage.setText("Help")
            sendMessage()
        }

        findViewById<Button>(R.id.btnTime).setOnClickListener {
            etMessage.setText("What time is it?")
            sendMessage()
        }

        etMessage.setOnEditorActionListener { _, _, _ ->
            sendMessage()
            true
        }
    }

    private fun sendMessage() {

        val message = etMessage.text.toString().trim()

        if (message.isEmpty()) {
            return
        }

        addUserMessage(message)

        val response = generateResponse(message)

        addBotMessage(response)

        etMessage.text.clear()

        scrollView.post {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }

    private fun generateResponse(message: String): String {

        val text = message.lowercase(Locale.getDefault())

        return when {

            text.contains("hello") ||
                    text.contains("hi") ||
                    text.contains("hey") ->
                "Hello! I'm NeuraAssistant. How can I help you?"

            text.contains("help") ->
                "I can help with greetings, time, date, calculations, and general questions."

            text.contains("time") -> {
                val time = SimpleDateFormat(
                    "hh:mm a",
                    Locale.getDefault()
                ).format(Date())

                "The current time is $time."
            }

            text.contains("date") -> {
                val date = SimpleDateFormat(
                    "dd MMMM yyyy",
                    Locale.getDefault()
                ).format(Date())

                "Today's date is $date."
            }

            text.contains("thank") ->
                "You're welcome! I'm happy to help."

            text.contains("bye") ->
                "Goodbye! Have a great day."

            else ->
                "I understand your question. I'm currently running in local assistant mode. Try asking about the time, date, help, or a greeting."
        }
    }

    private fun addUserMessage(message: String) {

        val textView = TextView(this)

        textView.text = message
        textView.textSize = 15f
        textView.setTextColor(0xFFFFFFFF.toInt())
        textView.setPadding(18, 12, 18, 12)
        textView.background = getDrawable(R.drawable.send_bg)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.gravity = Gravity.END
        params.setMargins(50, 8, 0, 8)

        chatContainer.addView(textView, params)
    }

    private fun addBotMessage(message: String) {

        val textView = TextView(this)

        textView.text = message
        textView.textSize = 15f
        textView.setTextColor(0xFF172033.toInt())
        textView.setPadding(18, 12, 18, 12)
        textView.background = getDrawable(R.drawable.input_bg)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.gravity = Gravity.START
        params.setMargins(0, 8, 50, 8)

        chatContainer.addView(textView, params)
    }
}