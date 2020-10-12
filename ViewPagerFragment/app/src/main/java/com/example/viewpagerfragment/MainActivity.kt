package com.example.viewpagerfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentList = listOf(FragmentA(), FragmentB(), FragmentC(), FragmentD())
        // 미리 정의된 상수명이 너무 길어서 해당 값인 1을 직접 사용함
        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList
        viewPager.adapter = adapter
    }
}