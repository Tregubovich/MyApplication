package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = findViewById<EditText>(R.id.input)
        fun validatedText(): String? {
            val text = input.text.toString()
            if (text.isBlank()) {
                input.error = getString(R.string.error_empty)
                input.requestFocus()
                return null
            }
            input.error = null
            return text
        }

        findViewById<Button>(R.id.open_second).setOnClickListener {
            val text = validatedText() ?: return@setOnClickListener
            startActivity(Intent(this, SecondActivity::class.java).apply {
                putExtra("text", text)
            })
        }
        findViewById<Button>(R.id.dial).setOnClickListener {
            val text = validatedText() ?: return@setOnClickListener
            val phone = text.trim().replace(Regex("[() -]"), "")
            if (!Regex("\\+?[0-9]{3,15}").matches(phone)) {
                input.error = getString(R.string.error_phone)
                input.requestFocus()
                return@setOnClickListener
            }
            openExternal(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)))
        }
        findViewById<Button>(R.id.share).setOnClickListener {
            val text = validatedText() ?: return@setOnClickListener
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            openExternal(Intent.createChooser(sendIntent, getString(R.string.share_chooser)))
        }
    }

    private fun openExternal(intent: Intent) {
        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, R.string.error_no_app, Toast.LENGTH_LONG).show()
        }
    }
}
