package com.example.collectionmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 맵은 키와 값의 쌍으로 입력되는 컬렉션이다.
        //1. 맵 생성하기
        var map = mutableMapOf<String, String>()
        //2. 값 넣기;
        map.put("키1", "값2")
        map.put("키2", "값2")
        map.put("키3", "값3")
        //3. 값 사용하기
        var variable = map.get("키2")
        Log.d("Collection", "키2의 값은 ${variable}입니다.")
        //4. 값 삭제하기
        map.remove("키2")
        //5.1 없는 값은 불러오면 null 값이 출력된다.
        Log.d("Collection", "키2의 값은 ${map.get("키2")}입니다.")

        // 이뮤터블 컬렉션은 일반 배열처럼 크기를 변경할 수 없으면서 값 또한 변경할 수 없다.
        var IMMUTABLE_LIST = listOf("JAN","FEB","MAR") //이뮤테블 컬렉션 생성
        Log.d("Collection", "리스트의 두 번째 값은 ${IMMUTABLE_LIST.get(1)} 입니다.") //사용

    }
}