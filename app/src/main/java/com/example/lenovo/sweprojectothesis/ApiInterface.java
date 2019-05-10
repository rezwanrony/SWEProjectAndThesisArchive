package com.example.lenovo.sweprojectothesis;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by azeR on 11/4/2018.
 */

public interface ApiInterface {
    @POST("studentregister_2.php")
    Call<LoginResult> getStringScalar(@Body LoginData body);

    @POST("super_adminregistration.php")
    Call<LoginResult> getStringAdminScalar(@Body AdminLoginData body);

    @POST("teacherregister.php")
    Call<TeacherLoginResult> getStringTeacherScalar(@Body TeacherLoginData body);

    @Multipart
    @POST("addprojects.php")
    Call<AddProjectResponse> projectreportupload(
            @Part("title") RequestBody name,
            @Part("programming_language") RequestBody programming_language,
            @Part("semester") RequestBody semester,
            @Part("year") RequestBody year,
            @Part("project_owner") RequestBody project_owner,
            @Part("classroom_id") RequestBody classroom_id,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part surveyImages,
            @Part("githublink") RequestBody githublink,
            @Part("student_email") RequestBody email
    );





}
