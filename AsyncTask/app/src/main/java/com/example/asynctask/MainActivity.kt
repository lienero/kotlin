package com.example.asynctask

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.asynctask.databinding.ActivityMainBinding
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonDownload.setOnClickListener {
            // 메서드의 파라미터에 문자열로 된 URL을 전달할 것이기 떄문에 첫 번째 제네릭은 String
            // 두 번쩨 제네릭은 사용하지 않기 때문에 Void,
            // 세번째는 백그라운드에서 다운로드한 이미지를 화면에 세팅해야 하므로 파라미터 타입이 Bitmap입니다.
            // Bitmap은 주소 오류 등으로 null이 입력될 수 있으므로 Bitmap? null 안정성 체크를 해야 합니다.
            val asynTask = object : AsyncTask<String, Void, Bitmap?>() {
                // execute() 메서드로부터 전달받은 문자열로 URL을 생성하고 URL과 연결된 Stream을 반환받습니다.
                override fun doInBackground(vararg params: String?): Bitmap? {
                    val urlString = params[0]!!
                    try {
                        val url = URL(urlString)
                        val stream = url.openStream()
                        // BitmapFactory의 docodeStream() 메서드로 Url의 stream으로 Bitmap 객체를 생성 후 반환합니다.
                        return BitmapFactory.decodeStream(stream)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        return null
                    }
                }

                // Void 타입인 onProgressUpdate()는 사용하지 않으므로 override하지 않아도 됩니다.
                override fun onProgressUpdate(vararg values: Void?) {

                }

                // doInBackground() 메서드의 이미지 다운로드 작업이 끝나고 반환된 Bitmap 객체가 onPostExecute() 메서드로 인자로 전달됩니다.
                override fun onPostExecute(result: Bitmap?) {
                    super.onPostExecute(result)

                    if(result != null) {
                        // 전달받은 result 변수를 이미지뷰의 setImageBitmap() 메서드에 전달해 이미지를 표시합니다.
                        binding.imagePreview.setImageBitmap(result)
                    } else {
                        Toast.makeText(this@MainActivity, "다운로드 오류", Toast.LENGTH_SHORT).show()
                    }

                }
            }
            // asynTask?.execute : 메서드를 호출하면서 사용자로부터 입력받은 이미지 주소를 전달합니다.
            // AsyncTask가 실행되면 doInBackground() 메서드가 호출됩니다.
            asynTask?.execute(binding.editUrl.text.toString())
        }
    }
}