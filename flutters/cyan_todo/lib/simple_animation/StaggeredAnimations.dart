import 'package:flutter/material.dart';

/**
 * 交错动画，也就是按照一定的时序播放的动画
 */
class StaggeredAnimation extends StatefulWidget {
  StaggeredAnimation({Key key}) : super(key: key);

  _StaggeredAnimationState createState() => _StaggeredAnimationState();
}

class _StaggeredAnimationState extends State<StaggeredAnimation>
    with SingleTickerProviderStateMixin {
  AnimationController controller;
  Animation<double> opacity;
  Animation<double> width;
  Animation<double> height;
  Animation<EdgeInsets> padding;
  Animation<BorderRadius> border;
  Animation<Color> color;
  Animation<double> rotate;

  @override
  void initState() {
    super.initState();

    controller = AnimationController(
        vsync: this, duration: Duration(milliseconds: 2500));

    controller.addListener(() {
      setState(() {});
    });
    controller.addStatusListener((s) {
      if (s == AnimationStatus.completed) {
        controller.reverse();
      } else if (s == AnimationStatus.dismissed) {
        controller.forward();
      }
    });
    // widget 透明度
    opacity = Tween(begin: 0.0, end: 1.0).animate(CurvedAnimation(
        parent: controller, curve: Interval(0.0, 0.1, curve: Curves.ease)));
    // widget 宽
    width = Tween(begin: 50.0, end: 150.0).animate(CurvedAnimation(
        parent: controller, curve: Interval(0.1, 0.250, curve: Curves.ease)));
    // widget 高
    height = Tween(begin: 50.0, end: 150.0).animate(CurvedAnimation(
        parent: controller, curve: Interval(0.25, 0.375, curve: Curves.ease)));
    // widget 底部距离
    padding = EdgeInsetsTween(
            begin: const EdgeInsets.only(top: 150.0),
            end: const EdgeInsets.only(top: .0))
        .animate(CurvedAnimation(
            parent: controller,
            curve: Interval(0.25, 0.375, curve: Curves.ease)));
    // widget 旋转
    rotate = Tween(begin: 0.0, end: 0.25).animate(CurvedAnimation(
        parent: controller, curve: Interval(0.375, 0.5, curve: Curves.ease)));
    // widget 外形
    border = BorderRadiusTween(
            begin: BorderRadius.circular(5.0), end: BorderRadius.circular(75.0))
        .animate(CurvedAnimation(
            parent: controller,
            curve: Interval(0.5, 0.75, curve: Curves.ease)));
    // widget 颜色
    color = ColorTween(begin: Colors.blue, end: Colors.orange).animate(
        CurvedAnimation(
            parent: controller,
            curve: Interval(0.75, 1.0, curve: Curves.ease)));

    //--开始
    controller.forward();
  }

 @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("时间交错动画"),
      ),
      body: Container(
        padding: padding.value,
        alignment: Alignment.center,
        // 旋转变化
        child: RotationTransition(
          turns: rotate, // turns 表示当前动画的值 * 360° 角度
          child: Opacity(
            opacity: opacity.value, // 透明度变化
            child: Container(
              width: width.value, // 宽度变化
              height: height.value, // 高度变化
              decoration: BoxDecoration(
                  color: color.value, // 颜色变化
                  border: Border.all(color: Colors.indigo[300], width: 3.0),
                  borderRadius: border.value), // 外形变化
            ),
          ),
        ),
      ),
    );
  }
}
