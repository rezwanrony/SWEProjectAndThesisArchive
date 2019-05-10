package com.example.lenovo.sweprojectothesis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class CreatedClassroomListActivity extends AppCompatActivity {

    List<CreatedClassroom> classroomlist;
    SQLiteHandler db;
    GridView lv_classroomlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_classroom_list);
        lv_classroomlist =findViewById(R.id.gridview);
        db=new SQLiteHandler(CreatedClassroomListActivity.this);
        classroomlist =new ArrayList<CreatedClassroom>();
        CoursesActivity.getToolbar(CreatedClassroomListActivity.this,"Virtual classroom list");
        getClassroomList();
    }

    private void getClassroomList(){

        final ProgressDialog dialog=PDialog.showDialog(CreatedClassroomListActivity.this);
        List<Teacher>teacherList=new ArrayList<Teacher>();
        teacherList=db.getTeacherDetails();
        String url=ApiUtils.BASE_URL+"createdvirtualclassroomlist.php?teacher_email="+teacherList.get(0).getPhone();
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
                                    int classroom_id=jsonObject.getInt("id");
                                    String name=jsonObject.getString("name");

                                    classroomlist.add(new CreatedClassroom(classroom_id,name));
                                }

                                CustomClassroomListAdapter customRequestedVCList=new CustomClassroomListAdapter(CreatedClassroomListActivity.this, classroomlist);
                                lv_classroomlist.setAdapter(customRequestedVCList);

                                lv_classroomlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        startActivity(new Intent(CreatedClassroomListActivity.this,AllStudentProjectActivity.class).putExtra("projectliststatus","classroom").putExtra("classroomid",classroomlist.get(i).getId()));
                                    }
                                });

                            }
                            else {
                                Toast.makeText(CreatedClassroomListActivity.this, response.getString("desc"), Toast.LENGTH_SHORT).show();
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
