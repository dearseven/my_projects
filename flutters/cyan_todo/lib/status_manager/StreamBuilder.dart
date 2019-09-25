import 'dart:async';

import 'package:flutter/material.dart';

/**
 *这是第2个例子，简单的使用一下采用StreamBuilder监听数据
 * 在第一个例子-Stream.dart中我并没有刷新数据到UI，如果有也要用
 * setState({
 * })这种方式，这样会刷新整个UI。
 * StreamBuilder可以刷新部分UI
 * ---官方述说
 * StreamBuilder其实是一个StatefulWidget，它通过监听Stream，发现有数据输出时，自动重建，调用builder方法。vb
 */
class StreamBuilderTest extends StatefulWidget {
  StreamBuilderTest({Key key}) : super(key: key);

  _StreamBuilderTestState createState() => _StreamBuilderTestState();
}

class _StreamBuilderTestState extends State<StreamBuilderTest> {
// 定义一个全局的 `StreamController`
  StreamController<int> _controller = StreamController.broadcast();
  // `sink` 用于传入新的数据
  Sink<int> _sink;
  int _counter = 99;

  @override
  void initState() {
    super.initState();
    //初始化sink
    _sink = _controller.sink;
  }

  @override
  void dispose() {
    super.dispose();
    // 需要销毁资源
    _sink.close();
    _controller.close();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
          child: Container(
        alignment: Alignment.center,
        //！！！！！自动更新Text
        child: StreamBuilder(
          builder: (_, snapshot) =>
              Text('${snapshot.data}', style: TextStyle(fontSize: 24.0)),
          stream: _controller.stream, // stream 在 StreamBuilder 销毁的时候会自动销毁，
          //这个是接受事件结果的stream
          //复杂一点的可能会设计2个stream，比如BLoCMain里的设计，反正其实道理都是一样
          //Controller的sink发消息，stream接消息~
          initialData: _counter,
        ),
      )),
      // 通过 `sink` 传入新的数据，去通知 `stream` 更新到 builder 中
      floatingActionButton: FloatingActionButton(
        onPressed: () => _sink.add(_counter++),
        child: Icon(Icons.add),
      ),
    );
  }
}
