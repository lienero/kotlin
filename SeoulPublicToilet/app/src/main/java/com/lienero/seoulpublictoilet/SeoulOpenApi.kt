package com.lienero.seoulpublictoilet

import com.lienero.seoulpublictoilet.data.Toilet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class SeoulOpenApi {
    // companion object 내에 선언된 속성과 함수는 {클래스 이름}.{필드/함수 이름} 형태로 바로 호출할 수 있습니다.
    companion object {
        val DOMAIN = "http://openapi.seoul.go.kr:8088/"
        val API_KEY = "65446665706c69653132316d4c4c7476"
    }
}

interface SeoulOpenService {
    // @Path 애노테이션을 사용하면 메서드의 파라미터로 넘어온 값을 @GET에 정의된 주소에 동적으로 삽입할 수 있습니다.
    // @GET("{매핑할 이름}/json/GeoInfoPublicToilet/1/200")
    @GET("{api_key}/json/SearchPublicToiletPOIService/1/200")
    // 반환 값은 Call<JSON 변환된 클래스> 입니다.
    // @Path 애노테이션을 사용하면 메서드의 파라미터로 넘어온 값을 @GET에 정의된 주소에 동적으로 삽입할 수 있습니다.
    // getToilet(@Path("매핑할 이름") 파라미터: String): Call<Toilet>
    fun getToilet(@Path("api_key")key: String): Call<Toilet>
}