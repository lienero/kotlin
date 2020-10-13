package com.example.viewpagerfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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

        // 메뉴명으로 사용할 이름들을 배열에 저장합니다. (MainActivity의 onCreate() 함수 마지막 줄에 작성)
        val tabTitles = listOf<String>("A", "B", "C", "D")

        // TabLayoutMediator를 사용해서 TabLayout과 뷰페이저를 연결합니다.
        // 코드블럭으로 전달되는 tab 파라미터의 text속성에 앞에서 미리 정의해둔 메뉴명을 입력합니다.
        TabLayoutMediator(tablayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()


    }
}