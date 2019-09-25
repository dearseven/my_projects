import 'package:flutter/material.dart';

/**
 * 其会缩放自己，导致子widget也会跟着改变大小。
 */
class ScaleAbleSizedBox extends StatefulWidget {
  Widget forScaleChild; //实际显示的widget
  double fraction = 1;

  ScaleAbleSizedBox({Widget this.forScaleChild, Key key}) : super(key: key);

  _ScaleAbleSizedBoxState createState() => _ScaleAbleSizedBoxState();
}

class _ScaleAbleSizedBoxState extends State<ScaleAbleSizedBox>
    with SingleTickerProviderStateMixin {
  double _fraction;
  //动画控制器
  AnimationController _animationController;
  Animation _scaleAnimation; // 用于控制大小

  @override
  void initState() {
    super.initState();
    _fraction = widget.fraction;

    _animationController =
        AnimationController(vsync: this, duration: Duration(milliseconds: 100));
    // 当动画值发生变化的时候，重绘
    _animationController.addListener(() {
      setState(() {
        //通过动画的值来修改_fraction
        _fraction = _scaleAnimation.value;
      });
    });

    // 通过 `chain` 结合 `CurveTween` 修改动画的运动方式，曲线类型可自行替换
    _scaleAnimation = Tween(begin: 1, end: 0.95)
        .chain(CurveTween(curve: Curves.linear))
        .animate(_animationController);
  }

  @override
  void dispose() {
    //*一定要释放资源啊
    _animationController.dispose();
    super.dispose();
  }

  /**
   * 反正我发现要做这个缩放，需要2个可以设置大小的widget，而且不能两个都是SizedBox，
   * 外层是SizedBox，里层是FractionallySizedBox，
   */
  @override
  Widget build(BuildContext context) {
    //这个sizebox本来就稍微小一点，0.975这么大，这个就是最外层的widget
    return SizedBox(
      width:
          MediaQuery.of(context).size.width * 0.975, //3 / 2.6, //横纵比 宽高比  3:2.6
      height: MediaQuery.of(context).size.width * (2.6 / 3) * 0.975,
      child: GestureDetector(
        child: FractionallySizedBox(
          //widget.forScaleChild这个是实际显示的widget，他是 FractionallySizedBox的child
          child: widget.forScaleChild,
          //这个表示FractionallySizedBox他是SizedBox的大小的多少，
          // _fraction是缩放因子，换句话说，FractionallySizedBox这个widget会被缩放
          widthFactor: _fraction,
          heightFactor: _fraction,
        ),
        onPanDown: (details) {
          print("onPanDown");

          // setState(() {
          //   //sizebox的0.95，执行按下的时候
          //   _fraction = 0.95;
          // });
          _animationController.forward();
        },
        onPanCancel: () {
          print("onPanCancel");

          // setState(() {
          //   //还原的时候 1表示还原到和SizedBox一样大
          //   _fraction = 1;
          // });
          _animationController.reverse();
        },
        onPanEnd: (d) {
          print("onPanCancel");

          // setState(() {
          //   //还原的时候 1表示还原到和SizedBox一样大
          //   _fraction = 1;
          // });
          _animationController.reverse();
        },
      ),
    );
  }
}
