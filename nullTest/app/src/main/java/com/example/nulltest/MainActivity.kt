package com.example.nulltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var nullable: String? // 타입 다음에 물음표를 붙여서 null 값을 입력할 수 있습니다.
        nullable = null

        var notNullable : String
        //notNullable = null //일반 변수에는 null을 입력할 수 없습니다.

        fun nullParameter(str : String?) {
            //파라미터 str에 null이 허용되었기 때문에 함수 내부에서 null 체크를 하기 전에는 str을 사용할 수 없습니다.
            //if 조건문에서 null인지 아닌지 체크해야지만 사용할 수 있습니다.
            if(str != null) {
                var length2 = str.length
            }
        }

        //리턴 타입에도 물음표를 붙여서 null 허용 여부를 설정 할 수 있다.
        fun nullReturn(): String? {
            return null
        }

        fun testSafeCall(str: String?):Int? {
            //str이 null이면 length를 체크하지 않고 null을 반환합니다.
            //변수 다음에 ?.를 사용하면 해당 변수 null일 경우?> 다음의 메소드나 프로퍼티를 호출하지 않습니다.
            var resultNull : Int? = str?.length
            return resultNull
        }

        fun testElvis(str: String): Int {
            // length 오른쪽에 ?:을 사용하면 null일 경우 ?: 오른쪽의 값이 반환됩니다.
            var resultNonNull: Int = str?.length ?: 0
            return resultNonNull
        }
    }
}