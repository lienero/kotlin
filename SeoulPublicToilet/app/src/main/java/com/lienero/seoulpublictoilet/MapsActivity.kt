package com.lienero.seoulpublictoilet

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.lienero.seoulpublictoilet.data.Toilet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        // Add a marker in Sydney and move the camera
        // val sydney = LatLng(-34.0, 151.0)
        // mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        loadToilet()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        updateLocation()
    }

    fun loadToilet() {
        // 메서드 안에 도메인 주소와 JSON 컨버터를 설정해서 레트로핏을 생성합니다.
        val retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val seoulOpenService = retrofit.create(SeoulOpenService::class.java)

        seoulOpenService
            .getToilet(SeoulOpenApi.API_KEY)
            .enqueue(object : Callback<Toilet> {
                override fun onFailure(call: Call<Toilet>, t: Throwable) {
                    Toast.makeText(baseContext, "서버에서 데이터를 가져올 수 없습니다.", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<Toilet>, response: Response<Toilet>) {
                    showToilets(response.body() as Toilet)
                }
            })
    }

    fun showToilets(toilets: Toilet) {
        // 마커의 영역을 저장
        val latLngBounds = LatLngBounds.Builder()

        for (toi in toilets.SearchPublicToiletPOIService.row) {
            val position = LatLng(toi.Y_WGS84.toDouble(), toi.X_WGS84.toDouble())
            // 좌표와 화장실이 있는 빌딩의 이름으로 마커를 생성합니다.
            val marker = MarkerOptions().position(position).title(toi.FNAME)
            // 지도에 마커를 추가합니다.
            mMap.addMarker(marker)
            // latLngBounds에도 마커를 추가합니다.
            latLngBounds.include(marker.position)
        }

        val bounds = latLngBounds.build()
        val padding = 0
        // 마커의 영역과 여백을 옵션으로 하여 카메라를 업데이트
        val updated = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        // 업데이트된 카메라를 지도에 반영합니다.
    }

    @SuppressLint("MissingPermission")
    // 권한 체크를 onCreate에서 했기 때문에 권하넹 대한 오류 처리 필요
    fun updateLocation() {
        // 위치 정보를 요청할 정확도와 주기를 설정할 locationRequest를 생성
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            // PRIORITY_HIGH_ACCURACY : 배터리소모를 고려하지 않으며 정확도를 최우선으로 고려
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
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
        // 마커 생성
        var bitmapDrawable: BitmapDrawable

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bitmapDrawable = getDrawable(R.drawable.marker) as BitmapDrawable
        } else {
            bitmapDrawable = resources.getDrawable(R.drawable.marker) as BitmapDrawable
        }

        var locationMarker = BitmapDescriptorFactory.fromBitmap(bitmapDrawable.bitmap)
        // 전달 받은 위치정보로 좌표 생성 LatLng(위도, 경도)
        val LATLNG = LatLng(lastLocation.latitude, lastLocation.longitude)
        // 해당 좌표로 마커 생성 MarkerOptions().position(마커의 좌표).title("마커의 제목")
        val markerOptions = MarkerOptions().position(LATLNG).icon(locationMarker).title("Here!")
        // CameraPosition.옵션1.옵션2.Builder()
        val cameraPosition = CameraPosition.Builder()
            // target은 지도의 중심 위치이며 위도 및 경도 좌표로 지정됩니다.
            .target(LATLNG)
            // zoom은 레벨에 따라 지도의 배율이 결정됩니다. 줌 레벨이 높을 수록 더 자세한 지도를 볼 수 있습니다.
            .zoom(15.0f)
            .build()
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