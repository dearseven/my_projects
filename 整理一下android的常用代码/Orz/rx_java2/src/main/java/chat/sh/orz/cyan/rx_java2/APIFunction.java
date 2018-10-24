package chat.sh.orz.cyan.rx_java2;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * API的接口定义,全是按照json格式提交
 * Created by wx on 2018/4/19.
 */

public interface APIFunction {
    //json格式参数
    @POST("/user/default/authorize")
    @Deprecated
    Observable<ResponseBody> playAuthorize(@Body RequestBody requestBody);
    //拼接查询
    @GET("/s")
    Observable<ResponseBody> seeBaidu(@QueryMap HashMap<String, String> params);
    //拼接查询
    @FormUrlEncoded
    @POST("news/list")
    Observable<ResponseBody> getNewsData(@FieldMap HashMap<String, String> params);
    //------------------------------------------------------------------------------------------------------
    //json格式参数
    @POST("APIServlet")
    Observable<ResponseBody> loginWithPwd(@Body HashMap<String, Object> params);
}

