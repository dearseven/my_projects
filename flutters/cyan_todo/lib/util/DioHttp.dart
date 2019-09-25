import 'dart:io';

import 'package:dio/dio.dart';
import 'dart:convert';

class DioHttp {
  // 工厂模式
  factory DioHttp() => _getInstance();

  static DioHttp get instance => _getInstance();
  static DioHttp _instance;

  DioHttp._internal() {
    // 初始化
  }

  static DioHttp _getInstance() {
    if (_instance == null) {
      _instance = new DioHttp._internal();
    }
    return _instance;
  }

//-------------------------------------------------------------
  static const String GET = "get";
  static const String POST = "post";

  void jiafan(String url, Function callBack,
      {Map<String, String> params, Function errorCallBack}) async {
    url = "https://api.ququtech.xyz:666/qqjfapi/" + url;
    _request(url, callBack,
        method: GET, params: params, errorCallBack: errorCallBack);
  }

  //-------注意下面的参数有一段是用中括号括起来的
  // {Map<String, String> params, Function errorCallBack}
  //这种表示实参要指定参数名
  //get请求
  void get(String url, Function callBack,
      {Map<String, String> params, Function errorCallBack}) async {
    _request(url, callBack,
        method: GET, params: params, errorCallBack: errorCallBack);
  }

  //post请求
  void post(String url, Function callBack,
      {Map<String, String> params, Function errorCallBack}) async {
    _request(url, callBack,
        method: POST, params: params, errorCallBack: errorCallBack);
  }

  //具体的还是要看返回数据的基本结构
  //公共代码部分
  static void _request(String url, Function callBack,
      {String method,
      Map<String, String> params,
      Function errorCallBack}) async {
    Options options = Options(headers: {
      HttpHeaders.contentTypeHeader: "application/json",
      'guitoujun': 'true'
    });

    print("<net> url :<" + method + ">" + url);

    if (params != null && params.isNotEmpty) {
      print("<net> params :" + params.toString());
    }

    String errorMsg = "";
    int statusCode;

    try {
      Response response;
      print("http 1");
      if (method == GET) {
        //组合GET请求的参数
        if (params != null && params.isNotEmpty) {
          StringBuffer sb = new StringBuffer("?");
          params.forEach((key, value) {
            sb.write("$key" + "=" + "$value" + "&");
          });
          String paramStr = sb.toString();
          paramStr = paramStr.substring(0, paramStr.length - 1);
          url += paramStr;
        }
        print("http 2");
        response = await Dio().get(url);
      } else {
        print("http 3");
        if (params != null && params.isNotEmpty) {
          print("http 3.1");
          response = await Dio().post(url, data: params, options: options);
        } else {
          print("http 3.2");
          response = await Dio().post(url, options: options);
        }
      }
      print("http 4");
      //处理返回值
      statusCode = response.statusCode;
      if (statusCode == 200) {
        //print("response data is:${response.data}");
        //print(response.data["person"]["name"]);
        if (callBack != null) {
          //返回的数据是
          //{"errMsg":0,"person":{"name":"wx"},"list":[{"k":1},{"k":2}]}
          //但是貌似已经被处理成json的感觉了，但是在解析的时候不能用.
          //比如我要拿到wx那个值
          //response.data["person"]["name"]
          //而不能使用
          //response.data.person.name
          callBack(response.data);
        }
        print("http 5");
      } else {
        //if (statusCode < 0) {
        errorMsg = "网络请求错误,状态码:" + statusCode.toString();
        _handError(errorCallBack, errorMsg);
        return;
        //}
      }
    } catch (exception) {
      _handError(errorCallBack, exception.toString());
    }
  }

  //处理异常
  static void _handError(Function errorCallback, String errorMsg) {
    if (errorCallback != null) {
      errorCallback(errorMsg);
    }
    print("<net> errorMsg :" + errorMsg);
  }
}
