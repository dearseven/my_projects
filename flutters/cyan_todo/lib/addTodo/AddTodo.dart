import 'dart:ui';

import 'package:cyan_todo/a_pretty_login/PrettyLoginUI.dart';
import 'package:cyan_todo/draggable/draggable.dart';
import 'package:cyan_todo/draglike/drag_like.dart';
import 'package:cyan_todo/follow_finger_rect/FollowFingerRect.dart';
import 'package:cyan_todo/movie_list/MovieListMain.dart';
import 'package:cyan_todo/my_slider/MySlider.dart';
import 'package:cyan_todo/simple_animation/Animation_Widget.dart';
import 'package:cyan_todo/simple_animation/Animation_Widget_Staggered.dart';
import 'package:cyan_todo/simple_animation/Parallelanimation.dart';
import 'package:cyan_todo/simple_animation/SimpleScaleAnimation.dart';
import 'package:cyan_todo/simple_animation/StaggeredAnimations.dart';
import 'package:cyan_todo/status_manager/BLoC/BLoCMain.dart';
import 'package:cyan_todo/status_manager/Stream.dart';
import 'package:cyan_todo/status_manager/StreamBuilder.dart';
import 'package:cyan_todo/util/DioHttp.dart';
import 'package:cyan_todo/util/MyRouter.dart';
import 'package:cyan_todo/webview/FlutterWebView.dart';
import 'package:flutter/material.dart';
import 'package:cyan_todo/util/SizeUtil.dart';
import 'package:flutter/services.dart';

/**
 * 新增事项的UI
 */
class AddTodoUI extends StatefulWidget {
  AddTodoUI({Key key}) : super(key: key);

  // _AddTodoUIState createState() => _AddTodoUIState();

  _AddTodoUIState createState() {
    return _AddTodoUIState();
  }
}

class _AddTodoUIState extends State<AddTodoUI> {
  final TextEditingController titleTextController = TextEditingController();

  //用于获取element（MySlider的对象）
  GlobalKey _mySliderKey = new GlobalKey();

  //保存用户输入的信息的变量
  String pickedDate = '0000-00-00';
  String pickedTime = '00:00:00';
  int groupValue = 1;
  String titleTxt = "闹钟";
  String detailTxt = "无";

  //
  double mySliderPanInit = 0.0;
  double mySliderPercentage = 10.0;

  //

  //调用原生代码的通道
  static const platform = const MethodChannel("cyan.todo.main/plugin");

