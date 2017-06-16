package com.cn.kin.translator.database;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Kin on 17.6.8.
 */

public class DBUtil {
    /*public static Boolean queryIfItemExist(NotebookDatabaseHelper dbhelper, String queryString){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query("notebook",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                String s = cursor.getString(cursor.getColumnIndex("input"));
                if (queryString.equals(s)){
                    return true;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return false;
    }*/

    public static Boolean queryIfItemIsExist(String queryString){
        List<NoteBook>noteBooks=DataSupport.findAll(NoteBook.class);
        for(NoteBook noteBook:noteBooks){
            String s=noteBook.getInput();
            if(queryString.equals(s)){
                return true;
            }
        }
        return false;
    }
    /*public static void insertValue(NotebookDatabaseHelper dbhelper, ContentValues values){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        db.insert("notebook",null,values);
    }*/

    public static void insertValue(NoteBook book){
        book.save();
    }


    /*public static void deleteValue(NotebookDatabaseHelper dbhelper,String deleteString){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        db.delete("notebook","input = ?",new String[]{deleteString});
    }*/

    public static void deleteValue(String deleteString){
        DataSupport.deleteAll(NoteBook.class,"input=?",deleteString);
    }
}
