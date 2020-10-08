package com.example.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.view.*



class ListFragment : Fragment() {

    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //onAttach() 메서드를 통해서 넘어오는 Context는 부모 액티비티가 전체가 담겨있습니다. as 키워드를 사용해서 액티비티로 다시 변환하고 사용할 수 있습니다.
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        //inflater : 레이아웃 파일을 로드하기 위한 레이아웃 인플레이터를 기본으로 제공
        //container : 프레그먼트 레이아웃이 배치되는 부모 레이아웃(액티비티의 레이아웃입니다)
        //savedInstanceState : 상태값 저장을 위한 보조 도구, 액티비티의 onCreate의 파라미터와 동일하게 동작합니다.
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*** 원본코드: inflater로 생성한 뷰를 바로 리턴하는 구조입니다. ***/
        // return inflater.inflate(R.layout.fragment_list, container, false)

        /*** 수정코드: inflater로 생성한 뷰(fragment_list)를 view 변수에 담아두고, view 안에 있는 버튼에 리스너를 등록한 후에 리턴합니다. ***/
        // !! : 에러를 패스할 떄 쓰는 것(NULL을 문자형으로 인식하고 넘김)
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        view.btnNext.setOnClickListener {
            mainActivity?.goDetail()
        }
        return view
    }

}