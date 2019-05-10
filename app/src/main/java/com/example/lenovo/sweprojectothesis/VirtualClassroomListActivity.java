package com.example.lenovo.sweprojectothesis;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VirtualClassroomListActivity extends AppCompatActivity {

    ListView lv_classroom;
    String semester,secction;
    int course_id;
    List<VirtualClassroom> virtualClassroomList;
    List<VirtualClassroomName> virtualClassroomNameList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_classroom_list);
        TextView tv_title=findViewById(R.id.toolbartitle);
        tv_title.setText("Virtual classroom List");
        lv_classroom=findViewById(R.id.lv_virtualclassroom);
        virtualClassroomList =new ArrayList<VirtualClassroom>();
        virtualClassroomNameList =new ArrayList<VirtualClassroomName>();

        semester=getIntent().getStringExtra("semester");
        course_id=getIntent().getIntExtra("course_id",0);
        secction=getIntent().getStringExtra("section");

        getCourses(secction,semester,course_id);


    }

    private void getCourses(final String sec, String sem, int c_id){

        final ProgressDialog dialog=PDialog.showDialog(VirtualClassroomListActivity.this);
        String url=ApiUtils.BASE_URL+"virtualClass/allVirtualClass.php?section="+sec+"&semester="+sem+"&course_id="+c_id;
        final String TAG="Volley Response";
        final List<String>courseList=new ArrayList<String>();
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
                                JSONObject jsonArray=response.getJSONObject("data");
                                    int id = jsonArray.getInt("id");
                                    String sections = jsonArray.getString("section");
                                    String semesters = jsonArray.getString("semester");
                                    String course_names = jsonArray.getString("course_name");
                                    String createdby = jsonArray.getString("createdby");
                                    virtualClassroomNameList.add(new VirtualClassroomName(sections,semesters,course_names));


                                int year=Calendar.getInstance().get(Calendar.YEAR);
                                String name=virtualClassroomNameList.get(0).getCourse_name();
                                String semester=virtualClassroomNameList.get(0).getSemester();
                                String section=virtualClassroomNameList.get(0).getSection();
                                String vname=name+" "+semester+" "+String.valueOf(year)+" "+section;
                                    virtualClassroomList.add(new VirtualClassroom(id,vname,createdby));

                                CustomVirtualClassListAdapter customVirtualClassListAdapter=new CustomVirtualClassListAdapter(VirtualClassroomListActivity.this,virtualClassroomList);
                                lv_classroom.setAdapter(customVirtualClassListAdapter);


                            }
                            else {
                                Toast.makeText(VirtualClassroomListActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
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
