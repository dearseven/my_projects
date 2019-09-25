import 'package:cyan_todo/movie_list/base/BaseBLoCImpl.dart';
import 'package:cyan_todo/movie_list/bloc/MovieListBLoC.dart';
import 'package:cyan_todo/movie_list/pojo/ParamMessage.dart';
import 'package:flutter/material.dart';
import 'package:path/path.dart';

import 'views/ScaleAbleSizedBox.dart';

/**
 * 当我取名叫movielist以后却发现豆瓣的api不对外开放了。行吧，那我用加饭的api吧。至少有图片嘛 哈哈哈哈
 */
//入口
class MovieListMain extends StatelessWidget {
  const MovieListMain({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'TOP150',
      theme: new ThemeData(
        primarySwatch: Colors.orange,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: Text(
            'TOP 150 Movies',
            style: TextStyle(color: Color.fromARGB(255, 255, 255, 255)),
          ),
          centerTitle: true,
          elevation: 10.0,
        ),
        body: MainAreaContainer(),
      ),
    );
  }
}

//主要部分，把页面通过Column分成两个部分
class MainAreaContainer extends StatelessWidget {
  const MainAreaContainer({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    MovieListBLoC bloc = MovieListBLoC();
    return Container(
        color: Colors.yellow,
        //把内容区块分成2个部分，这两个部分的都是通过BLoC刷新哦
        child: Column(
          children: <Widget>[
            Expanded(
                flex: 1,
                child: Container(
                    color: Color.fromARGB(255, 230, 230, 230),
                    //这里是第一个Provider（包含了业务逻辑和组件）
                    child: BaseBLoCImpl<MovieListBLoC>(
                      bloc: bloc,
                      child: MyListView(),
                    ))),
            // Expanded(
            //     flex: 2,
            //     child: Container(
            //       color: Colors.white,
            //     )),
            SizedBox(
                height: 50,
                child: Container(
                  color: Colors.white,
                  //这里是第二个Provider（包含了业务逻辑和组件）
                  child: BaseBLoCImpl<MovieListBLoC>(
                    bloc: bloc,
                    child: MyNumsView(),
                  ),
                ))
          ],
        ));
  }
}

//-----------------------------------真正和BLoC配合的Widget-----------------------------------------------
class MyListView extends StatelessWidget {
  const MyListView({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final MovieListBLoC bloc = BaseBLoCImpl.of<MovieListBLoC>(context);
    //发送一个请求到bloc，获取数据
    bloc.inMessageSink.add(0);
    //缩放因子,其实还不能做在这里，要做在每一个item的试图中，不然会一起缩小和放大
    //double fraction = 0.95;
    return Container(
      child: StreamBuilder<dynamic>(
          stream: bloc.outStream,
          //这个初始化类型一定要正确啊，一开始我报了很多次错，都是说int不能cast为ParamMessage类型
          //一开始我这里放的是一个0
          initialData: ParamMessage()..code = 0,
          builder: (BuildContext context, AsyncSnapshot<dynamic> snapshot) {
            print("snapshot.data=${snapshot.data}");
            if ((snapshot.data as ParamMessage).code == 0) {
              return Text('正在获取数据~');
            } else if ((snapshot.data as ParamMessage).code == 1) {
              List<dynamic> nowItems =
                  (snapshot.data as ParamMessage).list[0]["nowList"];
              nowItems.forEach((x) {
                x["fraction"] = 0.98;
              });
              return ListView.builder(
                itemCount: nowItems.length,
                itemBuilder: (context, index) {
                  // return AspectRatio(
                  //     aspectRatio: 3 / 2.6, //横纵比 宽高比  3:2.6
                  //     child: Container(
                  //       child: Card(
                  //         child: Column(
                  //           children: buliderView(nowItems, index, context),
                  //         ),
                  //       ),
                  //     ));
                  //--
                  // return Center(
                  //     child: SizedBox(
                  //   width: MediaQuery.of(context).size.width *
                  //       nowItems[index]["fraction"], //3 / 2.6, //横纵比 宽高比  3:2.6
                  //   height: MediaQuery.of(context).size.width *
                  //       nowItems[index]["fraction"] *
                  //       (2.6 / 3),
                  //   child: Card(
                  //     child: Column(
                  //       children: buliderView(nowItems, index, context),
                  //     ),
                  //   ),
                  // ));
                  //--
                  return Center(
                      //把动画放到ScaleAbleSizedBox中
                      child: ScaleAbleSizedBox(
                        forScaleChild: Card(
                      child: Column(
                        children: buliderView(nowItems, index, context),
                      ),
                    ),
                  ));
                },
              );
            }
          }),
    );
  }

  List<Widget> buliderView(
      List<dynamic> nowItems, int index, BuildContext context) {
    return <Widget>[
      Expanded(
        flex: 1,
        child: Image.network(
          nowItems[index]["mealImg"],
          fit: BoxFit.fill,
          width: double.infinity,
          height: double.infinity,
        ),
      ),
      //下方控制区
      Container(
        height: 50,
        color: Colors.white,
        child: Row(
          children: <Widget>[
            Expanded(
                flex: 2,
                child: Align(
                  alignment: Alignment.centerLeft,
                  child: Column(
                    children: <Widget>[
                      Align(
                          alignment: Alignment.centerLeft,
                          child: Padding(
                            child: Text(
                              nowItems[index]["mealName"],
                              style: TextStyle(fontWeight: FontWeight.w600),
                            ),
                            padding: EdgeInsets.only(left: 10, top: 0),
                          )),
                      Align(
                        alignment: Alignment.centerLeft,
                        child: Padding(
                          child: Text("剩余${nowItems[index]["mealNums"]}份"),
                          padding: EdgeInsets.only(
                            left: 10,
                            bottom: 0,
                          ),
                        ),
                      )
                    ],
                  ),
                )),
            Expanded(
                flex: 2,
                child: Row(
                  children: <Widget>[
                    Expanded(
                      child: Container(
                          // margin: EdgeInsets.only(top: 20.0),
                          ),
                    ),
                    Expanded(
                        child: Padding(
                      padding: EdgeInsets.only(right: 5),
                      child: Container(
                        // margin: EdgeInsets.only(top: 20.0),
                        child: RaisedButton(
                          onPressed: () {},
                          child: Text(
                            "购买",
                          ),
                          disabledTextColor: Colors.blue,
                          textColor: Colors.red,
                          disabledColor: Colors.pink,
                          color: Colors.yellow,
                          splashColor: Colors.blue,
                          highlightColor: Colors.greenAccent,
                          highlightElevation: 5,
                        ),
                      ),
                    ))
                  ],
                )),
          ],
        ),
      )
    ];
  }
}

class MyNumsView extends StatelessWidget {
  const MyNumsView({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final MovieListBLoC bloc = BaseBLoCImpl.of<MovieListBLoC>(context);

    return Container(
      child: null,
    );
  }
}
