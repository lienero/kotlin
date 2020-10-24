package com.example.googlemaps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

// 안드로이드는 구글 지도가 준비되면,
// OnMapReadyCallback 인터페이스의 onMapReady() 메서드를 호출해주면서 파라미터로 준비된 GoogleMap을 전달해줍니다
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    // 위치 값 사용을 위한 변수
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    // 위치 값 요청에 대한 갱신 정보를 받는데 필요합니다.
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }
    
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        updateLocation()
    }
    
    @SuppressLint("MissingPermission")
    // 권한 체크를 onCreate에서 했기 때문에 권하넹 대한 오류 처리 필요
    fun updateLocation() {
        // 위치 정보를 요청할 정확도와 주기를 설정할 locationRequest를 생성
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            // PRIORITY_HIGH_ACCURACY : 배터리소모를 고려하지 않으며 정확도를 최우선으로 고려
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            // 주기는 1초(1.000 밀리초)
            interval = 1000
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    for ((i, location) in it.locations.withIndex()) {
                        Log.d("Location", "$i ${location.latitude}, ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }
        // onMapReady에서 생성한 위치 검색 클라이언트의 requestLocationUpdates()에 앞에서 생성한 2개와 함께 루퍼 정보를 넘겨줍니다.
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    fun setLastLocation(lastLocation: Location) {
        // 전달 받은 위치정보로 좌표 생성 LatLng(위도, 경도)
        val LATLNG = LatLng(lastLocation.latitude, lastLocation.longitude)
        // 해당 좌표로 마커 생성 MarkerOptions().position(마커의 좌표).title("마커의 제목")
        val markerOptions = MarkerOptions().position(LATLNG).title("Here!")
        // CameraPosition.옵션1.옵션2.Builder()
        val cameraPosition = CameraPosition.Builder()
                // target은 지도의 중심 위치이며 위도 및 경도 좌표로 지정됩니다.
                .target(LATLNG)
                // zoom은 레벨에 따라 지도의 배율이 결정됩니다. 줌 레벨이 높을 수록 더 자세한 지도를 볼 수 있습니다.
                .zoom(15.0f)
                .build()
        // 마커를 지도에 반영하기 전에 이전에 그려진 마커가 있으면 지웁시다.
        mMap.clear()
        mMap.addMarker(markerOptions)
        // moveCamera() 카메라의 포지션을 기준으로 지도의 위치, 배율, 기울기 등이 변경되서 표시됩니다.
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    // 여기서 부터 권한 처리 관련 메소드
    // 카메라 권한의 승인 상태 가져오기
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val PERM_LOCATION = 99
    fun checkPermission() {
        var permitted_all = true
        // 권한 개수와 상관없이 반복문을 돌면서 하나라도 승인처리가 되어 있지 않다면 다시 요청하는 코드입니다.
        for(permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if(result != PackageManager.PERMISSION_GRANTED){
                permitted_all = false
                //승인되지 않았다면 권한 요청 프로세스 진행
                requestPermissions()
                break
            }
        }
        if(permitted_all) {
            startProcess() //승인이면 프로그램 진행
        }
    }

    fun requestPermissions() {
        // ActivityCompat.requestPermissions: 미승인된 권한을 사용자에게 요청하는 메서드
        // 두번쨰 파라미터는 배열이며 이는 권한이 복수 개일 때를 대비한 것입니다.
        // 세번쨰 파라미터는 퀘스트 코드로 startActivityForResult에서 사용했던 것처럼
        // 권한을 요청한 주제가 어떤 것인지 구분하기 위해서 코드를 숫자로 입력해서 사용합니다.
        ActivityCompat.requestPermissions(this, permissions, PERM_LOCATION)
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
            PERM_LOCATION -> {
                var granted_all = true
                for (result in grantResults) {
                    //권한 결괏값 확인 후 실행 내용 결정
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        granted_all = false
                        break
                    }
                }
                if (granted_all) {
                    startProcess()
                } else {
                    Toast.makeText(this, "권한을 승인해야지만 앱을 사용할 수 있습니다", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }

    fun startProcess() { // 승인이면 프로그램 진행하는 메소드
        // 권한이 모두 승인되고 맵이 준비되면 onMapReady() 메서드가 정상적으로 호출됩니다.
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
}