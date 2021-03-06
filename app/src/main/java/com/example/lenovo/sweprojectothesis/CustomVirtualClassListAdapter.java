package com.example.lenovo.sweprojectothesis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class CustomVirtualClassListAdapter extends BaseAdapter {
    Context context;
    List<VirtualClassroom> studentProjectList;
    SQLiteHandler db;

    public CustomVirtualClassListAdapter(Context context, List<VirtualClassroom> studentProjectList) {
        this.context = context;
        this.studentProjectList = studentProjectList;
    }

    @Override
    public int getCount() {
        return studentProjectList.size();
    }

    @Override
    public Object getItem(int i) {
        return studentProjectList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.custom_vc_list,viewGroup,false);

        }

        db=new SQLiteHandler(context);

        TextView tv_teacherinit=(TextView)view.findViewById(R.id.tv_name_vc_a);
        tv_teacherinit.setText(studentProjectList.get(i).getName());

        Button btn_login=(Button)view.findViewById(R.id.btn_request_vc);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requesttojoin(studentProjectList.get(i).getId(),studentProjectList.get(i).getCreatedby());
            }
        });
        return view;
    }

    private void requesttojoin(int id,String teacher_email){

        List<Student>studentList=new ArrayList<Student>();
        studentList=db.getStudentDetails();
        final ProgressDialog dialog=PDialog.showDialog(context);
        String url=ApiUtils.BASE_URL+"joinRequest.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(context);


        Map postParam= new HashMap();
        postParam.put("student_email", studentList.get(0).getEmail());
        postParam.put("teacher_email", teacher_email);
        postParam.put("classroom_id", id);


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
                                Toast.makeText(context, response.getString("msg"), Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context,StudentDashboardActivity.class));
                            }
                            else {
                                Toast.makeText(context, response.getString("msg"), Toast.LENGTH_SHORT).show();
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
