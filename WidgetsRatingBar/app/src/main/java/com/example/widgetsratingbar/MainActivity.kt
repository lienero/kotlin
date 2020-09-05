package com.example.widgetsratingbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // rating : 현재 별점
        // formUser: 사용자 입력 여부
        // 람다식 : 함수명을 작성하지 않고 중괄호를 사용해서 처리하는 것, 중괄호를 사용해서 코드를 축약한 형태
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, formUser ->
            textView.text = "$rating"
        }
    }
}