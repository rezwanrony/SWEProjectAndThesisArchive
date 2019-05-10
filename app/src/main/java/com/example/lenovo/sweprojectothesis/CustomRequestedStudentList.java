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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomRequestedStudentList extends BaseAdapter {
    Context context;
    List<RequestedStudentList> requestedVCList;
    SQLiteHandler db;

    public CustomRequestedStudentList(Context context, List<RequestedStudentList> requestedVCList) {
        this.context = context;
        this.requestedVCList = requestedVCList;
    }

    @Override
    public int getCount() {
        return requestedVCList.size();
    }

    @Override
    public Object getItem(int i) {
        return requestedVCList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.custom_requested_studentlist,viewGroup,false);

        }

        db=new SQLiteHandler(context);

        TextView classroomname=view.findViewById(R.id.tv_classroomname_requestedstudentlist);
        TextView vname=view.findViewById(R.id.tv_name_requestedstudentlist);
        Button btn_enter=view.findViewById(R.id.btn_approve_requestedstudentlist);

        vname.setText(requestedVCList.get(i).getEmail());
        classroomname.setText(requestedVCList.get(i).getClassroomname());

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approverequest(requestedVCList.get(i).getClassroom_id(),requestedVCList.get(i).getEmail());
            }
        });
        return view;
    }

    private void approverequest(int id,String student_email){

        List<Teacher>teacherList=new ArrayList<Teacher>();
        teacherList=db.getTeacherDetails();
        final ProgressDialog dialog=PDialog.showDialog(context);
        String url=ApiUtils.BASE_URL+"approvestudentrequest.php";
        final String TAG="Volley Response";

        RequestQueue queue = Volley.newRequestQueue(context);


        Map postParam= new HashMap();
        postParam.put("student_email",student_email);
        postParam.put("teacher_email", teacherList.get(0).getPhone());
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
                                context.startActivity(new Intent(context,TeacherDashboard.class));
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
