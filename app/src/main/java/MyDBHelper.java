import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sam750.countel.R;

public class MyDBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase myDB;
    private static final String DB_NAME = "sam_elcount.db";
    final String DB_NAME1 = "sam_elcount1"; // тип счетчика

    // поля таблицы
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "type_c";

    private String type_c;
    final String DB_NAME2 = "sam_elcount2"; // счетчики
    final String DB_NAME3 = "sam_elcount3"; // адреса
    final String DB_NAME4 = "sam_elcount4"; // данные
    Context mContext;

    public MyDBHelper(Context context, int dbVer){
        super(context, DB_NAME, null, dbVer);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String add_type_C = mContext.getResources().getString(R.string.Add_type_count);

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_NAME1 +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " text )");  //название счетчика

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_NAME2 +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " type_c integer, " + //таблица1 _id счетчика
                " addr_c integer, " + //таблица3 _id адреса
                " det_c  text )" );  //доп. данные по счетчикам "хол/гор..."

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_NAME3 +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                " sity_c   text " +  //город
//                " street_c text " +  //улица
//                " house_c  text " +  //дом
//                " flat_c   text " +  //квартира
                " fulla_c   text )"); //полное название

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DB_NAME4 +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " count_c  integer, " + //счетчик
                " year_c   integer, " + //год
                " month_c  integer, " + //месяц
                " data_c     text )");  //данные
//        db.close();
// первоначальные значения
//        dbInsert(DB_NAME1, add_type_C);
//        dbInsert(DB_NAME1, "Добавить вид счетчика");
        dbInsertIn(db, DB_NAME1, "Электрический");
        dbInsertIn(db, DB_NAME1, "Водный");
        dbInsertIn(db, DB_NAME1, "Газовый");

        dbInsertIn(db, DB_NAME2, "Добавить счетчики");
//        dbInsert(DB_NAME3, "Добавить адрес");
//        dbInsert(DB_NAME3, "Внести данные");
//        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //проверяете какая версия сейчас и делаете апдейт
//        onCreate(db);
    }


    public Cursor getAllCounts () {
        String qry = "SELECT * FROM " + DB_NAME1;
//        Log.d("sam750.elc", "before getReadable");
        SQLiteDatabase db = this.getReadableDatabase();
//        Log.d("sam750.elc", "after getReadable");
        Cursor cursor = db.rawQuery(qry , null);
        return cursor;
    }

    public List<String> getCount1 () {

        List<String> lType_c = new ArrayList<String>();
        String qry = "SELECT * FROM " + DB_NAME1;

//        Log.d("sam750.elc", "before getReadable");
        SQLiteDatabase db = this.getReadableDatabase();
//        Log.d("sam750.elc", "after getReadable");
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                String type_c = cursor.getString(
                        cursor.getColumnIndexOrThrow("type_c")
                );
//                lType_c.add(type_c);
            } while (cursor.moveToNext());
        }
        db.close();
        return lType_c;
    }

    public void dbInsert (String pDB_NAME, String type_c1) {
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "INSERT INTO " + pDB_NAME +
                " ( TYPE_C ) VALUES ('" + type_c1 + "')";
        db.execSQL(qry);
        db.close();
    }

    public void dbInsertIn (SQLiteDatabase db, String pDB_NAME, String type_c1) {
//        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "INSERT INTO " + pDB_NAME +
                " ( TYPE_C ) VALUES ('" + type_c1 + "')";
        db.execSQL(qry);
    }
}
