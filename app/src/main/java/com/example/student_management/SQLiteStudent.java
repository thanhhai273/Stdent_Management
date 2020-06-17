package com.example.student_management;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class SQLiteStudent {
    final String DATEBASE_NAME = "my_database.sqlite";
    SQLiteDatabase database;

    public List<Student> findAll(Activity activity){
        List<Student> listAll = new ArrayList<>();
        database = Database.initDatabase(activity, DATEBASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM student;", null);

        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            Student s = convertCursorToStudent(cursor);
            listAll.add(s);
        }
        return listAll;
    }

    public Student findOne(Activity activity, int code){
        Student s = null;
        database = Database.initDatabase(activity, DATEBASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM student WHERE code = ?", new String [] {code + ""});
        if(cursor.moveToFirst()){
            s = convertCursorToStudent(cursor);
        }
        return s;
    }

    public void update(Activity activity, Student s){
        database = Database.initDatabase(activity, DATEBASE_NAME);
        database.update("student", getContentStudent(s), "code = ?", new String[]{s.getCode()+""});
    }

    public void insert(Activity activity, Student s){
        database = Database.initDatabase(activity, DATEBASE_NAME);
        database.insert("student",null, getContentStudent(s));
    }



    public  void delete(int code_id, Activity context){
        database = Database.initDatabase(context, DATEBASE_NAME);
        database.delete("student", "code = ?", new String []{code_id +""});
    }

    private Student convertCursorToStudent(Cursor cursor){
        return new Student(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
    }
    private ContentValues getContentStudent(Student s){
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", s.getCode());
        contentValues.put("name", s.getName());
        contentValues.put("date", s.getDate());
        contentValues.put("email", s.getEmail());
        contentValues.put("address", s.getAddress());
        return contentValues;
    }
}
