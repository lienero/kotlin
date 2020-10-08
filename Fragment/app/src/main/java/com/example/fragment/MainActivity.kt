package com.example.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceControl

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment()

    }

    /* 프레그먼트를 삽입하는 과정은 하나의 트랜잭션으로 관리되기떄문에 트랜잭션 매니저를 통해
       brgin transaction > add fragment > commit transaction 순서로 처리합니다.
     */
    // 트랜잭션이란 여러 개의 의존성이 있는 동작을 한 번에 실행할 떄 중간에 하나라도 잘못되면 모든 동작을 복구하는 하나의 작업 단위입니다.
    fun setFragment() {
        val fragment = ListFragment()
        //액티비티가 가지고 있는 프래그먼트 매니저를 통해서 트랜잭션을 시작하고, 시작한 트랜잭션을 변수에 저장
        val transaction = supportFragmentManager.beginTransaction()
        // add(레이아웃, 프래그먼트) : R.id.frameLayout에 listFragment값을 삽입
        transaction.add(R.id.frameLayout, fragment)
        transaction.commit()
    }

    fun goDetail() {
        val detail = DetailFragment()
        //액티비티가 가지고 있는 프래그먼트 매니저를 통해서 트랜잭션을 시작하고, 시작한 트랜잭션을 변수에 저장
        val transaction = supportFragmentManager.beginTransaction()
        // add(레이아웃, 프래그먼트) : R.id.frameLayout에 listFragment값을 삽입
        transaction.add(R.id.frameLayout, detail)
        // addToBackStack : 뒤로가기 버튼(사용하지 않은 채 뒤로가기 버튼을 누르면 종료됨)
        transaction.addToBackStack("detail")
        transaction.commit()
    }

    fun goBack() {
        // onBackPressed() 뒤로 가기가 필요할 떄 액티비티에서 기본으로 사용할 수 있는 메서드
        onBackPressed()
    }
}