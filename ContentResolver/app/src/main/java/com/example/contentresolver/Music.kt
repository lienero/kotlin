package com.example.contentresolver

import android.net.Uri
import android.provider.MediaStore

// id: MediaStore가 음원을 구분하는 유니크 ID
// title : 음원의 제목
// artist : 음원의 아티스트
// albumId: 앨범을 구분하는 ID
// duration: 음원의 길이
class Music (id: String, title: String, artist: String?, albumId: String?, duration: Long?) {
    var id: String = ""
    var title: String?
    var artist: String?
    var albumId: String?
    var duration: Long?

    init {
        this.id = id
        this.title = title
        this.artist = artist
        this.albumId = albumId
        this.duration = duration
    }

    // 음원의 URI를 생성하는 메서드
    fun getMusicUri(): Uri {
        return Uri.withAppendedPath(
            // 음원 URI는 기본 MediaStore의 주소와 음원 ID를 조합해서 만든다.
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
        )
    }

    fun getAlbumUri() : Uri{
        // 앨범아트의 URI 문자열을 Uri.parse() 메서드로 해석하여 URI를 생성합니다.
        return Uri.parse(
            "content://media/external/audio/albumart/" + albumId
        )
    }
}