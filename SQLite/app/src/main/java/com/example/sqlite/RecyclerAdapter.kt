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

class RecyclerAdapter : RecyclerView.Adapter<Holder>() {
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


}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var itembinding: ItemRecyclerBinding

    fun setMemo(memo: Memo) {
        itembinding = ItemRecyclerBinding.bind(itemView)
        itembinding.textNo.text = "${memo.no}"
        itembinding.textContent.text = memo.content
        // 날짜 포맷은 SimplgeDateFormat
        val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
        itembinding.textDatetime.text = "${sdf.format(memo.datetime)}"
    }
}