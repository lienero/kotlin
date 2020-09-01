package com.example.designtool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //접근 제한자 테스트
        //child() 생성자 호출
        var child = Child()
        child.calVariables()

        // 부모 클래스 직접 호출해보기
        //Parent() 생성자 호출
        var parent = Parent()
        // 상속을 받지 않았기에 protected 접근 제한자에 접근할 수 없음
        //Log.d("Visibility","Parent: protected 변수의 값은 ${parent.protectedVal}입니다.")
        Log.d("Visibility","Parent: internal 변수의 값은 ${parent.internalVal}입니다.")

        fun testGenerics() {
            //String을 제네릭으로사용했기 떄문에 list 변수에는 문자열을 담을 수 있습니다.
            var list: MutableList<String> = mutableListOf()
            list.add("월")
            list.add("화")
            list.add("수")
            //list.add(35) // <- 입력 오류가 발생합니다.
            // String 타입의 item 변수로 꺼내서 사용할 수 있습니디.
            for (item in list) {
                Log.d("Visibility","list에 입력된 값은 ${item}입니다.")
            }
        }
        testGenerics()
        
    }
}
//추상 클래스 설계
//추상화: 명확하지 않은 코드를 구현 단계에서 작성하도록 메소드 이름만 선언한것(실행코드 있음)
abstract class Animal {
    fun walk() {
        Log.d("abstract","걷습니다.")
    }
    abstract fun move()
}

//구현
//Bird : Animal() : Bird클래스는 Animal() 클래스에게 상속받는다.
class Bird : Animal() {
    //oberride : 덮어쓰기
    override fun move(){
        Log.d("abstract","날아서 이동합니다.")
    }
}

//인터페이스 설계
//인터페이스 : 실행 코드 없이 메소드 이름만 가진 추상 클래스
interface InterfaceKotlin {
    var variable: String
    fun get()
    fun set()
}

//구현
class KotlinImpl : InterfaceKotlin {
    override var variable: String = "init value"
    override fun get() {
        //코드 구현
    }

    override fun set() {
        //코드 구현
    }
}

//접근 제한자 테스트를 위한 부모 클래스
open class Parent {
    //private : 다른 파일에서 접근할 수 없다
    private val privateVal = 1
    //protected : private와 같으나 상속 관계에서 자식 클래스가 접근할 수 있습니다.
    protected open val protectedVal = 2
    //internal : 같은 모듈에 있는 파일만 접근할 수 있습니다.
    internal val internalVal = 3
    //접근 제한자가 없는 멤버에는 public이 적용되어 접근할 수 있다.
    //public : 제한 없이 모든 파일에서 접근할 수 있습니다.
    val defaultVal = 4
}

//자식 클래스
class Child : Parent() {
    fun calVariables() {
        //privateVal은 호출이 안 됩니다.
        Log.d("Visibility","Child: protected 변수의 값은 ${protectedVal}입니다.")
        Log.d("Visibility","Child: internal 변수의 값은 ${internalVal}입니다.")
        Log.d("Visibility","Child: 기본 제한자 변수 defaultVal의 값은 ${defaultVal}입니다.")
    }
}