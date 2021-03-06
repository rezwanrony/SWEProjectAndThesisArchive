package com.example.lenovo.sweprojectothesis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateVirtualClassroomActivity extends AppCompatActivity {

    Spinner spinner_semester;
    Spinner spinner_course;
    EditText et_section;
    Button btn_create;
    List<Course> courses;
    int course_id;
    String course;
    String semester;
    SQLiteHandler db;
    ArrayList<String>semesterlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_virtual_classroom);
        spinner_semester=(Spinner)findViewById(R.id.spinner_semester_vc);
        spinner_course=(Spinner)findViewById(R.id.spinner_course_vc);
        et_section=findViewById(R.id.et_section_vc);
        courses =new ArrayList<Course>();
        db=new SQLiteHandler(CreateVirtualClassroomActivity.this);
        TextView tv_title=findViewById(R.id.toolbartitle);
        tv_title.setText("Create Virtual classroom");

        semesterlist = new ArrayList<String>();

        int year=Calendar.getInstance().get(Calendar.YEAR);
        semesterlist.add("Spring "+String.valueOf(year));
        semesterlist.add("Summer "+String.valueOf(year));
        semesterlist.add("Fall "+String.valueOf(year));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateVirtualClassroomActivity.this,
                R.layout.spinner_item_black,semesterlist);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
// Apply the adapter to the spinner
        spinner_semester.setAdapter(adapter);

        spinner_semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    semester="Spring";
                }
                else if (i==1){
                    semester="Summer";
                }
                else {
                    semester="Fall";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                semester="Spring";
            }
        });

        Button btn_login=(Button)findViewById(R.id.btn_create_vc);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateVirtualClassroomActivity.this,AllStudentProjectActivity.class));
            }
        });

        getCourses();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createClassroom(semester,course_id,course,et_section.getText().toString().trim());
            }
        });


    }


    private void createClassroom(String semester,int course_id,String course,String section){

        int year=Calendar.getInstance().get(Calendar.YEAR);
        String name=course+" "+semester+" "+String.valueOf(year)+" "+section;
        List<Teacher>teacherList=new ArrayList<Teacher>();
        teacherList=db.getTeacherDetails();

        final ProgressDialog dialog=PDialog.showDialog(CreateVirtualClassroomActivity.this);
        String url=ApiUtils.BASE_URL+"virtualClassCreate.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(this);
        Map postParam= new HashMap();
        postParam.put("name", name);
        postParam.put("createdby",teacherList.get(0).getPhone());
        postParam.put("course_id", course_id);
        postParam.put("semester", semester);
        postParam.put("section", section);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            int status=response.getInt("status");
                            if (status==1) {
                                Toast.makeText(CreateVirtualClassroomActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CreateVirtualClassroomActivity.this,TeacherDashboard.class));
                            }
                            else {
                                Toast.makeText(CreateVirtualClassroomActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        jsonObjReq.setTag(TAG);
        // Adding request to request queue
        queue.add(jsonObjReq);
    }

    private void getCourses(){

        final ProgressDialog dialog=PDialog.showDialog(CreateVirtualClassroomActivity.this);
        String url=ApiUtils.BASE_URL+"course.php";
        final String TAG="Volley Response";
        final List<String> courseList=new ArrayList<String>();
        RequestQueue queue = Volley.newRequestQueue(this);




        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();
                        Log.d(TAG, response.toString());
                        try {
                            int status=response.getInt("status");
                            if (status==1) {
                                JSONArray jsonArray=response.getJSONArray("course");
                                for (int i=0;i<jsonArray.length();i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    int id=jsonObject.getInt("id");
                                    String course_code = jsonObject.getString("course_code");
                                    String course_name = jsonObject.getString("course_name");
                                    courses.add(new Course(id,course_code,course_name));
                                }

                                for (int i=0;i<courses.size();i++){
                                    courseList.add(courses.get(i).getCoursename());
                                }

                                ArrayAdapter<String> adaptertwo = new ArrayAdapter<String>(  CreateVirtualClassroomActivity.this,
                                        R.layout.spinner_item_black,
                                        courseList);
// Specify the layout to use when the list of choices appears
// Apply the adapter to the spinner
                                adaptertwo.setDropDownViewResource(android.R.layout.simple_list_item_1);
                                spinner_course.setAdapter(adaptertwo);

                                spinner_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        course_id=courses.get(i).getId();
                                        course=courses.get(i).getCoursename();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        course_id=courses.get(0).getId();
                                        course=courses.get(0).getCoursename();
                                    }
                                });

                            }
                            else {
                                Toast.makeText(CreateVirtualClassroomActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        jsonObjReq.setTag(TAG);
        // Adding request to request queue
        queue.add(jsonObjReq);
    }


}
