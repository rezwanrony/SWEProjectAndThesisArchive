package com.example.lenovo.sweprojectothesis;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAddCoursesPopup extends Dialog {

    public Activity activity;
    EditText et_coursecode;
    EditText et_coursename;
    Button btn_add,btn_cancel;

    public CustomAddCoursesPopup(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_addcourses_popup);
        getWindow().setGravity(Gravity.CENTER);
        et_coursecode=findViewById(R.id.et_coursecode_addcourse);
        et_coursename=findViewById(R.id.et_coursetitle_addcourse);
        btn_add=findViewById(R.id.btn_add_addcourse);
        btn_cancel=findViewById(R.id.btn_cancel_injurypopup);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addcourses();
            }
        });




    }

    private void Addcourses(){
        final ProgressDialog dialog=PDialog.showDialog(activity);
        String url=ApiUtils.BASE_URL+"virtualClassCreate.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(activity);
        Map<String,String> postParam= new HashMap<String,String>();
        postParam.put("course_code", et_coursecode.getText().toString());
        postParam.put("course_name",et_coursename.getText().toString());


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
                                Toast.makeText(activity, response.getString("msg"), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                            else {
                                Toast.makeText(activity, response.getString("msg"), Toast.LENGTH_SHORT).show();
                                onBackPressed();
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
                onBackPressed();
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
}
