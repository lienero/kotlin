package com.example.activitybasic

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent =
            Intent(this, SubActivity::class.java) // 인텐트를 생성할 떄 호출 할 클래스의 이름 뒹 콜론이 2개 들어갑니다::
        //putExtra는 인텐트에서 첫번쨰 값인 키와, 두 번째인 실제 값을 전달한다.
        intent.putExtra("from1", "hello Bundle")
        intent.putExtra("from2", 2020)
        //startActivityForResult는 첫번쨰 파라미터에는 인텐트가 두번쨰 파라미터는 메인 액티비티에서 서브 액티비티를 호출하는 버튼이
        // 여려개 있을 떄 어떤 버튼에서 호출된 것인지를 구분하는 용도입니다.
        btnStart.setOnClickListener { startActivityForResult(intent, 99) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //requestCode: 호출 시에 메인 액티비티에서 입력하는 코드 메서드에 인텐트와 함꼐 입력해서 호출한 코드(또는 버튼)을 구분합니다.
        //resultCode: 결과 처리 후 서브 액티비티에서 입력하는 코드, 앞에서 RESULT_OK를 담아서 보냈습니다.
        //data: 결과 처리 후 서브 액티비티가 넘겨주는 인텐트가 담겨있습니다.
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                99 -> {
                    val message = data?.getStringExtra("returnValue")
                    // Toast는 화면에 잠깐 나타났다 사라지는 메시지 출력 도구입니다.
                    // Toast.makeText() 첫번쨰 파라미터는 화면을 위한 기본 도구인 컨텍스트가 필요한데 액티비티가 이미 가지고 있어, this라고 입력한다
                    // 두번쨰 파라미터 : 출력될 메시지를 문자열로 전달합니다.
                    // 세번째 파라미터 : 메시지가 얼마 동안 출력될지를 결정합니다.ex) LENGTH_LONG 오랫동안 출력
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

