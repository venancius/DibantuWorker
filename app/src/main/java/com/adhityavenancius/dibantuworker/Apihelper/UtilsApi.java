package com.adhityavenancius.dibantuworker.Apihelper;

/**
 * Created by Adhitya Venancius on 5/8/2018.
 */

public class UtilsApi {

    // 10.0.2.2 ini adalah localhost.
    public static final String BASE_URL_API = "http://192.168.43.172/dibantu/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

    //URL uploads
    public static final String UPLOAD_URL = BASE_URL_API + "uploads/";

}
