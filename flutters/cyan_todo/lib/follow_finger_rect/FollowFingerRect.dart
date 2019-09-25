import 'package:flutter/material.dart';

class FollowFingerRectOne extends StatefulWidget {
  FollowFingerRectOne({Key key}) : super(key: key);

  _FollowFingerRectOneState createState() => _FollowFingerRectOneState();
}

class _FollowFingerRectOneState extends State<FollowFingerRectOne> {
  //用于保存follow item
  final itemKey = GlobalKey();

  //item的位置
  double left = 0.0;
  double top = 0.0;

  // 状态栏高度
  double _statusBarHeight = 0.0;

  // appbar 高度
  double _kLeadingWidth = 0.0;

  @override
  Widget build(BuildContext context) {
    //初始化状态栏的高和appbar的高
    _statusBarHeight = MediaQuery.of(context).padding.top;
    _kLeadingWidth = kToolbarHeight;

    return Container(
      child: followOneCreater(),
    );
  }

  Scaffold followOneCreater() {
    return Scaffold(
      appBar: AppBar(title: Text("Follow finger one demo")),
      body: buildRealFollowOne(),
    );
  }

  Stack buildRealFollowOne() {
    return Stack(
      alignment: Alignment.center,
      children: <Widget>[
        //这个是跟随手指滑动的小块
        Positioned(
            child: Container(
                key: itemKey, width: 50.0, height: 50.0, color: Colors.red),
            left: left,
            top: top),
        //手势处理
        GestureDetector(
          behavior: HitTestBehavior.translucent,
          child: Container(
              color: Colors.transparent,
              width: MediaQuery.of(context).size.width - 10,
              height: MediaQuery.of(context).size.height),
          onPanDown: (details) {
            setState(() {
              //通过itemKey获得控件，然后计算出他一半的宽和高
              RenderObject renderObject =
                  itemKey.currentContext.findRenderObject();
              double itemHalfHeight = renderObject.paintBounds.size.height / 2;
              double itemHalfWidth = renderObject.paintBounds.size.width / 2;

              //计算left的时候，要减去一半的宽
              left = details.globalPosition.dx-itemHalfWidth;
              //计算top的时候，要减去状态栏和appbar还有自身一半的高
              top = details.globalPosition.dy -
                  _kLeadingWidth -
                  _statusBarHeight -
                  itemHalfHeight;
            });
          },
          onPanUpdate: (details) {
            setState(() {
              //通过itemKey获得控件，然后计算出他一半的宽和高
              RenderObject renderObject =
                  itemKey.currentContext.findRenderObject();
              double itemHalfHeight = renderObject.paintBounds.size.height / 2;
              double itemHalfWidth = renderObject.paintBounds.size.width / 2;

              //计算left的时候，要减去一半的宽
              left = details.globalPosition.dx-itemHalfWidth;
              //计算top的时候，要减去状态栏和appbar还有自身一半的高
              top = details.globalPosition.dy -
                  _kLeadingWidth -
                  _statusBarHeight -
                  itemHalfHeight;
            });
          },
          onPanEnd: (details) {
            setState(() {
              left = 0.0;
              top = 0.0;
            });
          },
          onPanCancel: () {
            setState(() {
              left = 0.0;
              top = 0.0;
            });
          },
        ),
      ],
    );
  }
}
