package com.example.lenovo.sweprojectothesis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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

public class AdminDashboardActivity extends AppCompatActivity {

    CardView card_addcourses,card_addprojects,card_seecourselist,card_seeprojectlist;
    TextView tv_name,tv_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        card_addcourses=findViewById(R.id.card_addcourse);
        card_addprojects=findViewById(R.id.card_addpreviousproject);
        card_seecourselist=findViewById(R.id.card_seecourselistadmin);
        card_seeprojectlist=findViewById(R.id.card_seeprojectlist_admin);
        tv_name=findViewById(R.id.tv_proname_admindashboard);
        tv_email=findViewById(R.id.tv_email_admindashboard);
        getAdmindata();

        card_addcourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAddCoursesPopup dialog = new CustomAddCoursesPopup(AdminDashboardActivity.this);
                WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
                lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT; // this is where the magic happens
                lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                dialog.getWindow().setAttributes(lWindowParams);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });

        card_addprojects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,AddProjectActivity.class).putExtra("addstatus","admin"));
            }
        });

        card_seecourselist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,CoursesActivity.class));
            }
        });

        card_seeprojectlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,AllStudentProjectActivity.class).putExtra("projectliststatus","classroom").putExtra("classroomid",-1));
            }
        });
    }

    private void getAdmindata(){

        final ProgressDialog dialog=PDialog.showDialog(AdminDashboardActivity.this);
        String url=ApiUtils.BASE_URL+"admindata.php?email="+MainActivity.getDefaults("adminemail",AdminDashboardActivity.this);
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
                                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                                    String name=jsonObject.getString("name");
                                    String email=jsonObject.getString("email");
                                    tv_name.setText(name);
                                    tv_email.setText(email);

                            }
                            else {
                                Toast.makeText(AdminDashboardActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
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
