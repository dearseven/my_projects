import 'dart:async';

import 'package:flutter/material.dart';

/**
 * 这是第1个例子，简单的使用一下Stream
 * Stream 是 Dart 提供的一种数据流订阅管理的"工具"，
 * 感觉有点像 Android 中的 EventBus 或者 RxBus，
 * Stream 可以接收任何对象，包括是另外一个 Stream，
 * 接收的对象通过 StreamController 的 sink 进行添加，
 * 然后通过 StreamController 发送给 Stream，
 * 通过 listen 进行监听，listen 会返回一个 StreamSubscription 对象，
 * StreamSubscription 可以操作对数据流的监听，例如 pause，resume，cancel 等。
 */
class StreamTest extends StatefulWidget {
  StreamTest({Key key}) : super(key: key);

  _StreamTestState createState() => _StreamTestState();
}

class _StreamTestState extends State<StreamTest> {
  StreamController _controller =
      StreamController(); // 创建单订阅类型 `StreamController`
  Sink _sink;
  StreamSubscription _subscription;

  //
  StreamController<int> _controllerInt = StreamController();
  Sink _sinkInt;
  StreamSubscription _subscriptionInt;

  @override
  void initState() {
    super.initState();

    _sink = _controller.sink; // _sink 用于添加数据

    // 添加数据，stream 会通过 `listen` 方法打印
    _sink.add('A');
    // _controller.stream 会返回一个单订阅 stream，
    // 通过 listen 返回 StreamSubscription，用于操作流的监听操作
    _subscription =
        _controller.stream.listen((data) => print('Listener: $data'));
    //_controller.stream.asBroadcastStream()可以返回一个广播型订阅
    //StreamController<int> _controller = StreamController.broadcast();
    _sink.add(11);
    _subscription.pause(); // 暂停监听
    _sink.add(11.16);
    _subscription.resume(); // 恢复监听
    _sink.add([1, 2, 3]);
    _sink.add({'a': 1, 'b': 2});
    //单订阅 Stream 反正就是有监听的时候才会发送数据，没有监听的时候，数据就堵塞了。
    //广播订阅 Stream 则不考虑这点，有数据就发送；

    //----带有逻辑的Stream
    //取大于10的数字的而前五个
    _sinkInt = _controllerInt.sink; //这个controller有泛型，申明是StreamController<int>
    _subscriptionInt = _controllerInt.stream
        .where((value) => value > 10)
        .take(5)
        .listen((data) => print('Listen: $data'));
    List.generate(20, (index) => _sinkInt.add(index));
  }

  @override
  void dispose() {
    super.dispose();
    // 最后要释放资源...
    _sink.close();
    _controller.close();
    _subscription.cancel();
    //
    _sinkInt.close();
    _controllerInt.close();
    _subscriptionInt.cancel();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(),
    );
  }
}
