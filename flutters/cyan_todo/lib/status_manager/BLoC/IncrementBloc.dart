import 'package:flutter/material.dart';
import 'dart:async';

/**
 * 处理业务逻辑的BLoC类，其实dart本身并没有BlocBase这个超类，是我定义的
 */
class IncrementBloc extends BlocBase {
  int _counter;

  // 处理counter++的stream（把事件传出去，暴露counterAddStream）
  StreamController<int> _counterAddController = StreamController<int>();
  StreamSink<int> get _counterAddSink => _counterAddController.sink;
  //这个stream被是要传递出去的，他会监听_handleLogic方法里调用的_counterAddSink放进去的数据
  Stream<int> get counterAddStream => _counterAddController.stream;

  // 处理业务逻辑（用户点击+按钮）的stream（外部传进来的）
  StreamController _userClickController = StreamController();
  StreamSink get userClickSink => _userClickController.sink;

  // 构造器
  IncrementBloc() {
    _counter = 0;
    //接听用户点击按钮的时候发送过来的消息，然后调用_handleLogic方法
    _userClickController.stream.listen(_handleLogic);
  }

  @override
  void dispose() {
    _userClickController.close();
    _counterAddController.close();
  }

/**
 * 当_userClickController.stream接受到消息以后，调用这个方法
 * 发送消息到接收者
 */
  void _handleLogic(data) {
    //自增1
    _counter = _counter + 1;
    //这个是把自增好的结果放到队列
    _counterAddSink.add(_counter);
  }
}

/**
 * 所有 BLoCs 的通用接口
 */
abstract class BlocBase {
  void dispose();
}
