package com.example.containerrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*
import java.text.SimpleDateFormat

//어댑터가 정상적으로 동작하려면 미리 정의된 Holder 클래스를 제네릭으로 지정한 후 어댑터에 설계되어 잇는 3개의 인터페이스를 반드시 구현해야 한다.
class CustomAdapter : RecyclerView.Adapter<Holder>(){
    var listData = mutableListOf<Memo>()

    //LayoutInflater를 사용하면 특정 xml 파일을 개발자가 직접 클래스로 변환할 수 있다.
    //LayourInflater은 화면 요소이므로 컨텍스트가 필요하고, inflate() 메서드에 레이아웃을 지정해서 호출하면 클래스로 변환됩니다.
    //onCreateViewHolder() 아이템의 레이아웃을 생성하는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        /*inflate(resource, root, attachToRoot) :
          resource: View로 생성할 레잉아웃 파일명(id) 입니다.
          root : attachToRoot가 true 또는 false에 따라 root의 역할이 결정됩니다.
          attachToRoot : true일 경우 attach 해야 하는 대상으로 root를 지정하고 아래에 붙입니다.,
          flase일 경우 View의 최상위 레이아웃의 속성을 기본으로 레이아웃이 적용됩니다.
         */
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    //onBindViewHolder() : 스크롤이 될 때마다 실제 화면에 데이터와 레이아웃을 연결하는 메소드
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)
        holder.setMemo(memo)
    }
   
    //getItemCount() : 리사이클러뷰에서 사용할 데이터의 총 개수를 리턴하는 메소드
    override fun getItemCount(): Int {
        return listData.size
    }
}

//생성자에 1개의 값이 필수로 입력되어야 한다
//itemView를 ViewHolder의 생성자에 전달한다.
// 모든 레이아웃은 코드로 변환되는 순간 View가 된다
class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun setMemo(memo: Memo) {
        itemView.textNo.text = "${memo.no}"
        itemView.textTitle.text = memo.title

        var sdf = SimpleDateFormat("yyyy/MM/dd")
        var formattedDate = sdf.format(memo.timestamp)
        itemView.textDate.text = formattedDate
    }

}