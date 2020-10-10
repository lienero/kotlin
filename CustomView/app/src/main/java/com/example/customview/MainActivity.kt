package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customView = CustomView("안녕 코틀린!",this)
        frameLayout.addView(customView)
    }
}
//View는 컨텍스트를 생성자에서 입력받아야 하기 때문에 CustomView에는 컨텍스트를 입력받는 생성자가 하나 꼭 있어야합니다.
class CustomView(text: String, context: Context) : View(context){
    val text : String
    init {
        this.text = text
    }
    //Canvas에는 그림판과 함께 그림을 그리기 위해서 draw로 시작하는 메서드들이 제공된다.
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //텍스트를 출력하기 위해서는 Canvas의 drawText() 메서드를 사용한다.
        //drawText()메서드는 출력할 문자열, 가로세로 좌표 그리고 글자의 색과 두께 정보를 가지고있는 Paint가 필요합니다.
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 100f
        canvas?.drawText(text,100f,300f,paint)
    }
}