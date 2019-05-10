package com.example.lenovo.sweprojectothesis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
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

public class VirtualClassroomActivity extends AppCompatActivity {

    CardView card_searchvc,card_requestedvc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_classroom);
        CoursesActivity.getToolbar(VirtualClassroomActivity.this,"Virtual Classroom");
        card_searchvc=findViewById(R.id.card_searchforvc);
        card_requestedvc=findViewById(R.id.card_requestedvclist);
        card_searchvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VirtualClassroomActivity.this,SearchVirtualClassroomActivity.class));
            }
        });

        card_requestedvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VirtualClassroomActivity.this,RequestedVirtualClassroomListActivity.class));
            }
        });
    }

}
