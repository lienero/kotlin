package com.example.sqlite

import android.app.ActivityManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite.databinding.ActivityMainBinding
import com.example.sqlite.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var helper: RoomHelper? = null
    var listData = mutableListOf<RoomMemo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val RoomMemo = listData.get(position)
        holder.setRoomMemo(RoomMemo)

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var itembinding: ItemRecyclerBinding
        var mRoomMemo : RoomMemo? = null

        init {
            itembinding = ItemRecyclerBinding.bind(itemView)
            itembinding.buttonDelete.setOnClickListener {
                //deleteRoomMemo()는 null을 허용하지 않는데 mRoomMemo는 null을 허용하도록 설정되었기 떄문에 !!를 사용해서 강제해야합니다.
                helper?.roomMemoDao()?.delete(mRoomMemo!!)
                listData.remove(mRoomMemo)
                notifyDataSetChanged()
            }
        }

        fun setRoomMemo(RoomMemo: RoomMemo) {
            itembinding = ItemRecyclerBinding.bind(itemView)
            itembinding.textNo.text = "${RoomMemo.no}"
            itembinding.textContent.text = RoomMemo.content
            // 날짜 포맷은 SimplgeDateFormat
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            itembinding.textDatetime.text = "${sdf.format(RoomMemo.datetime)}"
            // setRoomMemo 메서드로 넘어온 RoomMemo를 임시로 저장합니다.
            this.mRoomMemo = RoomMemo
        }

    }


}

