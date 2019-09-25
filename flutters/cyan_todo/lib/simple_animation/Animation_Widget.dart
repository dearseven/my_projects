import 'package:flutter/material.dart';

/**
 * 用AnimatedWidget组建来替代执行
 * controller.addListener(() {
    setState(() {});
    });
    刷新
 */
class AnimatedWidgetUI extends StatefulWidget {
  AnimatedWidgetUI({Key key}) : super(key: key);

  _AnimatedWidgetUIState createState() => _AnimatedWidgetUIState();
}

class _AnimatedWidgetUIState extends State<AnimatedWidgetUI>
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
    _animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: RunningHeart(
          animations: List<Animation>()
            ..add(_colorAnimation)
            ..add(_scaleAnimation)
            ..add(_positionAnimation),
          animationController: _animationController),
    );
  }
}

/**
 * 那么 Flutter 也提供了一个部件 AnimationWidget 来实现动画部件，就不需要一直监听了
 */
class RunningHeart extends AnimatedWidget {
  final List<Animation> animations; // 传入动画列表
  final AnimationController animationController; // 控制动画

  RunningHeart({this.animations, this.animationController})
      // 对传入的参数进行限制(当然你也可以不做限制)
      : assert(animations.length == 3),
        assert(animations[0] is Animation<Color>),
        assert(animations[1] is Animation<double>),
        assert(animations[2] is Animation<Offset>),
        super(listenable: animationController);

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        Positioned(
          // 之前的 animation 都通过 animations 参数传入到 `AnimationWidget`
          child: Icon(Icons.favorite,
              color: animations[0].value, size: animations[1].value),
          left: animations[2].value.dx,
          top: animations[2].value.dy,
        )
      ],
    );
  }
}
