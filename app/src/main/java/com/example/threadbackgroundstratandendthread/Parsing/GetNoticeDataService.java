package com.example.threadbackgroundstratandendthread.Parsing;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetNoticeDataService {
        @GET("/pizza/?format=xml")
        Call<ResponseBody> GetData2();
}
