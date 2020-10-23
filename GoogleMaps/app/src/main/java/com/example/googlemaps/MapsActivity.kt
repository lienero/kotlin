package com.example.googlemaps

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

// 안드로이드는 구글 지도가 준비되면,
// OnMapReadyCallback 인터페이스의 onMapReady() 메서드를 호출해주면서 파라미터로 준비된 GoogleMap을 전달해줍니다
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // supportFragmentManager.findFragmentById(R.id.map) 메서드로 아이디가 map인 supportMapFragment를 찾은 다음
        // getMapAsync() 를 호출해서 안드로이드에 구글 지도를 그려달라는 요청을 합니다.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        // getMapAsync() 메서드는 메인 스레드에서 호출해야합니다.
        mapFragment.getMapAsync(this)
    }
    
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        // LatLng(위도, 경도)
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    // 여기서 부터 권한 처리 관련 메소드
    // 카메라 권한의 승인 상태 가져오기
    val permission = arrayOf(
        Manifest.permission.ACCESS_CHECKIN_PROPERTIES,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    fun checkPermission() {
        var permitted_all = true
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
}