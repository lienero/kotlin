package com.example.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.sqlite.databinding.ActivityMainBinding
import com.example.sqlite.databinding.ItemRecyclerBinding

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerBinding: ActivityMainBinding
    var helper : RoomHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(recyclerBinding.root)

        helper = Room.databaseBuilder(this, RoomHelper::class.java, "room_memo").allowMainThreadQueries().build()
        val adapter = RecyclerAdapter()
        adapter.helper = helper
        adapter.listData.addAll(helper?.roomMemoDao()?.getAll() ?: mutableListOf())

        recyclerBinding.recyclerMemo.adapter = adapter
        recyclerBinding.recyclerMemo.layoutManager = LinearLayoutManager(this)

        recyclerBinding.buttonSave.setOnClickListener {
            // 플레인 텍스트를 검사해서 값이 있으면 해당 내용으로 Memo 클래스를 생성합니다.
            if (recyclerBinding.editMemo.text.toString().isNotEmpty()) {
                val memo = RoomMemo(recyclerBinding.editMemo.text.toString(), System.currentTimeMillis())
                // helper 클래스의 insertMemo() 메서드에 앞에서 생성한 Memo를 전달해 데이터베이스에 저장합니다.
                helper?.roomMemoDao()?.insert(memo)

                // 어댑터의 데이터를 모두 초기화
                adapter.listData.clear()
                // 데이터베이스에서 새로운 목록을 읽어와 어댑터에 세팅하고 갱신합니다.
                // 새로 생성되는 메모에는 번호가 자동입력되기 떄문에 번호를 갱신하기 위해서는 새로운 데이터를 세팅하는 것입니다.
                adapter.listData.addAll(helper?.roomMemoDao()?.getAll() ?: mutableListOf())
                adapter.notifyDataSetChanged()

                // 끝으로 메모 내용을 입력하는 위젯의 내용을 지워서 초기화합니다.
                recyclerBinding.editMemo.setText("")
            }
        }



    }
}