package com.example.lenovo.sweprojectothesis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sweprojectothesis";

    // Login table name
    private static final String TABLE_STUDENT = "student";
    private static final String TABLE_TEACHER = "teacher";

    // Login Table Columns names
    private static final String KEY_STUDENTID = "studdentid";
    private static final String KEY_STUDENTNAME = "studentname";
    private static final String KEY_STUDENTEMAIL = "studentemail";
    private static final String KEY_STUDENTPHONE = "studentphone";
    private static final String KEY_STUDENTADDRESS = "studentaddress";

    private static final String KEY_TEACHERID = "teacherid";
    private static final String KEY_TEACHERNAME = "teachername";
    private static final String KEY_TEACHEREMAIL = "teacheremail";
    private static final String KEY_TEACHERPHONE = "teacherphone";



    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
                + KEY_STUDENTID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_STUDENTNAME + " TEXT," + KEY_STUDENTEMAIL + " TEXT UNIQUE," + KEY_STUDENTPHONE + " TEXT," + KEY_STUDENTADDRESS + " TEXT " + ")";

        String CREATE_TEACHER_TABLE = "CREATE TABLE " + TABLE_TEACHER + "("
                + KEY_TEACHERID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TEACHERNAME + " TEXT," + KEY_TEACHEREMAIL + " TEXT UNIQUE," + KEY_TEACHERPHONE + " TEXT " + ")";

        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_TEACHER_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */


    public void addStudent(String name,String email,String phone,String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STUDENTNAME, name);//phone
        values.put(KEY_STUDENTPHONE, phone);//area
        values.put(KEY_STUDENTEMAIL, email);//permission
        values.put(KEY_STUDENTADDRESS, address);
        // Created At

        // Inserting Row
        long dataid = db.insert(TABLE_STUDENT, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New student inserted into sqlite: " + dataid);
    }

    public void addTeacher(String name,String email,String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEACHERNAME, name);//phone
        values.put(KEY_TEACHEREMAIL, phone);//area
        values.put(KEY_TEACHERPHONE, email);//permission
        // Created At

        // Inserting Row
        long dataid = db.insert(TABLE_TEACHER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New teacher inserted into sqlite: " + dataid);
    }


    /**
     * Getting user data from database
     * */

    public ArrayList<Student> getStudentDetails() {
        ArrayList<Student>studentArrayList=new ArrayList<Student>();
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                Student student=new Student();
                student.setId(cursor.getInt(cursor.getColumnIndex(KEY_STUDENTID)));
                student.setName(cursor.getString(cursor.getColumnIndex(KEY_STUDENTNAME)));
                student.setEmail(cursor.getString(cursor.getColumnIndex(KEY_STUDENTEMAIL)));
                student.setPhone(cursor.getString(cursor.getColumnIndex(KEY_STUDENTPHONE)));
                student.setAddress(cursor.getString(cursor.getColumnIndex(KEY_STUDENTADDRESS)));
                studentArrayList.add(student);
            }
        }
        return studentArrayList;
    }

    public ArrayList<Teacher> getTeacherDetails() {
        ArrayList<Teacher>teacherArrayList=new ArrayList<Teacher>();
        String selectQuery = "SELECT  * FROM " + TABLE_TEACHER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                Teacher teacher=new Teacher();
                teacher.setId(cursor.getInt(cursor.getColumnIndex(KEY_TEACHERID)));
                teacher.setName(cursor.getString(cursor.getColumnIndex(KEY_TEACHERNAME)));
                teacher.setEmail(cursor.getString(cursor.getColumnIndex(KEY_TEACHEREMAIL)));
                teacher.setPhone(cursor.getString(cursor.getColumnIndex(KEY_TEACHERPHONE)));
                teacherArrayList.add(teacher);
            }
        }
        return teacherArrayList;
    }



    public void deleteStudent() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_STUDENT, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void deleteTeacher() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_TEACHER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }






   /* public void UpdateUsers(String phone, String email, String address, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        //update user data
        ContentValues cv = new ContentValues();
        cv.put("phone",phone); //These Fields should be your String values of actual column names
        cv.put("email",email);
        cv.put("address",address);

        db.update(TABLE_STUDENT, cv, String.format("%s = ?", "userid"), new String[]{uid});

        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void UpdateAlert(Alert alert,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //update user data
        ContentValues cv = new ContentValues();
        // Name
        cv.put(KEY_ALERTNAME, alert.getAlertname()); // Name
        cv.put(KEY_ALERTTIME, alert.getAlerttime()); // Email
        cv.put(KEY_AMORPM, alert.getAmorpm()); // Email//permission

        db.update(TABLE_TEACHER, cv, KEY_STUDENTID + " = ? ", new String[]{ String.valueOf(id) });

        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }*/






}
