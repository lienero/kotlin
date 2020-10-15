package com.example.sqlite

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<Holder>() {

}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var binding: ActivityMainBinding

    fun setMemo(memo: Memo) {
        itemView.textNo.text =
    }
}