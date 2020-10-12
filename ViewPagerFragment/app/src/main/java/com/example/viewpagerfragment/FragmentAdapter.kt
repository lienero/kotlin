package com.example.viewpagerfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    // 메뉴 형태로 사용하는 뷰페이저의 화면 아이템은 대부분 중간에 개수가 늘거나 줄지 않고, 처음에 정해진 개수만큼 사용합니다.
    // 그래서 mutableListOf가 아닌 listOf를 사용하는 것이 효율적입니다.
    var fragmentList = listOf<Fragment>()

    //  FragmentStateAdapte의 필수 메소드
    // getCount() : 어댑터가 화면에 보여줄 전체 프래그먼트의 개수를 반환해야합니다.
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    // createFragment(position: Int): Fragment : 현재 페이지의 position이 파라미터로 넘어옵니다. position에 해당하는 위치의 프래그먼트를 반환해야 합니다.
    override fun createFragment(position: Int): Fragment {
        return fragmentList.get(position)
    }

}