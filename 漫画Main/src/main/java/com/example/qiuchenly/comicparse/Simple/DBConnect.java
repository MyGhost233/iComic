package com.example.qiuchenly.comicparse.Simple;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qiuchenly.comicparse.Bean.ComicBookInfo;

import java.util.ArrayList;
import java.util.List;

public class DBConnect extends SQLiteOpenHelper {

    public DBConnect(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private SQLiteDatabase mDataBase = null;

    @Override
    public void onCreate(SQLiteDatabase db) {
        mDataBase = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static final String TABLE_STUDENT = "students";

    //创建 students 表的 sql 语句
    private static final String STUDENTS_CREATE_TABLE_SQL = "create table" + TABLE_STUDENT + "("
            + "id integer primary key autoincrement,"
            + "name varchar(20) not null,"
            + "tel_no varchar(11) not null,"
            + "cls_id integer not null"
            + ");";

    private boolean canuse() {
        return mDataBase != null;
    }

    private static String MY_BOOK_LIST = "myBook";
    private static String RECENTLY_WEEK = "recently";

    public void initDB() {
        String schema = "create table if not exists " + RECENTLY_WEEK + "(id integer primary key autoincrement," +
                "BookName TEXT not null," +
                "BookName_Link TEXT not null," +
                "BookName_Pic_Link TEXT not null," +
                "BookName_read_point TEXT not null," +
                "BookName_author TEXT not null);";
        getWritableDatabase().execSQL(schema);
        schema = "create table if not exists " + MY_BOOK_LIST + "(id integer primary key autoincrement," +
                "BookName TEXT not null," +
                "BookName_Link TEXT not null," +
                "BookName_Pic_Link TEXT not null," +
                "BookName_read_point TEXT not null," +
                "BookName_author TEXT not null);";
        getWritableDatabase().execSQL(schema);
    }

    public void RECENTLY_INSERT(ComicBookInfo.ComicBookInfo_Recently book) {
        INSERT(book, RECENTLY_WEEK);
    }

    public void LOCALBOOK_INSERT(ComicBookInfo.ComicBookInfo_Recently book) {
        INSERT(book, MY_BOOK_LIST);
    }

    public String LOCALBOOK_GET_READ_POSITION(String bookName) {
        Cursor cur = hasKey_Ex(RECENTLY_WEEK, "BookName", bookName);
        if (cur.moveToFirst()) return cur.getString(4);
        else return "还没有看哦";
    }

    public void INSERT(ComicBookInfo.ComicBookInfo_Recently book, String tbName) {
        if (hasKey(tbName, "BookName", book.BookName)) {
            //updatePoint(book.BookName, book.BookName_read_point);
            System.out.println("数据存在，不重复插入。");
            return;
        }
        ContentValues values = new ContentValues();
        values.put("BookName", book.BookName);
        values.put("BookName_Link", book.BookName_Link);
        values.put("BookName_Pic_Link", book.BookName_Pic_Link);
        values.put("BookName_read_point", book.BookName_read_point);
        values.put("BookName_author", book.author);
        getWritableDatabase().insert(tbName, null, values);
    }

    private boolean hasKey(String TBName, String key, String value) {
        String s = "select * from " + TBName + " where " + key + "='" + value + "';";
        Cursor a = getReadableDatabase().rawQuery(s, null);
        return a.moveToFirst();
    }

    private Cursor hasKey_Ex(String TBName, String key, String value) {
        String s = "select * from " + TBName + " where " + key + "='" + value + "';";
        Cursor a = getReadableDatabase().rawQuery(s, null);
        a.moveToFirst();
        return a;
    }

    public void updatePoint(String Bookname, String point) {
        String s = "UPDATE recently SET BookName_read_point = '" + point + "' WHERE BookName = '" + Bookname + "';";
        getWritableDatabase().execSQL(s);

    }

    public List<ComicBookInfo.ComicBookInfo_Recently> RECENTLY_GET_ALL() {
        return GET_ALL(RECENTLY_WEEK);
    }

    private List<ComicBookInfo.ComicBookInfo_Recently> GET_ALL(String tbName) {
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + tbName + ";", null);
        List<ComicBookInfo.ComicBookInfo_Recently> list = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                ComicBookInfo.ComicBookInfo_Recently a = new ComicBookInfo.ComicBookInfo_Recently();
                a.BookName = c.getString(1);
                a.BookName_Link = c.getString(2);
                a.BookName_Pic_Link = c.getString(3);
                a.BookName_read_point = c.getString(4);
                a.author = c.getString(5);
                list.add(a);
                if (c.isLast())
                    break;
            } while (c.moveToNext());
        }
        return list;
    }

    //获取所有本地书架
    public List<ComicBookInfo.ComicBookInfo_Recently> LOCALBOOK_GET_ALL() {
        return GET_ALL(MY_BOOK_LIST);
    }

}