package com.example.networkhttpurlconnection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.networkhttpurlconnection.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonRequest.setOnClickListener {
            // 버튼을 클릭하면 네트워크 작업을 요청하고 이를 백그라운드 스레드에서 처리합니다.
            thread(start = true) {
                try {
                    // 주소 입력 필드에 입력된 주소를 가져옵니다.
                    var urlText = binding.editUrl.text.toString()
                    // http로 시작하지 않으면 앞에 https:// 를 붙여줍니다.
                    if (!urlText.startsWith("https")) {
                        urlText = "https://${urlText}"
                    }
                    // 조스를 URL객체로 변환하고 변수에 저장합니다.
                    val url = URL(urlText)
                    // URL 객체에서 openConnection() 메서드를 사용하여 서버와의 연결을 생성합니다.
                    // 그리고 HttpURLConnection으로 형변환해줍니다.
                    // opneConnection() 메서드에서 반환되는 값은 URLConnection이라는 추상 클래스입니다.
                    // 추상 클래스를 사용하기 위해서는 실제 구현 클래스인 HttpURLConnection으로 변환 하는 과장이 필요합니다.
                    val urlConnection = url.openConnection() as HttpURLConnection
                    // 연결된 커넥션에 요청 방식을 설정합니다.
                    urlConnection.requestMethod = "GET"
                    // 응답이 정상이면 응답 데이터를 처리합니다.
                    if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                        // 네트워크 입력 스트립(input Stream)
                        // 데이터를 읽어오기 위해서는 HttpURLConnection에서 inputStream 이라는 읽기 전용 파이프를 꺼내서 사용해야합니다.
                        val streamReader = InputStreamReader(urlConnection.inputStream)
                        val buffered = BufferedReader(streamReader)
                        val content = StringBuilder()

                        while (true) {
                            // 반복문을 돌리면서 한 줄씩 읽은 데이터를 content 변수에 저장합니다.
                            // ?: 해당 값이 null일 경우에 break 한다.
                            val line = buffered.readLine() ?: break
                            content.append(line)
                        }
                        // 사용한 스트립과 커넥션을 모두 해제합니다.
                        buffered.close()
                        urlConnection.disconnect()

                        runOnUiThread {
                            // 화면 텍스트뷰에 content 변수에 저장된 값을 입력합니다.
                            binding.textContent.text = content.toString()
                        }
                    }
                } catch (e:Exception) {
                    // 네트워크 관련 코드는 예외로 치명적인 오류(앱다운)가 발생할 수 있습니다.
                    // e.printStackTrace()메서드는 예외 발생시 로그를 출력하는 역할을 합니다.
                    // 서브 스레드로 동작하기 때문에 print 문을 사용하는 것보다 성능이 좋습니다.
                    e.printStackTrace()
                }
            }
        }
    }
}