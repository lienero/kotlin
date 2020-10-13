package com.example.viewpagerfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {


    // 메뉴 형태로 사용하는 뷰페이저의 화면 아이템은 대부분 중간에 개수가 늘거나 줄지 않고, 처음에 정해진 개수만큼 사용합니다.
    // 그래서 mutableListOf가 아닌 listOf를 사용하는 것이 효율적입니다.
    // 리사이클러뷰 어댑터에서 사용했던 것처럼 페이저어댑터도 화면에 표시해줄 아이템의 목록이
    // 필요합니다. fragmentList 변수를 하나 만들고 초기화합니다
    var fragmentList = listOf<Fragment>()

    // FragmentStateAdapter의 필수 메소드
    // 먼저 페이지의 개수를 결정하기 위해, getItemCount() 메서드에서 프래그먼트의 개수를 리턴합니다.
    // 페이지가 요청될 때 getItem()으로 요청되는 페이지의 position 값이 넘어옵니다.
    // position 값을 이용해서 프래그먼트 목록에서 해당 position에 있는 프래그먼트 1개를 리턴합니다.
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    // createFragment(position: Int): Fragment : 현재 페이지의 position이 파라미터로 넘어옵니다. position에 해당하는 위치의 프래그먼트를 반환해야 합니다.
    override fun createFragment(position: Int): Fragment {
        return fragmentList.get(position)
    }


}