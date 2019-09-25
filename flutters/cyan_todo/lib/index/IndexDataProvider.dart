import 'package:cyan_todo/beans/TodoItem.dart';
import 'package:flutter/services.dart';

class IndexDataProvider {
  static final IndexDataProvider _singleton = new IndexDataProvider._internal();
  //和原生交互的通道
  static const platform = const MethodChannel("cyan.todo.main/plugin");

  /**
   * 这样就可以返回一个单例哦
   * new IndexDataProvider() hhh
   */
  factory IndexDataProvider() {
    return _singleton;
  }

  IndexDataProvider._internal();

//----------------------------------------
  Future<List<TodoItem>> getTodoItem(double index) async {
    List<TodoItem> list = List();
    if (index == 0) {
      //暴力的从sqlite中拿数据
      //从原生获取数据 原生的是List<Map<String,String>>
      List<Map> listMap =
      await platform.invokeListMethod("TodoSavePlugin_todo_getTodos");
      for (int i = 0; i < listMap.length; i++) {
        Map m = listMap[i];
        list.add(TodoItem((100000 + i).toDouble(), m["col_title"], 1555732803000,  i % 3 == 0));
        print(m["col_title"]);
      }
    }
    int size = 30;
    double end = index + size;
    for (double i = (index); i < end; i++) {
      list.add(TodoItem(i, "提醒$i", 1555732803000, i % 2 == 0));
    }
    return list;
  }
}
