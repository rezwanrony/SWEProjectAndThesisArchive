package com.example.lenovo.sweprojectothesis;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProjectActivity extends AppCompatActivity {

    //Pdf request code
    private int PICK_PDF_REQUEST = 1;
    private Uri filePath;
    String path;
    byte[] decodedString;
    String encoded;
    ApiInterface apiInterface;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    EditText et_title,et_desc,et_language,et_developedby,et_githublink;
    Spinner spinner_semester;
    ArrayList<String>semesterlist;
    String semester;
    Button btn_addreport,btn_add,btn_cancel;
    TextView tv_filename;
    ProgressDialog dialog;
    SQLiteHandler db;
    LinearLayout ll_developedby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addprojectactivity);
        et_title=findViewById(R.id.et_title_addproject);
        et_language=findViewById(R.id.et_programminglanguage_addproject);
        ll_developedby=findViewById(R.id.ll_developedby);
        et_developedby=findViewById(R.id.et_developedby_addproject);
        et_desc=findViewById(R.id.et_projectdetails_addproject);
        et_githublink=findViewById(R.id.et_githublink_addproject);
        btn_addreport=findViewById(R.id.btn_addprojectreport);
        btn_add=findViewById(R.id.btn_add_addreport);
        semesterlist=new ArrayList<String>();
        db=new SQLiteHandler(AddProjectActivity.this);
        spinner_semester=(Spinner)findViewById(R.id.spinner_semester_addprojects);
        tv_filename=findViewById(R.id.tv_projectreportname_addproject);
        apiInterface=ApiUtils.getAPIService();
        dialog=new ProgressDialog(AddProjectActivity.this);
        CoursesActivity.getToolbar(AddProjectActivity.this,"Add Projects");
        int year=Calendar.getInstance().get(Calendar.YEAR);
        semesterlist.add("Spring "+String.valueOf(year));
        semesterlist.add("Summer "+String.valueOf(year));
        semesterlist.add("Fall "+String.valueOf(year));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddProjectActivity.this,
                R.layout.spinner_item_black,semesterlist);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
