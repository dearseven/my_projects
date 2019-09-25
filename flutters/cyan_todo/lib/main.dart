import 'package:cyan_todo/index/MainIndexListComponent.dart';
import 'package:cyan_todo/util/MyRouter.dart';
import 'package:flutter/material.dart';
import 'package:cyan_todo/addTodo/AddTodo.dart';

void main() {
  runApp(MaterialApp(
    home: MyApp(),
    debugShowCheckedModeBanner: false, // 设置这一属性即可
  ));
}

class MyApp extends StatelessWidget {
  MainIndexList mainList = MainIndexList();

  @override
  Widget build(BuildContext context) {
    //Scaffold是Material中主要的布局组件.
    return Scaffold(
      appBar: AppBar(
        title: Text('Cyan TODO'),
      ),
      //body占屏幕的大部分
      body: mainList,
      floatingActionButton: FloatingActionButton(
        tooltip: 'Add', // used by assistive technologies
        child: Icon(Icons.add),
        //跳转的新增提醒页面
        onPressed: () async {
          //result是上个页面返回的数据
          // final result = await Navigator.of(context).push(
          //   new MaterialPageRoute(builder: (context) => new AddTodoUI()),
          // );

          //这里是自己实现的路由
          final result =
              await Navigator.of(context).push(MyRouter(AddTodoUI()));

          //打印一下返回的数据
          print("from AddTodo resut：${result}");
          //调用写在State里的方法刷新数据
          (mainList.getState() as MainIndexListState).refresh();
        },
      ),
    );
  }
}
