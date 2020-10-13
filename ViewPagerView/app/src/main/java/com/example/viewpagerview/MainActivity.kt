package com.example.viewpagerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 뷰페이저에서 사용할 뷰 클래스를 모두 생성해서 views 변수에 담습니다.
        val textList = listOf("뷰 A", "뷰 B", "뷰 C", "뷰 D")
        // 커스텀 어댑터를 생성합니다.
        val adapter = CustomPagerAdapter()
        // 생성해둔 뷰 클래스 목록을 adapter에 담습니다.
        adapter.textList = textList
        // viewPager에 adapter를 연결합니다.
        viewPager.adapter = adapter

        val tabTitles = listOf("View A", "View B", "View C", "View D")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}