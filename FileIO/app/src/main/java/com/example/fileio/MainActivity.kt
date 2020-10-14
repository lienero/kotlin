package com.example.fileio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // absolutePath : 절대경로
        val dir = filesDir.absolutePath
        val filename = "first.txt"
        val content = "파일 내용 쓰기\n01. 안녕하세요.\n02. 두 번쨰 줄입니다."

        // 파일 쓰기
//        writeTextFile(dir, filename, content)

        // 파일 읽기
        val result = readTextFile(dir + "/" + filename)
        Log.d("파일내용", result)
    }

    //파일 읽기
    fun readTextFile(fullPath: String): String {
        // 파일 객체 생성
        val file = File(fullPath)
        // file.exists() : 파일 존재 여부 확인
        if (!file.exists()) return ""
        // FileReader(file): 파일을 읽음
        val reader = FileReader(file)
        // BufferedReader(reader): 한줄씩 읽음, 버퍼에 담으면 속도가 상승한다.
        val buffer = BufferedReader(reader)
        // 한줄씩 읽은 내용을 임시저장
        var temp:String? = ""
        // 모든 내용을 저장
        val result = StringBuffer()

        while (true) {
            // 줄 단위로 읽어서 임시 저장
            temp = buffer.readLine()
            // 읽을 내용이 없으면 반복문을 빠져나갑니다.(null)
            if (temp == null) break
            else result.append(temp).append("\n")
        }
        buffer.close()
        return result.toString()
    }

    // 파일쓰기
    // 파일을 생성할 디렉터리, 파일명, 작성할 내용 총 3가지 값이 전달되어야 한다.
    fun writeTextFile(directory: String, filename: String, content: String) {
        var dir = File(directory)
        if (!dir.exists()) {
            // mkdirs() : 디렉터리를 생성합니다. 생성하려는 디렉터리의 중간 경로도 함꼐 생성합니다.
            dir.mkdirs()
        }

        val fullPath = "$directory/$filename"
        // FileWriter : 디렉터리에 파일명을 합해서 생성
        val writer = FileWriter(fullPath)
        // buffer에 담으면 쓰기 속도가 상승한다.
        val buffer = BufferedWriter(writer)

        buffer.write(content)
        buffer.close()
    }
}