package com.example.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_detail.view.*

class DetailFragment : Fragment() {
    // 메인 액티비티를 담아두는 변수 mainActivity를 선언합니다.
    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // !! : 에러를 패스할 떄 쓰는 것 (NULL을 문자형으로 인식하고 넘김)
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        view.btnBack.setOnClickListener {
            mainActivity?.goBack()
        }
        return view
    }


}