  @override
  Widget build(BuildContext context) {
    titleTextController.addListener(() {
      print("${titleTextController.text}");
    });
    var sizeUtil = SizeUtil();
//    print("获取像素密度:${MediaQuery.of(context).devicePixelRatio}");
//    print(
//        "获取逻辑宽高:${sizeUtil.getScreenWidth(context)},${sizeUtil.getScreenHeight(context)}");
//    print(
//        "获取物理宽高:${sizeUtil.getPhysicScreenWidth(window)},${sizeUtil.getPhysicScreenHeight(window)}");
//    print("状态栏的高度:${sizeUtil.getStatuBarHeight(window)}");

    return Container(
      child: Scaffold(
          appBar: AppBar(
            title: Text("新增事项"),
            automaticallyImplyLeading: false, //去掉默认的leading
            leading: IconButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              icon: Icon(Icons.arrow_back),
            ),
            actions: <Widget>[
              IconButton(
                icon: Icon(Icons.done),
                tooltip: "完成",
                onPressed: () async {
                  print("完成");
                  //用flutter调用原生代码
                  final Object result = await platform.invokeMethod(
                      "FLUTTER_CALL_NAME_showToast",
                      [DateTime.now().toString()]);
                  //采用dio访问网络 api用mocky.io生成的哦
                  final String url =
                      "http://www.mocky.io/v2/5ccfe3ef320000630000f893";
                  DioHttp.instance.get(url, (data) {
//{"errMsg":0,"person":{"name":"wx"},"list":[{"k":1},{"k":2}]}
                    //api返回的数据是以上格式，不能用.来访问
                    print("name is ${data["person"]["name"]}");
                    List list = data["list"];
                    for (int i = 0; i < list.length; i++) {
                      print(" item ${i + 1}; is ${list[i]["k"]}");
                    }
                  }, errorCallBack: (errorMsg) {
                    print("出错了奥~~~ ${errorMsg}");
                  });
                  //----获取控件输入的信息----
                  print(
                      "${pickedDate}-${pickedTime}-${groupValue}-${titleTxt}-${detailTxt}");
                  //保存闹钟信息到sqlite
                  final bool saveResult = await platform.invokeMethod(
                      "TodoSavePlugin_save_todo", [
                    pickedDate,
                    pickedTime,
                    groupValue,
                    titleTxt,
                    detailTxt
                  ]);
                  //将数据返回到上一级
                  if (saveResult) {
                    Navigator.pop(context, 'save suc!');
                  }
                },
              ),
            ],
          ),
          body: ListView(
            children: <Widget>[
              //第一行
              SizedBox(
                height: sizeUtil.getScreenHeight(context) * 0.08,
                width: sizeUtil.getScreenWidth(context),
                child: Container(
                  color: Colors.green,
                  child: Row(
                    mainAxisSize: MainAxisSize.max,
                    children: <Widget>[
                      SizedBox(
                        width: sizeUtil.getScreenWidth(context) * 0.3,
                        child: Container(
                          color: Colors.red,
                          child: Text(
                            "标题",
                            textAlign: TextAlign.center,
                            style: TextStyle(
                              fontSize: 20.0,
                            ),
                          ),
                        ),
                      ),
                      SizedBox(
                        width: sizeUtil.getScreenWidth(context) * 0.7,
                        child: Container(
                          color: Colors.blue,
                          child: TextField(
                            onChanged: (txt) {
                              print(txt);
                              setState(() {
                                titleTxt = txt;
                              });
                            },
                            maxLines: 1,
                            textAlign: TextAlign.left,
                            // onChanged: (text) {
                            //   print("标题=${text}");
                            // },
                            controller: titleTextController,
                            decoration: InputDecoration(hintText: "请输入标题"),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              //第二行
              SizedBox(
                height: sizeUtil.getScreenHeight(context) * 0.08,
                width: sizeUtil.getScreenWidth(context),
                child: Row(
                  children: <Widget>[
                    SizedBox(
                      width: sizeUtil.getScreenWidth(context) * 0.3,
                      child: Container(
                        color: Colors.red,
                        child: Text(
                          "提醒日期",
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            fontSize: 20.0,
                          ),
                        ),
                      ),
                    ),
                    SizedBox(
                      width: sizeUtil.getScreenWidth(context) * 0.7,
                      child: Container(
                        color: Colors.blue,
                        child: GestureDetector(
                            onTap: () async {
                              Locale myLocale = Localizations.localeOf(context);
                              var picker = await showDatePicker(
                                  context: context,
                                  initialDate: DateTime.now(),
                                  firstDate: DateTime(2019),
                                  lastDate: DateTime(2022),
                                  locale: myLocale);
                              setState(() {
                                pickedDate = picker.toString().split(" ")[0];
                              });
                            },
                            child: Text(
                              pickedDate,
                              textAlign: TextAlign.center,
                              style: TextStyle(fontSize: 22.0),
                            )),
                      ),
                    ),
                  ],
                ),
              ),
              //第三行
              SizedBox(
                height: sizeUtil.getScreenHeight(context) * 0.08,
                width: sizeUtil.getScreenWidth(context),
                child: Row(
                  children: <Widget>[
                    SizedBox(
                      width: sizeUtil.getScreenWidth(context) * 0.3,
                      child: Container(
                        color: Colors.red,
                        child: Text(
                          "提醒时间",
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            fontSize: 20.0,
                          ),
                        ),
                      ),
                    ),
                    SizedBox(
                      width: sizeUtil.getScreenWidth(context) * 0.7,
                      child: Container(
                        color: Colors.blue,
                        child: GestureDetector(
                            onTap: () async {
                              var picker = await showTimePicker(
                                  context: context,
                                  initialTime: TimeOfDay.now());
                              setState(() {
                                pickedTime = "${picker.hour}:${picker.minute}";
                              });
                            },
                            child: Text(
                              pickedTime,
                              textAlign: TextAlign.center,
                              style: TextStyle(fontSize: 22.0),
                            )),
                      ),
                    ),
                  ],
                ),
              ),
              //第四行，这里是一个详情输入框
              ConstrainedBox(
                constraints: BoxConstraints(maxHeight: 100, maxWidth: 200),
                child: new TextField(
                  maxLines: 3,
                  onChanged: (txt) {
                    setState(() {
                      detailTxt = txt;
                    });
                  },
                  decoration: InputDecoration(
                    contentPadding: const EdgeInsets.symmetric(vertical: 4.0),
                    hintText: '请输入提醒内容',
                    prefixIcon: Icon(Icons.input),
                    // contentPadding: EdgeInsets.all(10),
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(15),
                        borderSide: BorderSide.none),
                    filled: true,
                    fillColor: Color(0xffaaaaaa),
                  ),
                ),
              ),
              //第五行，重复提醒选择
              SizedBox(
                height: sizeUtil.getScreenHeight(context) * 0.1,
                width: sizeUtil.getScreenWidth(context),
                child: Row(
                  children: <Widget>[
                    Container(
                        width: sizeUtil.getScreenWidth(context) * 0.33,
                        child: Center(
                          child: Row(
                            children: <Widget>[
                              Radio(
                                value: 1,
                                groupValue:
                                    groupValue, //当value和groupValue一致的时候则选中
                                activeColor: Colors.red,
                                onChanged: (T) {
                                  setState(() {
                                    groupValue = 1;
                                  });
                                },
                              ),
                              Text("不重复")
                            ],
                          ),
                        )),
                    Container(
                      width: sizeUtil.getScreenWidth(context) * 0.33,
                      child: Center(
                        child: Row(
                          children: <Widget>[
                            Radio(
                              value: 2,
                              groupValue:
                                  groupValue, //当value和groupValue一致的时候则选中
                              activeColor: Colors.red,
                              onChanged: (T) {
                                setState(() {
                                  groupValue = 2;
                                });
                              },
                            ),
                            Text("不重复    ")
                          ],
                        ),
                      ),
                    ),
                    Container(
                      width: sizeUtil.getScreenWidth(context) * 0.33,
                      child: Center(
                        child: Row(
                          children: <Widget>[
                            Radio(
                              value: 3,
                              groupValue:
                                  groupValue, //当value和groupValue一致的时候则选中
                              activeColor: Colors.red,
                              onChanged: (T) {
                                setState(() {
                                  groupValue = 3;
                                });
                              },
                            ),
                            Text("不重复")
                          ],
                        ),
                      ),
                    )
                  ],
                ),
              ),
              //MySlider滑动块
              Container(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  color: Colors.blue,
                  child: Center(
                      child: GestureDetector(
                          onPanStart: (DragStartDetails dsd) {
                            mySliderPanInit = dsd.globalPosition.dx;
                          },
                          onPanUpdate: (dsd) {
                            double distance =
                                dsd.globalPosition.dx - mySliderPanInit;
                            //这里写除以300是因为我在定义的MySlider的宽度是300(qishi meiyou!)
                            double percentAddition = distance / 100;
                            mySliderPercentage =
                                (percentAddition + mySliderPercentage)
                                    .clamp(0.0, 100.0);
                            print(mySliderPercentage);
                            //通过_mySliderKey来获取MySlider的实例
                            //然后调用其setProgress方法
                            (_mySliderKey.currentWidget as MySlider)
                                .setProgress(mySliderPercentage);
                          },
                          onPanEnd: (DragEndDetails ded) {
                            mySliderPanInit = 0.0;
                          },
                          child: MySlider(
                            key: _mySliderKey,
                            percentage: mySliderPercentage,
                            positiveColor: Colors.amberAccent,
                            negetiveColor: Colors.black,
                          )))),
              //可拖动组建的进入区块
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入可拖动组建页面");
                        Navigator.of(context).push(
                          // new MaterialPageRoute(
                          //     builder: (context) => new DraggableOne()),
                          MyRouter(DraggableOne()),
                        );
                      },
                      child: Text(
                        "Draggable widget",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //pretty login page
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.12,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入Pretty login Page");
                        Navigator.of(context).push(
                          // new MaterialPageRoute(
                          //     builder: (context) => new PrettyLoginPage()),
                          MyRouter(PrettyLoginPage()),
                        );
                      },
                      child: Text(
                        "Pretty login page",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //仿探探左滑不喜欢右滑喜欢
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入【仿探探左滑不喜欢右滑喜欢】");
                        Navigator.of(context).push(
                          // new MaterialPageRoute(
                          //     builder: (context) => new DragLikePage()),
                          MyRouter(DragLikePage()),
                        );
                      },
                      child: Text(
                        "仿探探左滑不喜欢右滑喜欢",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //简单的跟随手指一动的小方块
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入【简单的跟随手指一动的小方块】");
                        Navigator.of(context).push(
                          // new MaterialPageRoute(
                          //     builder: (context) => new FollowFingerRectOne()),
                          MyRouter(FollowFingerRectOne()),
                        );
                      },
                      child: Text(
                        "简单的跟随手指一动的小方块",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //简单缩放动画
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入【简单缩放动画】");
                        Navigator.of(context).push(
                          // new MaterialPageRoute(
                          //     builder: (context) => new SimpleScale()),
                          MyRouter(SimpleScale()),
                        );
                      },
                      child: Text(
                        "简单缩放动画",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //并行多动画
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入【并行多动画】");
                        Navigator.of(context).push(
                          // MaterialPageRoute(
                          //     builder: (context) => ParallelAnimation()),
                          MyRouter(ParallelAnimation()),
                        );
                      },
                      child: Text(
                        "并行多动画",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //时间交错动画
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入【时间交错动画】");
                        Navigator.of(context).push(
                          // MaterialPageRoute(
                          //     builder: (context) => StaggeredAnimation()),
                          MyRouter(StaggeredAnimation()),
                        );
                      },
                      child: Text(
                        "时间交错动画",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //简单动画组件 AnimationWidget
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入【AnimationWidget】");
                        Navigator.of(context).push(
                          // MaterialPageRoute(
                          //     builder: (context) => AnimatedWidgetUI()),
                          MyRouter(AnimatedWidgetUI()),
                        );
                      },
                      child: Text(
                        "AnimationWidget",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //使用动画组件来执行时间交错动画
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入【动画组件来执行时间交错动画】");
                        Navigator.of(context).push(
                            // MaterialPageRoute(
                            //     builder: (context) => AnimationWidgetStaggered()),
                            MyRouter(AnimationWidgetStaggered()));
                      },
                      child: Text(
                        "动画组件来执行时间交错动画",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //状态管理-1-Stream
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入【状态管理-1-Stream】");
                        Navigator.of(context).push(
                            // MaterialPageRoute(
                            //     builder: (context) => AnimationWidgetStaggered()),
                            MyRouter(StreamTest()));
                      },
                      child: Text(
                        "状态管理-1-Stream",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //状态管理-2-StreamBuilder
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入【状态管理-2-StreamBuilder】");
                        Navigator.of(context).push(
                            // MaterialPageRoute(
                            //     builder: (context) => AnimationWidgetStaggered()),
                            MyRouter(StreamBuilderTest()));
                      },
                      child: Text(
                        "状态管理-2-StreamBuilder",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //状态管理-3-BLoCMainApp
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入【状态管理-3-BLoCMainApp】");
                        Navigator.of(context).push(
                            // MaterialPageRoute(
                            //     builder: (context) => AnimationWidgetStaggered()),
                            MyRouter(BLoCMainApp()));
                      },
                      child: Text(
                        "状态管理-3-BLoCMainApp",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //top150电影列表
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入【top150电影列表】");
                        Navigator.of(context).push(
                            // MaterialPageRoute(
                            //     builder: (context) => AnimationWidgetStaggered()),
                            MyRouter(MovieListMain()));
                      },
                      child: Text(
                        "top150电影列表",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
                   //WebViewDemo
              SizedBox(
                  height: sizeUtil.getScreenHeight(context) * 0.225,
                  width: sizeUtil.getScreenWidth(context),
                  child: Center(
                    child: GestureDetector(
                      onTap: () {
                        print("进入【WebViewDemo】");
                        Navigator.of(context).push(
                            // MaterialPageRoute(
                            //     builder: (context) => AnimationWidgetStaggered()),
                            MyRouter(WebViewDemo()));
                      },
                      child: Text(
                        "WebViewDemo",
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    ),
                  )),
              //这里开始是我练习Text的显示什么的
              //1 用sizebox框出一个大小,放text但是发现并不能完美居中
              SizedBox(
                height: sizeUtil.getScreenHeight(context) * 0.4,
                width: sizeUtil.getScreenWidth(context),
                child: Text(
                  "1",
                  style: TextStyle(
                      fontSize: 36,
                      background: Paint()
                        ..color = Color.fromARGB(255, 100, 168, 130)),
                ),
              ),
              //2 用sizebox 然后用Container来给大背景色,然后再Center里放Text才实现了居中,真的好多
              SizedBox(
                height: sizeUtil.getScreenHeight(context) * 0.6,
                width: sizeUtil.getScreenWidth(context),
                child: Container(
                    color: Colors.red,
                    child: Center(
                      child: Text(
                        "2",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                            fontSize: 36,
                            background: Paint()
                              ..color = Color.fromARGB(255, 100, 168, 130)),
                      ),
                    )),
              )
            ],
          )),
    );
  }
}

class _AnimationWidgetStaggeredState {}
