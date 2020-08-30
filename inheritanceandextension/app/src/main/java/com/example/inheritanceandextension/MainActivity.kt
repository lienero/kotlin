package com.example.inheritanceandextension

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1. 부모 클래스 직접 호출하기
        var parent = Parent()
        parent.sayHello()
        //2. 자식 클래스 호출해서 사용하기
        var child = Child()
        child.myHello()

        testStringExtension()
    }
    //Sting 익스텐션 테스트 하기
    fun testStringExtension() {
        var original = "Hello"
        var added = "Guys"
        //plus 함수를 사용해서 문자열을 더할 수 있습니다,
        Log.d("Extensione", "added를 더한 값은 ${original.plus(added)}입니다.")
    }
}
//상속 연습
//부모 클래스 생성
open class Parent {
    var hello : String = "안녕하세요"
    fun sayHello() {
        Log.d("Extensione","${hello}")
    }
}
//자식 클래스 생성
class Child : Parent() {
    fun myHello() {
        hello = "Hello"
        sayHello()
    }
}

//메소드 오버라이드 연습
open class BaseClass {
    open fun opened(){
    }
    fun notOpend(){
    }
}
class ChildClass : BaseClass(){
    override fun opened() {
    }
//    override fun notOpend() {//오버라이드되지 않고 에러가 발생합니다.
//    }
}
//프로퍼티 오버라이드 연습
open class BaseClass2 {
    open var oppend : String = "You are"
}
fun String.plus(word: String): String{
    return this + word
}