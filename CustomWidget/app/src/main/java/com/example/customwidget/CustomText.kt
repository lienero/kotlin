package com.example.customwidget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomText : AppCompatTextView {
    // 위젯 클래스를 소스코드에서 사용할 떄는 Context 하나만 입력받는 첫 번째 생성자가 호출되고,
    // 레이아웃 파일에서는 두 번쨰 생성자가 주로 호출됩니다.
    // 커스텀 위젯은 레이아웃에서도 사용되지만 코드에서도 직접 사용할 수 있기 때문에 항상 3개의 생성자를 모두 작성하는 편이 좋다.
    constructor(context: Context)
        : super (context) {
    }
    fun process(delimeter: String) {
        // substring(시작, 끝) : 입력된 텍스트의 앞 4글자 자르기
        var one = text.substring(0, 4)
        var two = text.substring(4, 6)
        // 입력된 텍스트의 마지막 2글자 자르기(총 8개의 숫자)
        var three = text.substring(6)

        //자른 글자 사이에 delimeter를 넣어서 화면에 세팅
        setText("$one $delimeter $two $delimeter $three")
    }
    constructor(context : Context, attrs: AttributeSet)
        : super (context, attrs) {
        // res/Value/attrs.xml에 정의된 어트리뷰트를 가져옵니다.
        val typed = context.obtainStyledAttributes(attrs, R.styleable.CustomText)
        val size = typed.indexCount

        for (i in 0 until size) {
            when (typed.getIndex(i)) {
                // 현재 속성을 확인하고 CustomText_delimeter와 같으면
                R.styleable.CustomText_delimeter -> {
                    // xml에 입력된 delimeter 값을 꺼내고
                    val delimeter = typed.getString(typed.getIndex(i)) ?: "-"
                    // 꺼낸 값을 process()메서드에 넘겨서 처리합니다.
                    process(delimeter)
                }
            }
        }
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
        : super (context, attrs, defStyleAttr) {
    }
}
