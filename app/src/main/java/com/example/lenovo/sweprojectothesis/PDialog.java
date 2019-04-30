package com.example.lenovo.sweprojectothesis;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;

public class PDialog{

    public static ProgressDialog showDialog(Context context){
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage("Please wait...");
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        m_Dialog.show();
        return m_Dialog;
    }


}