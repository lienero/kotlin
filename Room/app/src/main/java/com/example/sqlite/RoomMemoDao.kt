package com.example.sqlite

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao // 데이터베이스에 접근해서 DML 쿼리(SELECT, INSERT, UPDATE, DELETE)를 실행하는 메서드의 모음입니다.
interface RoomMemoDao {
    @Query("select * from orm_memo")
    fun getAll(): List<RoomMemo>

    //$Insert 어노테이션의 경우 옵션으로 onConflict = REPLACE를 적용하면 동일한 값이 입력되었을 때 UPDATE쿼리로 실행됩니다.
    @Insert(onConflict = REPLACE)
    fun insert(memo: RoomMemo)

    @Delete
    fun delete(memo: RoomMemo)
}