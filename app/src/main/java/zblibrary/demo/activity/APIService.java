package zblibrary.demo.activity;

import com.alibaba.fastjson.JSONObject;
import com.zhouyou.http.model.ApiResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {
    @POST("user/admin/sync")
    Observable<ApiResult<JSONObject>> login();
}
