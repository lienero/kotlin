package com.example.contentresolver

import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contentresolver.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class MusicRecyclerAdapter : RecyclerView.Adapter<MusicRecyclerAdapter.Holder>() {
    // 음악의 목록을 저장해두는 변수 생성
    var musicList = mutableListOf<Music>()
    var mediaPlayer: MediaPlayer? = null
    
    // 화면에 보이는 아이템 레이아웃을 생성하는 메소드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        // 목록의 개수를 리턴
        return musicList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val music = musicList.get(position)
        holder.setMusic(music)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var binding: ItemRecyclerBinding
        var musicUri: Uri? = null

        // init 함수는 매개변수가 없고 반환되는 값이 없는 특별한 함수입니다.
        // 생성자를 통해 인스턴스가 만들어 질 떄 호출되는 함수입니다.
        init {
            // 생성자로 넘어온 itemView에 클릭리스너를 달아줍니다.
            itemView.setOnClickListener {
                if (mediaPlayer != null) {
                    // release() : 시스템 자원을 해제한다.
                    mediaPlayer?.release()
                    mediaPlayer = null
                }
                mediaPlayer = MediaPlayer.create(itemView.context, musicUri)
                mediaPlayer?.start()
            }
        }

        fun setMusic(music: Music) {
            // 리사이클러뷰에서 뷰 그룹에서 가져올 경우에는 bind를 쓴다?
            binding = ItemRecyclerBinding.bind(itemView)
            binding.imageAlbum.setImageURI(music.getAlbumUri())
            binding.textArtist.text= music.artist
            binding.textTitle.text = music.title

            // 음악 재생 시간을 "분:초" 형태로 변환해서 사용한다.
            val duration = SimpleDateFormat("mm:ss").format(music.duration)
            binding.textDuration.text = duration
            // 클릭 시 플레이를 할 수 있게 음원의 Uri를 저장해둡니다.
            this.musicUri = music.getMusicUri()
        }

    }

}
