package com.example.dev_sec_5.network;

import com.example.dev_sec_5.model.Post_ibtissam;
import com.example.dev_sec_5.model.User_ibtissam;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService_ibtissam {

    @GET("posts")
    Call<List<Post_ibtissam>> getPosts_ibtissam();
    
    @GET("users/{userId}")
    Call<User_ibtissam> getUser_ibtissam(@Path("userId") int userId_ibtissam);
}