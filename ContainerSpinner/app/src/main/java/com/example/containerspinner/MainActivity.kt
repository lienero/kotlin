package com.example.containerspinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        var data = listOf("- 선택하세요 -", "1월", "2월", "3월", "4월", "5월", "6월")

        // ArrayAdapter(스피ㅈ너를 화면에 그리기 위한 컨텍스트, 스피너에 보여줄 목록 하나하나가 그려질 레이아웃, 어댑터에서 사용할 데이터)
        /* simple_list_item1 레이아웃은 텍스트뷰 1개만을 가지고 있는 특수한 레이아웃입니다. ArrayAdapter와 같은 기본 어댑터에 사용하면
           입력된 데이터에서 문자열 1개를 꺼내서 레이아웃에 그려줍니다.*/
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //position은 사용자가 스피너에서 선택을 하면 몇 번쨰 아이템인지를 알려주는 파라미터입니다.
                result.text = data.get(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
}