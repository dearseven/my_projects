import 'package:flutter/material.dart';

/**
 * 一个简单的反复Scale动画
 */
class SimpleScale extends StatefulWidget {
  SimpleScale({Key key}) : super(key: key);

  _SimpleScaleState createState() => _SimpleScaleState();
}

class _SimpleScaleState extends State<SimpleScale>
    with SingleTickerProviderStateMixin {
  AnimationController _animationController; //该对象管理着animation对象
  Animation<double> _scaleAnimation; //该对象是当前动画的状态，例如动画是否开始，停止，前进，后退。

  @override
  void initState() {
    super.initState();
    // 不通过AnimationController的`lowerBound` 和 `upperBound` 设置范围，改用 `Tween`
    _animationController =
        AnimationController(vsync: this, duration: Duration(milliseconds: 500));
    //当动画的值发生变化的时候，重绘
    _animationController.addListener(() {
      setState(() {});
    });
    //这一段是让动画自动反复播放
    _animationController.addStatusListener((status) {
      //动画正向播放结束
      if (_animationController.status == AnimationStatus.completed) {
        //反向播放
        _animationController.reverse();
      } //动画反向播放结束
      else if (_animationController.status == AnimationStatus.dismissed) {
        //正向播放
        _animationController.forward();
      }
    });

    /*
      Tween 是一个线性插值(如果要修改运动的插值，可以通过 CurveTween 来修改)，所以在线性变化的时候很有用
      通过调用 Tween 的 animate 方法生成一个 Animation(animate 一般传入 AnimationController)
    */
    // 通过 `Tween` 的 `animate` 生成一个 Animation
    // 再通过  Animation.value 进行值的修改
    _scaleAnimation =
        Tween(begin: 28.0, end: 50.0).animate(_animationController);
    //播放动画
    _animationController.forward();
  }

  @override
  void dispose() {
    //*一定要释放资源啊
    _animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('简单反复缩放动画'),
      ),
      body: Center(
        // 通过动画返回的值，修改图标的大小
        child: Icon(Icons.favorite,
            color: Colors.red, size: _scaleAnimation.value),
      ),
    );
  }
}
