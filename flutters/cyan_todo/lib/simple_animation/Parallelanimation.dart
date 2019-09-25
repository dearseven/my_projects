import 'package:flutter/material.dart';

/**
 * 通过chain实现并行动画
 */
class ParallelAnimation extends StatefulWidget {
  ParallelAnimation({Key key}) : super(key: key);

  _ParallelAnimationState createState() => _ParallelAnimationState();
}

class _ParallelAnimationState extends State<ParallelAnimation>
    with SingleTickerProviderStateMixin {
  AnimationController _animationController;
  Animation _scaleAnimation; // 用于控制图标大小
  Animation<Color> _colorAnimation; // 控制图标颜色
  Animation<Offset> _positionAnimation; // 控制图标位置

  @override
  void initState() {
    super.initState();
    _animationController =
        AnimationController(vsync: this, duration: Duration(milliseconds: 500));
    // 当动画值发生变化的时候，重绘下 icon
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

    // 通过 `chain` 结合 `CurveTween` 修改动画的运动方式，曲线类型可自行替换
    _scaleAnimation = Tween(begin: 28.0, end: 50.0)
        .chain(CurveTween(curve: Curves.linear))
        .animate(_animationController);

    _colorAnimation = ColorTween(begin: Colors.red[200], end: Colors.red[900])
        .chain(CurveTween(curve: Curves.fastOutSlowIn))
        .animate(_animationController);

    _positionAnimation = Tween(begin: Offset(100, 100), end: Offset(300, 300))
        .chain(CurveTween(curve: Curves.fastLinearToSlowEaseIn))
        .animate(_animationController);

    _animationController.forward(); // 启动动画
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
        title: Text('Animation Demo'),
      ),
      body: Stack(
        children: <Widget>[
          //通过动画的值变化来让Icon发生各种变化
          Positioned(
            child: Icon(Icons.favorite,
                color: _colorAnimation.value, size: _scaleAnimation.value),
            left: _positionAnimation.value.dx,
            top: _positionAnimation.value.dy,
          )
        ],
      ),
    );
  }
}