// Apply the adapter to the spinner
        spinner_semester.setAdapter(adapter);

        spinner_semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    semester="Spring";
                }
                else if (i==1){
                    semester="Summer";
                }
                else {
                    semester="Fall";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                semester="Spring";
            }
        });

        btn_addreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             showFileChooser();
            }
        });

        if (getIntent().getStringExtra("addstatus").equals("student")){
            ll_developedby.setVisibility(View.GONE);
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File file=new File(path);
                int file_size = Integer.parseInt(String.valueOf(file.length()/1024));

                if (file_size>5120){
                    Toast.makeText(AddProjectActivity.this, "The file size must not be more than 5 MB", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (getIntent().getStringExtra("addstatus").equals("student")){
                        ProjectReportStudentUpload(path, semester);
                    }
                    else {
                        ProjectReportUpload(path, semester);
                    }
                }


           /*         if (validate()==true){
                        if (!path.isEmpty()){
                        ProjectReportUpload(path,semester);
                        }
                        else {
                            Toast.makeText(AddProjectActivity.this, "You must add a pdf file", Toast.LENGTH_SHORT).show();
                        }

                }

                    else {
                        Toast.makeText(AddProjectActivity.this, "You have to fill up all the fields", Toast.LENGTH_SHORT).show();
                    }*/

            }
        });

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    //handling the image chooser activity result
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            path=getRealPathFromURI(AddProjectActivity.this,filePath);
            final File file=new File(path);
            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
             String encoded=getBase64FromPath(path);

            tv_filename.setVisibility(View.VISIBLE);
            tv_filename.setText(file.getName());

            tv_filename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent target = new Intent(Intent.ACTION_VIEW);
                    target.setDataAndType(Uri.fromFile(file),"application/pdf");
                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    Intent intent = Intent.createChooser(target, "Open File");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        // Instruct the user to install a PDF reader here, or something
                    }
                }
            });

            decodedString = Base64.decode(getBase64FromPath(path), Base64.DEFAULT);

            Log.d("pdffile",getBase64FromPath(path));

        }
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getBase64FromPath(String path) {
        String base64 = "";
        try {/*from w  w w.j a v  a2 s  .  c  om*/
            File file = new File(path);
            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    private void ProjectReportUpload(String path,String semester){

        int classroomid=-1;
        int year=Calendar.getInstance().get(Calendar.YEAR);

        final ProgressDialog dialog=PDialog.showDialog(AddProjectActivity.this);

        RequestBody title=RequestBody.create(MediaType.parse("text/plain"),et_title.getText().toString());
        RequestBody programming_language=RequestBody.create(MediaType.parse("text/plain"),et_language.getText().toString());
        RequestBody semesters=RequestBody.create(MediaType.parse("text/plain"),semester);
        RequestBody years=RequestBody.create(MediaType.parse("text/plain"), String.valueOf(year));
        RequestBody owners=RequestBody.create(MediaType.parse("text/plain"), et_developedby.getText().toString());
        RequestBody classroomsid=RequestBody.create(MediaType.parse("text/plain"), String.valueOf(classroomid));
        RequestBody desc=RequestBody.create(MediaType.parse("text/plain"), et_desc.getText().toString());
        RequestBody githublink=RequestBody.create(MediaType.parse("text/plain"), et_githublink.getText().toString());
        RequestBody email=RequestBody.create(MediaType.parse("text/plain"), "");

        File files = new File(path);
        RequestBody mFile = RequestBody.create(MediaType.parse("*"),files);
        MultipartBody.Part file2= MultipartBody.Part.createFormData("project_report", files.getName(), mFile);

        Call<AddProjectResponse> call=apiInterface.projectreportupload(title,programming_language,semesters,years,owners,classroomsid,desc,file2,githublink,email);

        call.enqueue(new Callback<AddProjectResponse>() {
            @Override
            public void onResponse(Call<AddProjectResponse> call, Response<AddProjectResponse> response) {
                dialog.dismiss();
                Toast.makeText(AddProjectActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AddProjectResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(AddProjectActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void ProjectReportStudentUpload(String path,String semester){

        List<Student> studentList=new ArrayList<Student>();
        studentList=db.getStudentDetails();

        int classroomid=getIntent().getIntExtra("classroomid",0);
        int year=Calendar.getInstance().get(Calendar.YEAR);

        final ProgressDialog dialog=PDialog.showDialog(AddProjectActivity.this);

        RequestBody title=RequestBody.create(MediaType.parse("text/plain"),et_title.getText().toString());
        RequestBody programming_language=RequestBody.create(MediaType.parse("text/plain"),et_language.getText().toString());
        RequestBody semesters=RequestBody.create(MediaType.parse("text/plain"),semester);
        RequestBody years=RequestBody.create(MediaType.parse("text/plain"), String.valueOf(year));
        RequestBody owners=RequestBody.create(MediaType.parse("text/plain"), studentList.get(0).getName());
        RequestBody classroomsid=RequestBody.create(MediaType.parse("text/plain"), String.valueOf(classroomid));
        RequestBody desc=RequestBody.create(MediaType.parse("text/plain"), et_desc.getText().toString());
        RequestBody githublink=RequestBody.create(MediaType.parse("text/plain"), et_githublink.getText().toString());
        RequestBody email=RequestBody.create(MediaType.parse("text/plain"), studentList.get(0).getEmail());

        File files = new File(path);
        RequestBody mFile = RequestBody.create(MediaType.parse("*"),files);
        MultipartBody.Part file2= MultipartBody.Part.createFormData("project_report", files.getName(), mFile);

        Call<AddProjectResponse> call=apiInterface.projectreportupload(title,programming_language,semesters,years,owners,classroomsid,desc,file2,githublink,email);

        call.enqueue(new Callback<AddProjectResponse>() {
            @Override
            public void onResponse(Call<AddProjectResponse> call, Response<AddProjectResponse> response) {
                dialog.dismiss();
                Toast.makeText(AddProjectActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AddProjectResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(AddProjectActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private boolean validate(){
        if(et_title.getText().toString().trim().equals(""))
            return false;
        else if(et_desc.getText().toString().trim().equals(""))
            return false;
        else if(et_language.getText().toString().trim().equals(""))
            return false;
        else if(et_developedby.getText().toString().trim().equals(""))
            return false;
        else if(et_githublink.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

}
