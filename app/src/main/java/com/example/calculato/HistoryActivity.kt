package com.example.calculato

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val listView = findViewById<ListView>(R.id.listViewHistory)
        val historyData = intent.getStringArrayListExtra("history") ?: arrayListOf()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, historyData)
        listView.adapter = adapter
    }
}