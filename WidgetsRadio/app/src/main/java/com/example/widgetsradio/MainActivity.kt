package com.example.widgetsradio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 첫번쨰 파라미터에는 라디오그룹 위젯이,
        // 두 번쨰 파라미터에는 라디오그룹 안에서 클릭된 라디오버튼의 id가 전달됩니다.
        // -> : checkedId 에 값이 있을 경우에 뒤의 코드가 실행됨
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                // 레이아웃 파일에 id를 입력하면 모든 아이디에 R.id이라는 일종의 접두어가 붙어서 코드로 생성된다.
                R.id.radioApple -> Log.d("RadioButton", "사과가 선택되었습니다.")
                R.id.radioBanana -> Log.d("RadioButton", "바나나가 선택되었습니다.")
                R.id.radioOrange -> Log.d("RadioButton", "오렌지가 선택되었습니다.")
            }
        }
    }
}