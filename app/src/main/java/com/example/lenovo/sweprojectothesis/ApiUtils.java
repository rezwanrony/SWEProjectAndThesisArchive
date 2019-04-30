package com.example.lenovo.sweprojectothesis;

/**
 * Created by azeR on 11/4/2018.
 */


public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://roni.datalibrary.io/webservices/";

    public static ApiInterface getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}

