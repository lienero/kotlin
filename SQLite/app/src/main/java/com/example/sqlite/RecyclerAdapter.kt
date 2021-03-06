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
    var helper: SqliteHelper? = null
    var listData = mutableListOf<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)
        holder.setMemo(memo)

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var itembinding: ItemRecyclerBinding
        var mMemo : Memo? = null

        init {
            itembinding = ItemRecyclerBinding.bind(itemView)
            itembinding.buttonDelete.setOnClickListener {
                //deleteMemo()는 null을 허용하지 않는데 mMemo는 null을 허용하도록 설정되었기 떄문에 !!를 사용해서 강제해야합니다.
                helper?.deleteMemo(mMemo!!)
                listData.remove(mMemo)
                notifyDataSetChanged()
            }
        }

        fun setMemo(memo: Memo) {
            itembinding = ItemRecyclerBinding.bind(itemView)
            itembinding.textNo.text = "${memo.no}"
            itembinding.textContent.text = memo.content
            // 날짜 포맷은 SimplgeDateFormat
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            itembinding.textDatetime.text = "${sdf.format(memo.datetime)}"
            // setMemo 메서드로 넘어온 Memo를 임시로 저장합니다.
            this.mMemo = memo
        }

    }


}

