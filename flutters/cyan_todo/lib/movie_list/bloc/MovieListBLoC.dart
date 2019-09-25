import 'dart:async';
import 'package:cyan_todo/movie_list/base/BaseBLoC.dart';
import 'package:cyan_todo/movie_list/pojo/ParamMessage.dart';
import 'package:cyan_todo/util/DioHttp.dart';

/**
 * 真正执行业务处理交互的地方
 */
class MovieListBLoC extends BaseBLoC {
  StreamController _controller = StreamController<dynamic>();
  StreamSink get inMessageSink => _controller.sink;
  Stream<dynamic> get _inStream => _controller.stream;
//

  StreamController _controller2 = StreamController<dynamic>();
  StreamSink get _outMessageSink => _controller2.sink;
  Stream<dynamic> get outStream => _controller2.stream.asBroadcastStream();

  MovieListBLoC() {
    _inStream.listen((data) {
      if (data is int) {
        print("data is int ,num=${data}");
        if (data == 0) {
          //命令0 初始化
          //初始化
          _outMessageSink.add(ParamMessage()..code = 0);
          getDataFromServer();
        }
        // else {
        //   _outMessageSink.add(ParamMessage()..code = 1);
        // }
      } else if (data is String) {
        print("data is String ,num=${data}");
        _outMessageSink.add(ParamMessage()..code = 100);
      }
    });
  }

/**
 * 获取网络数据
 */
  void getDataFromServer() {
    final String url = "getFirstPage";
    //final  url="http://192.168.3.190:8091/qqjfapi/getFirstPage/";
    DioHttp.instance.jiafan(
        url,
        (data) {
          print(data);
          //{"errMsg":0,"person":{"name":"wx"},"list":[{"k":1},{"k":2}]}
          // print("name is ${data["person"]["name"]}");
          // List list = data["list"];
          // for (int i = 0; i < list.length; i++) {
          //   print(" item ${i + 1}; is ${list[i]["k"]}");
          // }
          ParamMessage pm = ParamMessage();
          pm.code = 1;
          //我现在只要nowList
          //****** */用了stream函数式编程 反正我就是喜欢用函数式啊~
          Stream.fromIterable(data["list"])
              .takeWhile((item) {
                return item["type"] == "now";
              })
              .toList()
              .then((i) {
                ParamMessage pm = ParamMessage();
                print("here nowlist has been got");
                i.forEach((one) {
                  print(one);
                });
                pm.list=i;
                //把数据发送到ui
                _outMessageSink.add(pm..code = 1);
              });
        },
        params: Map<String, String>()
          ..["canteenId"] = "1"
          ..["lang"] = "1",
        errorCallBack: (errorMsg) {
          print("出错了奥~~~ ${errorMsg}");
        });
  }

  @override
  void dispose() {
    _controller.close();
    _controller2.close();
  }
}
