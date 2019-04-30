package com.example.lenovo.sweprojectothesis;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;

public class CustomAddAssignmentsPopup extends Dialog {

    public Activity activity;


    public CustomAddAssignmentsPopup(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_add_assignments_popup);
        getWindow().setGravity(Gravity.CENTER);




    }
}
