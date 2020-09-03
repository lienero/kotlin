package com.example.widgetscheckbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //체크박스, 토글버튼, 스위치 위젯은 컴파운드버튼을 기반으로 만들어져 있기 때문에 모두 컴파운드를 부모로 가지고 있다.
    //그렇기에 첫번째 파라미터로 compoundButton이라는 이름이 넘어온다.
    //체크박스 리스너의 첫 번쨰 파라미터에는 상태 변화가 있는 체크박스 위젯이 그대로 전달되고,
    //두 번째 파라미터에는 라디오그룹과는 다르게 체크 여부가 Boolean 타입으로 전달됩니다.
    var listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        //리스너의 첫 번쨰 파라미터인 buttonView는 아이디가 아니라 위젯 자체이기 떄문에 buttonView.id로 체크박스의 id에 접근할 수 있습니다.
        if (isChecked) {
            when (buttonView.id) {
                R.id.checkApple -> Log.d("CheckBox", "사과가 선택되었습니다.")
                R.id.checkBanana -> Log.d("CheckBox", "바나나가 선택되었습니다.")
                R.id.checkOrange -> Log.d("CheckBox", "오렌지가 선택되었습니다.")
            }
        } else {
            when (buttonView.id) {
                R.id.checkApple -> Log.d("CheckBox", "사과가 선택 해제 되었습니다.")
                R.id.checkBanana -> Log.d("CheckBox", "바나나가 선택 해제 되었습니다.")
                R.id.checkOrange -> Log.d("CheckBox", "오렌지가 선택 해제 되었습니다.")
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setOnCheckedChangeListener 다음에 코드 블록을 사용한 것은 리스너를 위젯에 직접 구현해주는 방식이었기에
        // 이렇게 따로 변수에 담아서 위젯에 간접적으로 연결하는 형태로 사용할 수도 있습니다.
        checkApple.setOnCheckedChangeListener (listener)
        checkBanana.setOnCheckedChangeListener (listener)
        checkOrange.setOnCheckedChangeListener (listener)
        //
//        checkApple.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) Log.d("CheckBox","사과가 선택되었습니다.")
//            else Log.d("CheckBox", "사과가 선택해제되었습니다.")
//        }
    }
}