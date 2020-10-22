package com.example.contentresolver

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contentresolver.databinding.ActivityMainBinding
import com.example.contentresolver.databinding.ItemRecyclerBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }
    // 카메라 권한의 승인 상태 가져오기
    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

    fun checkPermission() {
        //상태가 승인일 경우에는 코드 진행
        if(ContextCompat.checkSelfPermission(this, permission[0]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions() // 미승인이면 권한요청
        } else { //승인되지 않았다면 권한 요청 프로세스 진행
            startProcess() //승인이면 프로그램 진행
        }
    }

    fun requestPermissions() {
        // ActivityCompat.requestPermissions: 미승인된 권한을 사용자에게 요청하는 메서드
        // 두번쨰 파라미터는 배열이며 이는 권한이 복수 개일 때를 대비한 것입니다.
        // 세번쨰 파라미터는 퀘스트 코드로 startActivityForResult에서 사용했던 것처럼
        // 권한을 요청한 주제가 어떤 것인지 구분하기 위해서 코드를 숫자로 입력해서 사용합니다.
        ActivityCompat.requestPermissions(this, permission,99)
    }

    override fun onRequestPermissionsResult(
        //requestCode : 요청한 주제를 확인하는 코드로, requestPermissions() 메서드의 세번째 파라미터로 전달됩니다.
        requestCode: Int,
        //permissions: 요청한 권한 목록 requestPermissions() 메서드의 두 번째 파라미터로 전달됩낟.
        permissions: Array<out String>,
        //grantResult: 권한 목록에 대한 승인 미승인 값, 권한 목록의 개수와 같은 수의 결괏값이 전달됩니다.
        grantResults: IntArray
    ) {
        when (requestCode) {
            99 -> {
                //권한 결괏값 확인 후 실행 내용 결정
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startProcess()
                } else {
                    finish()
                }
            }
        }
    }
    fun startProcess() { // 승인이면 프로그램 진행하는 메소드
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val adapter = MusicRecyclerAdapter()
        adapter.musicList.addAll(getMusicList())
        mainBinding.recyclerView.adapter = adapter
        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // 음원을 읽어오는 메소드 작성
    fun getMusicList(): List<Music> {
        // 음원 정보의 주소를 listUrl 변수에 저장
        val listUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        // 음원 정보의 컬럼들
        val proj = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION
        )
        /*  contentResolver.query 파라미터 5개 :
            uri: Uri  "테이블의 주소 Uri"
            projection: String[] "테이블의 컬럼명 배열"
            selection:String "데이터의 검색 조건. 어떤 컬럼을 검색할 것인지 컬럼명 지정
            selectionArgs:String[] "조건의 값 세번째 컬럼명에 입력할 값)
            sortOrder:String 정렬 순서, 정렬할 컬럼이 오름차순인지 내림차순인지를 설정
        */
        // null을 입력하면 전체 데이터를 읽어옵니다.
        // 콘텐트 리졸버의 쿼리에 주소와 컬럼명을 입력하면 커서 형태로 반환 받는다.
        val cursor = contentResolver.query(listUri, proj, null, null, null)
        val musicList = mutableListOf<Music>()
        // 반복문으로 커서를 이동하면서 데이터를 한 줄씩 읽습니다.
        while (cursor?.moveToNext() == true) {
            // getString은 컬럼 타입이 문자일 때 데이터를 꺼내는 메소드입니다.
            // getLong은 컬럼 타입이 숫자일 때 데이터를 꺼내는 메소드입니다.
            // 메소드 뒤에 입력되는 숫자는 커서에 있는 컬럼 데이터의 순서로 앞에서 proj변수에 저장해두었던 컬럼의 순서와 같습니다.
            var id = cursor.getString(0)
            val title = cursor.getString(1)
            val artist = cursor.getString(2)
            val albumId = cursor.getString(3)
            val duration = cursor.getLong(4)

            val music = Music(id, title, artist, albumId, duration)
            musicList.add(music)
        }
        return musicList
    }
}