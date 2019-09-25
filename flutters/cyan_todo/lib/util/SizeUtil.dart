import 'dart:ui';

import 'package:flutter/material.dart';

/**
 * 返回各种宽高,都是四舍五入以后的double类型
 */
class SizeUtil {
  /**
   * 获取logical pixel width
   */
  double getScreenWidth(BuildContext context) {
    return MediaQuery.of(context).size.width.round().toDouble();
  }

  /**
   * 获取logical pixel height
   */
  double getScreenHeight(BuildContext context) {
    return MediaQuery.of(context).size.height.round().toDouble();
  }

  /**
   * 返回物理高
   */
  double getPhysicScreenHeight(Window window) {
    return window.physicalSize.height.round().toDouble();
  }

  /**
   * 返回物理宽
   */
  double getPhysicScreenWidth(Window window) {
    return window.physicalSize.width.round().toDouble();
  }

  /**
   * 返回状态栏的高度
   */
  double getStatuBarHeight(Window window){
    return MediaQueryData.fromWindow(window).padding.top.round().toDouble();
  }
}
