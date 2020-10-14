package com.example.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            checkPermission()
    }


        fun checkPermission() {
            // 카메라 권한의 승인 상태 가져오기
            val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            //상태가 승인일 경우에는 코드 진행
            if(cameraPermission == PackageManager.PERMISSION_GRANTED) {
                startProcess() //승인이면 프로그램 진행
            } else { //승인되지 않았다면 권한 요청 프로세스 진행
                requestPermissions() // 미승인이면 권한요청
            }
        }

        fun requestPermissions() {
            // ActivityCompat.requestPermissions: 미승인된 권한을 사용자에게 요청하는 메서드
            // 두번쨰 파라미터는 배열이며 이는 권한이 복수 개일 때를 대비한 것입니다.
            // 세번쨰 파라미터는 퀘스트 코드로 startActivityForResult에서 사용했던 것처럼
            // 권한을 요청한 주제가 어떤 것인지 구분하기 위해서 코드를 숫자로 입력해서 사용합니다.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),99)
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
            // 카메라를 실행한다는 메시지를 토스트로 알려주는 코드
            Toast.makeText(this, "카메라를 실행합니다", Toast.LENGTH_LONG).show()
        }


}
