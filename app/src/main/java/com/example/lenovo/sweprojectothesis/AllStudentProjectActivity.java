package com.example.lenovo.sweprojectothesis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllStudentProjectActivity extends AppCompatActivity {
    ListView lv_students;
    List<AllStudentProject> studentProjects;
    SQLiteHandler db;
    TextView tv_nodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student_project);
        lv_students=(ListView)findViewById(R.id.lv_listofprojectteacher);
        tv_nodata=findViewById(R.id.tv_nodata_allstudentproject);
        studentProjects=new ArrayList<AllStudentProject>();
        db=new SQLiteHandler(AllStudentProjectActivity.this);

        CoursesActivity.getToolbar(AllStudentProjectActivity.this,"Previous Project/Thesis List");

        if (getIntent().getStringExtra("projectliststatus").equals("student")){
            getStudentProjectList();
        }
        else if (getIntent().getStringExtra("projectliststatus").equals("classroom")){
            getClassroomProjectList();
        }
        else {
            getProjectList();

        }
    }

    private void getProjectList(){

        final ProgressDialog dialog=PDialog.showDialog(AllStudentProjectActivity.this);
        String url=ApiUtils.BASE_URL+"projectlist.php";
        final String TAG="Volley Response";
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

                                JSONArray jsonArray=response.getJSONArray("data");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String name=jsonObject.getString("name");
                                    String programming_language=jsonObject.getString("programming_language");
                                    String semester=jsonObject.getString("semester");
                                    String year=jsonObject.getString("year");
                                    String project_owner=jsonObject.getString("project_owner");
                                    String project_report = jsonObject.getString("project_report");
                                    String githublink = jsonObject.getString("githublink");
                                    String description = jsonObject.getString("description");
                                    studentProjects.add(new AllStudentProject(name, project_owner, programming_language, semester, year,description,project_report,githublink));
                                }

                                CustomAllStudentsProjectListAdapter customAllStudentsProjectListAdapter = new CustomAllStudentsProjectListAdapter(AllStudentProjectActivity.this, studentProjects);
                                lv_students.setAdapter(customAllStudentsProjectListAdapter);

                                lv_students.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Intent intent = new Intent(AllStudentProjectActivity.this, ProjectdetailsActivity.class);
                                        intent.putExtra("title",studentProjects.get(i).getProjectname());
                                        intent.putExtra("language",studentProjects.get(i).getLanguage());
                                        intent.putExtra("semester",studentProjects.get(i).getSemester());
                                        intent.putExtra("year",studentProjects.get(i).getYear());
                                        intent.putExtra("owner",studentProjects.get(i).getProjectowner());
                                        intent.putExtra("report",studentProjects.get(i).getProject_report());
                                        intent.putExtra("github",studentProjects.get(i).getGithublink());
                                        intent.putExtra("desc",studentProjects.get(i).getProject_details());
                                        startActivity(intent);
                                    }
                                });
                            }
                            else {
                                Toast.makeText(AllStudentProjectActivity.this, response.getString("desc"), Toast.LENGTH_SHORT).show();
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
    private void getStudentProjectList(){
        int classroom_id=getIntent().getIntExtra("studentvcid",0);
        List<Student> studentList=new ArrayList<Student>();
        studentList=db.getStudentDetails();
        String email=studentList.get(0).getEmail();

        final ProgressDialog dialog=PDialog.showDialog(AllStudentProjectActivity.this);
        String url=ApiUtils.BASE_URL+"studentprojectlist.php?student_email="+email+"&classroom_id="+classroom_id;
        final String TAG="Volley Response";
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

                                JSONArray jsonArray=response.getJSONArray("data");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String name = jsonObject.getString("name");
                                        String programming_language = jsonObject.getString("programming_language");
                                        String semester = jsonObject.getString("semester");
                                        String year = jsonObject.getString("year");
                                        String project_owner = jsonObject.getString("project_owner");
                                        String project_report = jsonObject.getString("project_report");
                                        String githublink = jsonObject.getString("githublink");
                                        String description = jsonObject.getString("description");
                                        studentProjects.add(new AllStudentProject(name, project_owner, programming_language, semester, year,description,project_report,githublink));
                                    }

                                CustomAllStudentsProjectListAdapter customAllStudentsProjectListAdapter = new CustomAllStudentsProjectListAdapter(AllStudentProjectActivity.this, studentProjects);
                                lv_students.setAdapter(customAllStudentsProjectListAdapter);

                                lv_students.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Intent intent = new Intent(AllStudentProjectActivity.this, ProjectdetailsActivity.class);
                                        intent.putExtra("title",studentProjects.get(i).getProjectname());
                                        intent.putExtra("language",studentProjects.get(i).getLanguage());
                                        intent.putExtra("semester",studentProjects.get(i).getSemester());
                                        intent.putExtra("year",studentProjects.get(i).getYear());
                                        intent.putExtra("owner",studentProjects.get(i).getProjectowner());
                                        intent.putExtra("report",studentProjects.get(i).getProject_report());
                                        intent.putExtra("github",studentProjects.get(i).getGithublink());
                                        intent.putExtra("desc",studentProjects.get(i).getProject_details());
                                    }
                                });
                                }

                            else {
                                tv_nodata.setVisibility(View.VISIBLE);
                                Toast.makeText(AllStudentProjectActivity.this, response.getString("desc"), Toast.LENGTH_SHORT).show();
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
    private void getClassroomProjectList(){
        int classroom_id=getIntent().getIntExtra("classroomid",0);
        List<Student> studentList=new ArrayList<Student>();
        studentList=db.getStudentDetails();
        String email=studentList.get(0).getEmail();

        final ProgressDialog dialog=PDialog.showDialog(AllStudentProjectActivity.this);
        String url=ApiUtils.BASE_URL+"studentprojectlist.php?classroom_id="+classroom_id;
        final String TAG="Volley Response";
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

                                JSONArray jsonArray=response.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String name = jsonObject.getString("name");
                                        String programming_language = jsonObject.getString("programming_language");
                                        String semester = jsonObject.getString("semester");
                                        String year = jsonObject.getString("year");
                                        String project_owner = jsonObject.getString("project_owner");
                                        String project_report = jsonObject.getString("project_report");
                                        String githublink = jsonObject.getString("githublink");
                                        String description = jsonObject.getString("description");
                                        studentProjects.add(new AllStudentProject(name, project_owner, programming_language, semester, year,description,project_report,githublink));
                                    }

                                    CustomAllStudentsProjectListAdapter customAllStudentsProjectListAdapter = new CustomAllStudentsProjectListAdapter(AllStudentProjectActivity.this, studentProjects);
                                    lv_students.setAdapter(customAllStudentsProjectListAdapter);

                                    lv_students.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new Intent(AllStudentProjectActivity.this, ProjectdetailsActivity.class);
                                            intent.putExtra("title",studentProjects.get(i).getProjectname());
                                            intent.putExtra("language",studentProjects.get(i).getLanguage());
                                            intent.putExtra("semester",studentProjects.get(i).getSemester());
                                            intent.putExtra("year",studentProjects.get(i).getYear());
                                            intent.putExtra("owner",studentProjects.get(i).getProjectowner());
                                            intent.putExtra("report",studentProjects.get(i).getProject_report());
                                            intent.putExtra("github",studentProjects.get(i).getGithublink());
                                            intent.putExtra("desc",studentProjects.get(i).getProject_details());
                                        }
                                    });




                            }
                            else {
                                tv_nodata.setVisibility(View.VISIBLE);
                                tv_nodata.setText("No data found!!!");
                                Toast.makeText(AllStudentProjectActivity.this, response.getString("desc"), Toast.LENGTH_SHORT).show();
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
