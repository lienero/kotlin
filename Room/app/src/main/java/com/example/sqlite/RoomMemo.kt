package com.example.sqlite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity(tableName = "이름"): 데이터베이스에서 테이블명을 클래스명과 다르게 하고 싶을 때 작성
@Entity(tableName = "orm_memo")
class RoomMemo {
    @PrimaryKey(autoGenerate = true)
    // 컬럼명도 테이블명처럼 변수명과 다르게 하고 싶을 때는 @ColumnInfo(name = "컬럼명")
    @ColumnInfo
    var no: Long? = null

    @ColumnInfo
    var content: String = ""

    @ColumnInfo
    var datetime: Long = 0

    constructor(content: String, datetime: Long){
        this.content = content
        this.datetime = datetime
    }
}