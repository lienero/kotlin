package com.example.activitybasic

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sub.*

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        //getStrngExtra() : 문자열을 꺼내는 메소드
        //intent는 액티비티의 기본 속성이기 떄문에 전달된 인텐트는 intent로 바로 호출해서 사용할 수 있습니다.
        to1.text = intent.getStringExtra("from1")
        //getIntExtra()의 첫번째 값은 putExtra에서 지정한 키, 2번쨰는 디폴트값
        //텍스트뷰의 text 속성은 문자열만 받을 수 있기에 쌍따옴표로 감싼 뒤 문자열 템플릿(${})을 사용해서 문자열로 변환해줍니다.
        to2.text = "${intent.getIntExtra("from2",0)}"

        btnClose.setOnClickListener {
            val returnIntent = Intent()
            //returnIntent에 editMessage의 값을 담는 코드
            returnIntent.putExtra("returnValue", editMessage.text.toString())
            //setResult() 실행하면 호출한 측으로 인텐트가 전달됩니다.(RESULT_OK는 성공)
            //setResult() 메서드의 첫번쨰 파라미터가 상태값, 두 번쨰가 전달하려는 인텐트입니다.
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}