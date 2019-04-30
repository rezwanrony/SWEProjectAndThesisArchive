package com.example.lenovo.sweprojectothesis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoursesActivity extends AppCompatActivity {

    ListView lv_students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        TextView tv_title=findViewById(R.id.toolbartitle);
        tv_title.setText("Project/Thesis Course List");
        lv_students=(ListView)findViewById(R.id.lv_listofprojectstudent);

        getCourses();
    }

    private void getCourses(){

        final ProgressDialog dialog=PDialog.showDialog(CoursesActivity.this);
        String url=ApiUtils.BASE_URL+"course.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(this);

        final List<Course> courses =new ArrayList<Course>();



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
                                    String course_code = jsonObject.getString("course_code");
                                    String course_name = jsonObject.getString("course_name");
                                    courses.add(new Course(course_code,course_name));
                                }

                                CustomCourseListAdapter customCourseListAdapter =new CustomCourseListAdapter(CoursesActivity.this, courses);

                                lv_students.setAdapter(customCourseListAdapter);
                            }
                            else {
                                Toast.makeText(CoursesActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
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
