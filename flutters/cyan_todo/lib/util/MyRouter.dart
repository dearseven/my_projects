import 'package:flutter/material.dart';

/**
 * 这是一个路由切换动画，由3个动画一起组合完成
 */
class MyRouter extends PageRouteBuilder {
  //要被切换出来显示的UI页面
  final Widget widget;

  MyRouter(this.widget)
      : super(
            //时长500毫秒
            transitionDuration: Duration(milliseconds: 500),
            pageBuilder: (context, anim1, anim2) => widget,
            //下面都是设置具体的动画
            transitionsBuilder: (context, anim1, anim2, child) =>
                //缩放动画，从0.75-1.0大小
                ScaleTransition(
                  scale: Tween(begin: 0.75, end: 1.0).animate(anim1),
                  //渐近动画，可见度从0.5到1
                  child: FadeTransition(
                    opacity: Tween(begin: 0.5, end: 1.0).animate(anim1),
                    //移动动画，从左侧进入一直移动到刚好和屏幕坐标顶点吻合
                    child: SlideTransition(
                      position: Tween<Offset>(
                              begin: Offset(-1.25, 0.0), end: Offset(0.0, 0.0))
                          .animate(CurvedAnimation(
                              parent: anim1, curve: Curves.fastOutSlowIn)),
                      child: child,
                    ),
                  ),
                ));
}
