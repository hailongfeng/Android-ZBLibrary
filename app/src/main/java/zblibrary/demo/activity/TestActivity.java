package zblibrary.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.exception.ApiException;

import zblibrary.demo.R;
import zuo.biao.library.base.BaseActivity;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

//    @Override
    public void initView() {

    }

//    @Override
    public void initData() {

    }

//    @Override
    public void initEvent() {

    }
    public static Intent createIntent(Context context) {
        return new Intent(context, TestActivity.class);
    }
    public void test1(View view) {
//        APIService apiService = EasyHttp.getRetrofit().create(APIService.class);
//        CustomRequest customRequest = EasyHttp.custom();
//        customRequest.apiCall(apiService.login(), new CallBack<ApiResult<JSONObject>>() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(ApiException e) {
//
//            }
//
//            @Override
//            public void onSuccess(ApiResult<JSONObject> jsonObjectApiResult) {
//
//            }
//        });
        EasyHttp.get("https://zj.v.api.aa1.cn/api/xz/")
                .params("code","654028207203")
                .execute(new CallBack<JSONObject>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onSuccess(JSONObject jsonObjectApiResult) {
                Logger.d(jsonObjectApiResult.toString());
            }
        });
    }
}