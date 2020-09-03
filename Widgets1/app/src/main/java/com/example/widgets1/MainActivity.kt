package com.example.widgets1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText.addTextChangedListener(object : TextWatcher{
            // TextWatcher의 빈코드 블록 사이를 클릭한 상태에서
            // ctrl + i 키를 누르면 TextWatcher 인터페이스의 함수 목록이 3개 나타난다.
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO는 개발자에게 코딩을 추가해야 한다는 것을 알려주는 메시지입니다.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            //Editable : 편집가능한 문자열로 EditText 의 기본 Type
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length >= 8) {
                    // toString() : 값을 String 형태로 변환
                    Log.d("EditText", "8자 이상일 때만 출력=${s.toString()}")
                }
            }
        })
    }
}