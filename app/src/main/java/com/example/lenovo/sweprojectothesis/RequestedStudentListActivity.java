package com.example.lenovo.sweprojectothesis;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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

public class RequestedStudentListActivity extends AppCompatActivity {

    List<RequestedStudentList>requestedStudentLists;
    SQLiteHandler db;
    ListView lv_requestedstudentlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_student_list);
        lv_requestedstudentlist=findViewById(R.id.lv_requestedstudentlist);
        db=new SQLiteHandler(RequestedStudentListActivity.this);
        requestedStudentLists=new ArrayList<RequestedStudentList>();
        CoursesActivity.getToolbar(RequestedStudentListActivity.this,"Requested student list");
        getRequestedVCList();
    }

    private void getRequestedVCList(){

        final ProgressDialog dialog=PDialog.showDialog(RequestedStudentListActivity.this);
        List<Teacher>teacherList=new ArrayList<Teacher>();
        teacherList=db.getTeacherDetails();
        String url=ApiUtils.BASE_URL+"requestedStudentList.php?teacher_email="+teacherList.get(0).getPhone();
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
                                    String email=jsonObject.getString("email");
                                    String teacher_email=jsonObject.getString("teacher_email");
                                    int classroom_id=jsonObject.getInt("classroom_id");
                                    String classroomname=jsonObject.getString("classroomname");

                                    requestedStudentLists.add(new RequestedStudentList(name,email,teacher_email,classroom_id,classroomname));
                                }

                                CustomRequestedStudentList customRequestedVCList=new CustomRequestedStudentList(RequestedStudentListActivity.this,requestedStudentLists);
                                lv_requestedstudentlist.setAdapter(customRequestedVCList);

                            }
                            else {
                                Toast.makeText(RequestedStudentListActivity.this, response.getString("desc"), Toast.LENGTH_SHORT).show();
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
