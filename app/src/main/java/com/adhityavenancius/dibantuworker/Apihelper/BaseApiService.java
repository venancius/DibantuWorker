package com.adhityavenancius.dibantuworker.Apihelper;

import com.adhityavenancius.dibantuworker.Model.ResponseAvailableJobs;
import com.adhityavenancius.dibantuworker.Model.ResponseCategory;
import com.adhityavenancius.dibantuworker.Model.ResponseCity;
import com.adhityavenancius.dibantuworker.Model.ResponseJobs;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Adhitya Venancius on 5/8/2018.
 */

public interface BaseApiService {

    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("role") String user);

    @FormUrlEncoded
    @POST("auth/register")
    Call<ResponseBody> registerRequest(@Field("name") String name,@Field("email") String email,
                                       @Field("phone") String phone,@Field("password") String password,
                                       @Field("id_city") int id_city,@Field("address") String address,
                                    @Field("role") String role,@Field("skill_category") int skill_category);


    @FormUrlEncoded
    @POST("auth/register")
    Call<ResponseBody> registerRequest(@Field("nama") String nama,
                                       @Field("email") String email,
                                       @Field("password") String password);


    @GET("category/getlist")
    Call<ResponseCategory> getAllCategory();

    @GET("city/getlist")
    Call<ResponseCity> getCity();


    @FormUrlEncoded
    @POST("jobs/inputjob")
    Call<ResponseBody> inputJobRequest(@Field("id_user") String id_user,
                                    @Field("id_worker") String id_worker,
                                    @Field("id_category") String id_category,
                                       @Field("id_city") int id_city,
                                       @Field("startdate") String startdate,
                                       @Field("enddate") String enddate,
                                       @Field("location") String location,
                                       @Field("time") String time,
                                       @Field("endtime") String endtime,
                                       @Field("fare") String fare,
                                       @Field("notes") String notes,
                                       @Field("status") String status
                                       );

    @FormUrlEncoded
    @POST("jobs/getlist")
    Call<ResponseJobs> getJobsRequest(@Field("id_worker") String id_user);

    @FormUrlEncoded
    @POST("jobs/getsingle")
    Call<ResponseBody> getJobsDetail(@Field("id_jobs") String id_jobs,
                                     @Field("role") String role);

    @FormUrlEncoded
    @POST("jobs/finishjob")
    Call<ResponseBody> finishJob(@Field("id_jobs") String id_jobs,
                                 @Field("rate") float rate,
                                 @Field("feedback") String feedback);

    @FormUrlEncoded
    @POST("account/getworkerprofile")
    Call<ResponseBody> getWorkerProfile(@Field("id_worker") String id_worker);


    @FormUrlEncoded
    @POST("jobs/takejob")
    Call<ResponseBody> takeJob(@Field("id_worker") String id_worker,@Field("id_job") String id_job);

    @FormUrlEncoded
    @POST("jobs/getavailablejobs")
    Call<ResponseAvailableJobs> getAvailableJobs(@Field("id_worker") String id_worker);

    @FormUrlEncoded
    @POST("account/setworkerprofile")
    Call<ResponseBody> setWorkerProfile(@Field("id") String id_worker,@Field("name") String name,
                                      @Field("phone") String phone,@Field("email") String email);

    @Multipart
    @POST("account/uploadimage")
    Call<ResponseBody> uploadImage(
            @Part MultipartBody.Part file,@Part("id") RequestBody id,@Part("role") RequestBody role
    );

}
