package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.example.timer.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    // 전체 시간을 저장하는 total
    var total = 0
    // started는 시작되지 않았으므로 'false'를 입력합니다.
    var started = false

    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val minute = String.format("%02d", total/60)
            val second = String.format("%02d", total%60)
            binding.textTimer.text = "$minute:$second"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStart.setOnClickListener {
            started = true
            thread(start=true) {
                while (started) {
                    Thread.sleep(1000)
                    if (started) {
                        total = total + 1
                        // sendEmptyMessage() : 간단하게 what 값을 통해서 메시지를 보낼 때 사용
                        handler?.sendEmptyMessage(0)
                    }
                }
            }
        }

        binding.buttonStop.setOnClickListener {
            if (started) {
                started = false
                total = 0
                binding.textTimer.text = "00:00"
            }
        }


    }
}