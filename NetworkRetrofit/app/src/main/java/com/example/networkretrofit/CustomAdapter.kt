package com.example.networkretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.networkretrofit.databinding.ItemRecyclerBinding


class CustomAdapter : RecyclerView.Adapter<Holder>() {
    var userList = mutableListOf<RepositoryItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // 레이아웃을 인플레이트 한 후 홀더에 담아서 반환합니다.
        // xml 레이아웃들이 객체화되는 과정은 메모리에 로딩되어 화면(뷰그룹)으로 드러나는데, 이 과정을 Inflation이라 한다.
        // 매개변수 설명 : inflate( 1.객체화하고픈 xml파일, 2.객체화한 뷰를 넣을 부모 레이아웃/컨테이너, 3.true(바로 인플레이션 하고자 하는지))
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        // Holder에 view 변수를 담아서 반환한다.
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 현 위치의 사용자 데이터를 userList에서 가져옵니다.
        val user = userList.get(position)
        holder.setUser(user)
    }
}
// ViewHodler를 상속받고, 아이템뷰를 받는 처리를 해줍니다.
class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ItemRecyclerBinding
    // 기존과는 달리 RepositoryItem 으로 제네럴 되는 것 같음
    fun setUser(user : RepositoryItem) {
        binding = ItemRecyclerBinding.bind(itemView)
        binding.textName.text = user.name
        binding.textId.text = user.node_id
        Glide.with(itemView).load(user.owner.avatar_url).into(binding.imageAvatar)
    }
}