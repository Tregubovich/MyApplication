package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        findViewById<TextView>(R.id.received_text).text = intent.getStringExtra("text")
    }
}
