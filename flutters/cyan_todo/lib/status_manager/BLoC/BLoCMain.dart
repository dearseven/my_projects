import 'package:cyan_todo/status_manager/BLoC/BlocProvider.dart';
import 'package:cyan_todo/status_manager/BLoC/IncrementBloc.dart';
import 'package:flutter/material.dart';

/**
 * BLoC是一种消息传递模式
 * 其大概的思路是
 * 事件Widget-->BLoC-->接受消息Widget
 * 这么设计的特色是让BLoC的实现类来处理业务逻辑，而widget只负责传递消息和显示结果
 * 最简单的分析一下
 * 1，BLoCMainApp就是这个UI的入口，home调用的是一个BlocProvider，其实他就是一个封装好的有状态组件
 * 2，IncrementBloc是封装好的接受消息->处理消息->传递结果消息的一个普通类而已
 * 3，核心思想其实就是Stream，BLoC其实一种设计模式
 * 4，就是把业务写在一个独立的BLoC类中，BloCProvider把两者结合在一起，
 * 5，BLoC的类设计的的时候，暴露可以触发发送用户消息的sink，给用户点击操作或者其他逻辑
 * 6，然后也暴露接受结果消息的stream给StringBuilder
 * 7，如果业务发生变化，只要修改BLoC的类就好了分离了组件和业务，而且组件也只需要无状态组件，
 *    这样一来，数据刷新就不用刷新整个widget了
 */
class BLoCMainApp extends StatelessWidget {
  const BLoCMainApp({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Streams Demo',
      theme: new ThemeData(
        primarySwatch: Colors.blue,
      ),
      //使用提供者，提供者其实是一个有状态组件，他一般是要被局部刷新嘛~
      home: BlocProvider<IncrementBloc>(
        bloc: IncrementBloc(),//BLoC
        child: CounterPage(),//无状态UI
      ),
    );
  }
}

//真的UI在这里哈，他可以获得自己的bloc实例,其实就是切小
class CounterPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    /* 
    通过BlocProvider获取IncrementBloc的实例，
    可以用来发送用户事件的消息和让StreamBuilder获取接受结果消息的Stream
    */
    //获取IncrementBloc的一个那个实例，可以去看具体的方法是context.ancestorWidgetOfExactType
    //换句话说，其实一个业务就需要一个BlocBase的实现类这样才不出错啊
    final IncrementBloc bloc = BlocProvider.of<IncrementBloc>(context);

    return Scaffold(
      appBar: AppBar(title: Text('Stream version of the Counter App')),
      body: Center(
        child: StreamBuilder<int>(
            // StreamBuilder控件中没有任何处理业务逻辑的代码
            //接受结果的消息的stream
            stream: bloc.counterAddStream,
            initialData: 0,
            builder: (BuildContext context, AsyncSnapshot<int> snapshot) {
              return Text('You hit me: ${snapshot.data} times');
            }),
      ),
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.add),
        onPressed: () {
          //调用userClickSink，也就是给队列里发一个消息，
          bloc.userClickSink.add(null);
        },
      ),
    );
  }
}
