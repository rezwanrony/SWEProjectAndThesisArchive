package com.example.lenovo.sweprojectothesis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by azeR on 11/4/2018.
 */

public interface ApiInterface {
    @POST("studentregister_2.php")
    Call<LoginResult> getStringScalar(@Body LoginData body);

    @POST("teacherregister.php")
    Call<TeacherLoginResult> getStringTeacherScalar(@Body TeacherLoginData body);


}
