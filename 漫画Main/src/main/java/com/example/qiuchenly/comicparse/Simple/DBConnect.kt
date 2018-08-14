package com.example.qiuchenly.comicparse.Simple

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import java.util.*

class DBConnect(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    private var mDataBase: SQLiteDatabase? = null

    override fun onCreate(db: SQLiteDatabase) {
        mDataBase = db
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    private fun canuse(): Boolean {
        return mDataBase != null
    }

    fun initDB() {
        var schema = "create table if not exists " + RECENTLY_WEEK + "(id integer primary key autoincrement," +
                "BookName TEXT not null," +
                "BookName_Link TEXT not null," +
                "BookName_Pic_Link TEXT not null," +
                "BookName_read_point TEXT not null," +
                "BookName_author TEXT not null);"
        writableDatabase.execSQL(schema)
        schema = "create table if not exists " + MY_BOOK_LIST + "(id integer primary key autoincrement," +
                "BookName TEXT not null," +
                "BookName_Link TEXT not null," +
                "BookName_Pic_Link TEXT not null," +
                "BookName_read_point TEXT not null," +
                "BookName_author TEXT not null);"
        writableDatabase.execSQL(schema)
    }

    fun RECENTLY_INSERT(book: ComicBookInfo.ComicBookInfo_Recently) {
        INSERT(book, RECENTLY_WEEK)
    }

    fun LOCALBOOK_INSERT(book: ComicBookInfo.ComicBookInfo_Recently) {
        INSERT(book, MY_BOOK_LIST)
    }

    fun LOCALBOOK_GET_READ_POSITION(bookName: String): String {
        val cur = hasKey_Ex(RECENTLY_WEEK, "BookName", bookName)
        return if (cur.moveToFirst())
            cur.getString(4)
        else
            "还没有看哦"
    }

    fun INSERT(book: ComicBookInfo.ComicBookInfo_Recently, tbName: String) {
        if (hasKey(tbName, "BookName", book.BookName)) {
            //updatePoint(book.BookName, book.BookName_read_point);
            println("数据存在，不重复插入。")
            return
        }
        val values = ContentValues()
        values.put("BookName", book.BookName)
        values.put("BookName_Link", book.BookName_Link)
        values.put("BookName_Pic_Link", book.BookName_Pic_Link)
        values.put("BookName_read_point", book.BookName_read_point)
        values.put("BookName_author", book.author)
        writableDatabase.insert(tbName, null, values)
    }

    private fun hasKey(TBName: String, key: String, value: String?): Boolean {
        val s = "select * from $TBName where $key='$value';"
        val a = readableDatabase.rawQuery(s, null)
        return a.moveToFirst()
    }

    private fun hasKey_Ex(TBName: String, key: String, value: String): Cursor {
        val s = "select * from $TBName where $key='$value';"
        val a = readableDatabase.rawQuery(s, null)
        a.moveToFirst()
        return a
    }

    fun updatePoint(Bookname: String, point: String) {
        val s = "UPDATE recently SET BookName_read_point = '$point' WHERE BookName = '$Bookname';"
        writableDatabase.execSQL(s)

    }

    fun RECENTLY_GET_ALL(): List<ComicBookInfo.ComicBookInfo_Recently> {
        return GET_ALL(RECENTLY_WEEK)
    }

    private fun GET_ALL(tbName: String): List<ComicBookInfo.ComicBookInfo_Recently> {
        val c = readableDatabase.rawQuery("SELECT * FROM $tbName;", null)
        val list = ArrayList<ComicBookInfo.ComicBookInfo_Recently>()
        if (c.moveToFirst()) {
            do {
                val a = ComicBookInfo.ComicBookInfo_Recently()
                a.BookName = c.getString(1)
                a.BookName_Link = c.getString(2)
                a.BookName_Pic_Link = c.getString(3)
                a.BookName_read_point = c.getString(4)
                a.author = c.getString(5)
                list.add(a)
                if (c.isLast)
                    break
            } while (c.moveToNext())
        }
        return list
    }

    //获取所有本地书架
    fun LOCALBOOK_GET_ALL(): List<ComicBookInfo.ComicBookInfo_Recently> {
        return GET_ALL(MY_BOOK_LIST)
    }

    companion object {

        val TABLE_STUDENT = "students"

        //创建 students 表的 sql 语句
        private val STUDENTS_CREATE_TABLE_SQL = ("create table" + TABLE_STUDENT + "("
                + "id integer primary key autoincrement,"
                + "name varchar(20) not null,"
                + "tel_no varchar(11) not null,"
                + "cls_id integer not null"
                + ");")

        private val MY_BOOK_LIST = "myBook"
        private val RECENTLY_WEEK = "recently"
    }

}