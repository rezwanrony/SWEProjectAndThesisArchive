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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestedVirtualClassroomListActivity extends AppCompatActivity {

    ListView lv_requestedvc;
    List<RequestedVC>requestedVCList;
    SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_virtual_classroom_list);
        CoursesActivity.getToolbar(RequestedVirtualClassroomListActivity.this,"Requested VC List");
        lv_requestedvc=findViewById(R.id.lv_requestedvc);
        requestedVCList=new ArrayList<RequestedVC>();
        db=new SQLiteHandler(RequestedVirtualClassroomListActivity.this);
        getRequestedVCList();
    }

    private void getRequestedVCList(){

        final ProgressDialog dialog=PDialog.showDialog(RequestedVirtualClassroomListActivity.this);
        List<Student>studentList=new ArrayList<Student>();
        studentList=db.getStudentDetails();
        String url=ApiUtils.BASE_URL+"requestedVirtualClassroomList.php?student_email="+studentList.get(0).getEmail();
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
                                    int id=jsonObject.getInt("id");
                                    String name=jsonObject.getString("name");
                                    String createdby=jsonObject.getString("createdby");
                                    String student_email=jsonObject.getString("student_email");
                                    int arrpovestatus=jsonObject.getInt("status");
                                    requestedVCList.add(new RequestedVC(id,name,createdby,student_email,arrpovestatus));
                                }

                                CustomRequestedVCList customRequestedVCList=new CustomRequestedVCList(RequestedVirtualClassroomListActivity.this,requestedVCList);
                                lv_requestedvc.setAdapter(customRequestedVCList);

                            }
                            else {
                                Toast.makeText(RequestedVirtualClassroomListActivity.this, response.getString("desc"), Toast.LENGTH_SHORT).show();
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
