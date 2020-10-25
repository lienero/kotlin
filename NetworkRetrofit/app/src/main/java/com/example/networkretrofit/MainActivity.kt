package com.example.networkretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networkretrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val adapter = CustomAdapter()
        mainBinding.recyclerView.adapter = adapter
        mainBinding.recyclerView.layoutManager  = LinearLayoutManager(this)

        // Retrofit.Builder()로 레트로핏을 생성하고 retrifit 변수에 담습니다.
        val retrofit = Retrofit.Builder()
                // 베이스가 되는 Github의 도메인 주소를 입력합니다.
                .baseUrl("https://api.github.com/")
                // JSON 데이터를 앞에 생성한 RepositoryItem 클래스의 컬렉션으로 변환해주는 컨버터를 입력합니다.
                .addConverterFactory(GsonConverterFactory.create())
                // 호출해 생성합니다.
                .build()

        mainBinding.buttonRequest.setOnClickListener {
            // 앞에서 정의한 인터페이스를 파라미터로 넘겨주면 실행가능한 서비스 객체를 생성해서 반환해줍니다.
            val githubService = retrofit.create(GithubService::class.java)
            // githubService에는 GithubService 인터페이스를 이용하여 객체를 생성했기 때문에 실행 가능한 상태의 user() 메서드를 가지고 있습니다.
            // 레트로핏의 create() 메서드는 인터페이스를 실행 가능한 서비스 객체로 만들면서 users() 메서드 안에 비동기 통신으로 데이터를 가져오는
            // enqueue() 메서드를 추가해 놓았습니다. enqueue()가 호출되면 통신이 시작됩니다.
            // enqueue() 메서드를 호출한 후 Github Api 서버로부터 응답을 받으면 enqueue() 안에 작성하는 콜백 인터페이스가 작동하게 됩니다.
            githubService.users().enqueue(object : Callback<List<RepositoryItem>>{
                // onResponse 통신이 성공적일 경우에 작동
                override fun onResponse(call: Call<List<RepositoryItem>>, response: Response<List<RepositoryItem>>) {
                    // 두번쨰 파라미터인 response의 body() 메서드를 호출하면 서버로부터 전송된 데이터를 꺼낼 수 있습니다.
                    // 꺼낸 데이터를 List<RepositoryItem>으로 형변환 후에 어댑터의 userList에 담습니다.
                    adapter.userList.addAll(response.body() as List<RepositoryItem>)
                    // 어댑터의 notifyDataSetChanged를 호출하면 리사이클러뷰에 변경된 사항이 반영됩니다.
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<List<RepositoryItem>>, t: Throwable) {
                }
            })
        }
    }
}

// 레트로핏 인터페이스는 호출 방식, 주소, 데이터 등을 지정합니다.
// Retrofit 라이브러리는 인터페이스를 해석해 HTTP 통신을 처리합니다.
interface GithubService {
    // 깃허브 API를 호출할 users 메서드를 만들고 @GET 애노테이션을 사용해서 요청 주소를 설정합니다.
    @GET("users/Kotlin/repos")
    // 반환 값은 Call<List<데이터클래스>> 형태로 작성합니다.
    fun users(): Call<List<RepositoryItem>>

}