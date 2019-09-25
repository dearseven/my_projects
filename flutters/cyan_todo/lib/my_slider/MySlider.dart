import 'package:flutter/material.dart';

/**
 *一个slider的示例
 */
class MySlider extends StatefulWidget {
  MySliderState state = null;

  double height = 40;
  double totalWidth = 300;
  double percentage = 0.0;
  Color positiveColor;
  Color negetiveColor;

  MySlider({Key key,this.percentage, this.positiveColor, this.negetiveColor}):super(key: key);

  @override
  State<StatefulWidget> createState() {
    state = MySliderState(
        totalWidth: totalWidth,
        height: height,
        percentage: percentage,
        positiveColor: positiveColor,
        negetiveColor: negetiveColor);
    return state;
  }

  void setProgress(double mySliderPercentage) {
    state.setProgress(mySliderPercentage);
  }
}

class MySliderState extends State<MySlider> {
  double totalWidth = 0;
  double height = 0;
  double percentage = 0.0;
  Color positiveColor;
  Color negetiveColor;

  MySliderState(
      {this.totalWidth,
      this.height,
      this.percentage,
      this.positiveColor,
      this.negetiveColor}) {}

  @override
  Widget build(BuildContext context) {
    return Container(
      height: height,
      width: totalWidth + 4.0,
      decoration: BoxDecoration(
          color: negetiveColor,
          border: Border.all(color: Colors.black87, width: 2.0)),
      child: Row(
        mainAxisSize: MainAxisSize.max,
        children: <Widget>[
          Container(
            color: positiveColor,
            width: (percentage / 100) * totalWidth,
          )
        ],
      ),
    );
  }

  void setProgress(double mySliderPercentage) {
    setState(() {
      percentage = mySliderPercentage;
    });
  }
}
