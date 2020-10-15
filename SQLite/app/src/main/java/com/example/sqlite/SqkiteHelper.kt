package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//SQLiteOpenHelper는 생성시에 Context, 데이터베이스 명, 팩토리(사용 안해도 됨), 버전 정보를 필요로 합니다.
class SqliteHelper(context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(db: SQLiteDatabase?) {
       val create = "create table memo (" +
               "no integer primary key," +
               "content text," +
               "datetime integer" +
               ")"
        // 테이블 생성 쿼리 문자를 문자열로 입력한 후 db의 execSQL() 메서드에 전달해서 실행합니다.
        db?.execSQL(create)
    }
    // onUpgrade는 SqliteHelper에 전달되는 버전 정보가 변경되었을 떄 현재 생성되어있는
    // 데이터베이스의 버전과 비교해서 더 높으면 호출됩니다.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertMemo(memo : Memo) {
        // SQLiteOpenHelper 를 이용해서 값을 입력할 떄는 코틀린의 Map 클래스처럼 키, 값 형태로 사용되는 ContentValues 클래스를 사용합니다.
        val values = ContentValues()
        //ContentValues에 put("컬럼명", 값)으로 저장합니다.
        values.put("content", memo.content)
        values.put("datetime", memo.datetime)

        val wd = writableDatabase
        wd.insert("memo", null, values)
        wd.close()
    }

    fun seleectMemo(): MutableList<Memo> {
        // 조회 메서드의 반환 값을 받을 변수 선언
        val list = mutableListOf<Memo>()
        //  메모의 전체 데이터를 조회하는 쿼리 작성
        val select = "select * from memo"
        // 읽기 전용 데이터베이스를 변수에 담습니다.
        val rd = readableDatabase
        // 데이터베이스의 rawQuery() 메서드에 앞에서 작성해둔 쿼리를 담아서 실행하면 커서 형태로 값이 반환됩니다.
        // 커서(cursor) 데이터셋을 처리할 떄 현재 위치를 포함하는 데이트 요소이다.
        // 커서를 사용하면 쿼리를 통해 반환된 데이터셋을 반복무능로 반복하며 하나씩 처리할 수 있습니다.
        val cursor = rd.rawQuery(select, null)
        
        //모든 레코드 문을 읽을 떄가지 반복
        while (cursor.moveToNext()) {
            //반복문을 돌면서 테이블에 정의된 3개의 컬럼에서 값을 꺼낸 후 각각 변수에 담습니다.
            // val 컬럼 위치(cursor.get타입(컬럼위치 : cursor.getColumIndex("컬럼명")
            val no = cursor.getLong(cursor.getColumnIndex("no"))
            val content = cursor.getString(cursor.getColumnIndex("content"))
            val datetime = cursor.getLong(cursor.getColumnIndex("datetime"))

            // 앞에서 변수에 저장해두었던 값들로 Memo 클래스를 생헝하고 반환할 목록에 더합니다.
            list.add(Memo(no, content, datetime))
        }

        // 커서와 읽기 전용 데이터베이스를 모두 닫아줍니다.
        cursor.close()
        rd.close()
        return list
    }

    fun updateMemo(memo: Memo) {
        val values = ContentValues()
        values.put("content", memo.content)
        values.put("datetime", memo.datetime)

        val wd = writableDatabase
        // update(테이블명, 수정할 값, 수정할 조건)
        wd.update("memo", values, "no = ${memo.no}", null)
        wd.close()
    }

    fun deleteMemo(memo: Memo) {
        val delete = "deleter from memo where no = ${memo.no}"
        var db = writableDatabase
        db.execSQL(delete)
        db.close()
    }
}

// SQLite에서 INTEGER로 선언한 것은 소스 코드에서는 Long을 사용합니다.
// no만 null을 허용한 것은 PRIMARY KEY 옵션으로 값이 자동으로 증가되므로 데이터 삽입 시에는 필요하지 않기 때문입니다.
data class Memo(var no: Long?, var content: String, var datetime:Long)