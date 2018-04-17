package com.mvvm.demo.api.user;



import com.mvvm.demo.api.ResponseBase;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Sumit on 05/07/17.
 */

public interface IUser {

    @FormUrlEncoded
    @POST("user/login")
    Observable<ResponseBase> login(@Field("login") String email, @Field("password") String password);

    }
