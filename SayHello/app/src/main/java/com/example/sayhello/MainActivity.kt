package com.example.sayhello

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
// XML에 있는 버튼 ID를 직접 접근하기 위해서 import되어야 합니다.

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSay

        btnSay.setOnClickListener {
            textSay.setText("Hello kotlin")

        }

    }
}