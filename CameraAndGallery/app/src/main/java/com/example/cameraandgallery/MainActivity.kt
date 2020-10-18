package com.example.cameraandgallery

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cameraandgallery.databinding.ActivityMainBinding
import java.io.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    // 권한 정의 : 권한을 그룹별로 나눠서 저장소 권한과 카메라 권한을 따로 상수에 저장해둡니다.
    val CAMERA_PREMISSION = arrayOf(Manifest.permission.CAMERA)
    val STORAGE_PREMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    
    // 권한 플래그값 정의 : 숫자로 사용했던 권한 요청 플래그를 상수로 미리 정의하고 사용합니다.
    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_SOTRAGE = 99

    // 카메라, 갤러리를 호출하는 플래그 상수 정의
    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_STORAGE = 102

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonCamera.setOnClickListener{
            if(isPermitted(CAMERA_PREMISSION)) {
                // 실제 카메라를 호출하는 코드
                openCamera()
            } else {
                ActivityCompat.requestPermissions(this, CAMERA_PREMISSION, FLAG_PERM_CAMERA)
            }
        }
        binding.buttonGallery.setOnClickListener{
            if(isPermitted(STORAGE_PREMISSION)) {
                // 갤러리를 호출하는 코드
                openGallery()
            } else {
                ActivityCompat.requestPermissions(this, STORAGE_PREMISSION, FLAG_PERM_SOTRAGE)
            }
        }
    }

    /* 권한 체크 메서드 : 승인후 처리를 하는 코드는 반복문으로 사용하기 때문에 개수와 상관없이 그대로 사용할 수 있습니다.
        checkPermission() 메서드도 비슷한 형태로 바꿀 수 있습니다. */
    // checkPermission이 실패하면, 권한 요청 팝업이 실행됩니다. 그리고 onRequestPermissionResult로 결과 값이 전달됩니다.
    fun isPermitted(permissions:Array<String>) : Boolean {
        for(permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this,permission)
            if(result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    // 카메라를 호출하는 메소드
    // 먼저 권한을 처리한 후에 카메라 요청 로직을 다시 한 번 호출해야하기 때문에 setViews() 함수에서 직접 호출하지 않고 분리한다.
    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, FLAG_REQ_CAMERA)
    }

    fun openGallery() {
        // intent의 파라미터로 ACTION_PICK을 사용하면 intent.type에 설정한 종류의 데이터를 MediaStroe에서 불러와서
        // 목록으로 나열한후 선택할 수 있는 앱이 실행됩니다. 다음과 같이 설정하면 이미지만 불러옵니다.
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, FLAG_REQ_STORAGE)
    }

    fun saveImageFile(filename: String, mimeType: String, bitmap: Bitmap) : Uri? {
        var values = ContentValues()
        // 외부 저장소에서 파일을 저장하기 위해서는 MediaStroe를 통해서만 가능합니다.
        // 물론 촬영한 이미지를 내부 저장소에 저장한다면 이 과정은 필요하지 않습니다.
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        // 안드로이드 버전이 Q이상이면 다른 곳에서 내가 사용하는 데이터를 요청할 경우 무시하는 옵션을 추가한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // IS_PENDING 이 1일때 동작합니다.
            values.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        // MediaStore에 파일을 등록하고, 등록된 Uri를 item 변수에 저장합니다.
        // uri : 통합 자원 식별자는 URI는 특정 리소스 자원을 고유하게 식별 할 수 있는 식별자를 의미합니다.
        // URI의 하위 개념으로 웹 서버의 특정 리소스 위치를 나타내는 URL과 위치와 관계없이 유일한 URN이 있습니다.
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        // 예외처리를 해야한다. sd카드일 시 오류발생가능
        try {
            if (uri != null) {
                // 파일디스크립터를 열고 descriptor 변수에 저장합니다. 파일디스크립터로 파일을 읽거나 쓸 수 있습니다.
                var descriptor = contentResolver.openFileDescriptor(uri, "w")
                if (descriptor != null) {
                    // 파일디스크립터를 사용할 수 있다면, FileOutputStrean으로 비트맵 파일을 저장합니다.
                    val fos = FileOutputStream(descriptor.fileDescriptor)
                    // 숫자 100운 압축률입니다. 숫자가 작을 수록 화질이 낮아지면서 파일의 크기도 작아집니다.
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.close()
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        values.clear()
                        values.put(MediaStore.Images.Media.IS_PENDING, 0)
                        contentResolver.update(uri,values,null,null)
                    }
                }
            }
        } catch (e:Exception) {
            Log.e("Camera", "error=${e.localizedMessage}")
        }
        return uri
    }

    // 현재 시간을 이용해서 파일명을 생성하는 메소드
    fun newFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return filename
    }

    // 카메라가 정상적으로 호출된 후 사용자가 사진 촬영을 완료하면 onActivityResult() 메서드로 결과값이 전달됩니다.
    // 촬영한 사진 정보는 세번째 파라미터인 data에 인텐트로 전달됩니다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode) {
                FLAG_REQ_CAMERA -> {
                    if(data?.extras?.get("data") != null){
                        // 전달받은 data 파라미터에서 사진을 꺼낸 후 이미지뷰에 세팅합니다.
                        /* data 파라미터를 통해 전달되는 사진은 data.extras.get("data)로 꺼낼 수 있습니다.
                           꺼낸 값이 먼저 null인지 아닌지 확인한 후에 Bitmap으로 형변환을 하고 사용해야합니다.
                           data.extras.get으로 꺼낸 값의 타입이 Object이므로 변환하지 않으면 이미지뷰에 세팅할 수 없습니다.
                           그리고 data나 extras가 null일 수도 있기 떄문에 중간에 ?를 붙여서 null 안정성을 체크합니다.*/
                        val bitmap = data?.extras?.get("data") as Bitmap
                        val filename = newFileName()
                        val uri = saveImageFile(filename, "image/jpeg", bitmap)
                    }
                }
                FLAG_REQ_STORAGE -> {
                    setContentView(binding.root)
                    // 호출된 갤러리에서 이미지를 선택하면 data의 data 속성으로 이미지의 Uri가 전달됩니다.
                    // 전달된 Uri를 이미지뷰에 세팅할 수 있습니다.
                    val uri = data?.data
                    binding.imagePreview.setImageURI(uri)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            // 카메라 권한 후처리 : 카메라 권한 승인 후 처리는 동일합니다.
            FLAG_PERM_CAMERA -> {
                var checked = true
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        checked = false
                        break
                    }
                }
                if (checked) {
                    // openCamera() 에서 checkPermission이 실패할 경우에 onRequestPermissionsResult에서 실행되는 for문 다음에 openCamera()를 한 번 더 호출해야 한다.
                    openCamera()
                }
            }
        }
    }
}