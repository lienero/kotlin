package com.example.viewpagerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.item_viewpager.view.*

class CustomPagerAdapter : RecyclerView.Adapter<Holder>() {
    // ctrl + i(implement) : 메서드명만 있는 인터페이스가 설게되어 있습니다. 메서드 내부에 코드를 작성해두면
    // 부모 클래스에 작성되어 있는 코드에서 우리가 작성한 인터페이스 메서드를 호출해서 사용합니다. 인터페이스는 구현하지 않으면 컴파일 되지 않습니다.
    // ctrl + o(override): 부모 클래스에 이미 만들어져 있는 메서드를 내 코드에 맞게 재정의하는 것입니다.
    // 구현하지 않아도 컴파일되며, 부모 클래스에 있는 메서드가 호출되고 실행됩니다
    var textList = listOf<String>()
    //onCreateViewHolder()에서 item_viewpager를 inflate한 후 Holder에 담아서 안드로이드에 전달합니다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager, parent, false)
        return Holder(view)
    }

    //Holder 에 만들어둔 setTexxt 함수를 호출해서 화면에 표시합니다.
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val text = textList[position]
        holder.setText(text)

    }
    // getItemCount 한수는 몇 개의 페이지가 보여질 건지 결정합니다.
    override fun getItemCount(): Int {
        return textList.size

    }
}
// Hodler 클래스의 itemView 파라미터로 우리가 앞에서 미리 만들어둔 item_viewpager레이아웃이 전달됩니다.
class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // item_viewpager레이아웃 안에 미리 만들어둔 텍스트뷰에 값을 입력하는 코드를 작성합니다.
    fun setText(text:String) {
        itemView.textView.text = text
    }
}