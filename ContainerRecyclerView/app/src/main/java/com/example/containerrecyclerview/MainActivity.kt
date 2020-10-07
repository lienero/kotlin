package com.example.containerrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data: MutableList<Memo> = loadData()
        //어대버를 생성하고 어댑터의 listData 변수에 위에서 생성한 데이터 목록을 저장합니다.
        var adapter = CustomAdapter()
        adapter.listData = data
        //recyclerView 위젯의 adapter 속성에 생성할 어댑터를 연결합니다.
        recyclerView.adapter = adapter
        // 리사이클러뷰를 화면에 보여주는 형태를 결정하는 레이아웃 매니저를 연결합니다.
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

        fun loadData(): MutableList<Memo> {
            val data:MutableList<Memo> = mutableListOf()
            for (no in 0..100) {
                val title = "이것이 코틀린 안드로이드다 ${no + 1}"
                //System.currentTimeMillis : 시스템의 현재 시간을 밀리초로 환산하여 Long값으로 환산한다.
                val date = System.currentTimeMillis()
                var memo = Memo(no, title, date)
                data.add(memo)
            }
            return data
        }


}