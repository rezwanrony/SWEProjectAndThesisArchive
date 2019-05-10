package com.example.lenovo.sweprojectothesis;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectStatsActivity extends AppCompatActivity {

    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    List<Projectstat>projectstatList;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_stats);
        chart = (BarChart) findViewById(R.id.chart1);
        projectstatList=new ArrayList<Projectstat>();
        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        getProjectStats();

    }

    private void getProjectStats(){

        final ProgressDialog dialog=PDialog.showDialog(ProjectStatsActivity.this);
        String url=ApiUtils.BASE_URL+"projectstatlist.php";
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
                                    String programming_language=jsonObject.getString("programming_language");
                                    int count=jsonObject.getInt("count");
                                    projectstatList.add(new Projectstat(programming_language,count));
                                }

                                for (int i=0;i<projectstatList.size();i++) {
                                    BARENTRY.add(new BarEntry(Float.parseFloat(String.valueOf(projectstatList.get(i).getCount())), i));
                                }

                                for (int i=0;i<projectstatList.size();i++) {
                                    BarEntryLabels.add(projectstatList.get(i).getProgramming_language());
                                }

                                Bardataset = new BarDataSet(BARENTRY, "Programming language used");

                                BARDATA = new BarData(BarEntryLabels, Bardataset);

                                Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);


                                chart.setData(BARDATA);

                                chart.animateY(2000);



                            }
                            else {
                                Toast.makeText(ProjectStatsActivity.this, response.getString("desc"), Toast.LENGTH_SHORT).show();
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
