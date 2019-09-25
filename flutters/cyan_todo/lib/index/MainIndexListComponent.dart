import 'package:cyan_todo/beans/TodoItem.dart';
import 'package:cyan_todo/index/IndexDataProvider.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class MainIndexList extends StatefulWidget {
//  @override
//  State<StatefulWidget> createState() => MainIndexListState();
  State<StatefulWidget> state=null;

  State<StatefulWidget> getState(){
    return state;
  }
  @override
  State<StatefulWidget> createState() {
     state= MainIndexListState();
     return state;
  }
}

class MainIndexListState extends State<MainIndexList> {
  List<TodoItem> _list = List();
  ScrollController _scrollController = ScrollController(); //listview的控制器
  @override
  void initState() {
    super.initState();
    Future<List<TodoItem>> listFuture =
        IndexDataProvider().getTodoItem(_list.length.toDouble());
    listFuture.then((list) {
      list.forEach((x) {
        _list.add(x);
      });
      setState(() {
        _list;
      });
    });
    _scrollController.addListener(() {
      if (_scrollController.position.pixels ==
          _scrollController.position.maxScrollExtent) {
        print('滑动到了最底部2');
        _getMore();
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      controller: _scrollController,
      itemCount: _list.length,
      itemExtent: 50.0, //强制高度为50.0
      itemBuilder: (BuildContext context, int index) {
        return ListTile(
          leading: Text(_list[index].title.substring(0, 1)),
          title: Text(
            _list[index].title,
            style: TextStyle(
                color: _list[index].isComplete ? Colors.grey : Colors.black),
          ),
          trailing: Checkbox(
            value: _list[index].isComplete,
            onChanged: (bool newValue) {
              setState(() {
                _list[index].isComplete = !_list[index].isComplete;
              });
            },
          ),
        );
      },
    );
  }

  Future _getMore() async {
    await Future.delayed(Duration(seconds: 2), () {
      setState(() {
        Future<List<TodoItem>> listFuture =
            IndexDataProvider().getTodoItem((_list.length).toDouble());
        listFuture.then((list) {
          print(list.length);
          list.forEach((x) {
            _list.add(x);
          });
        });
      });
    });
  }

  void refresh() {
    _list.clear();
    Future<List<TodoItem>> listFuture =
        IndexDataProvider().getTodoItem(_list.length.toDouble());
    listFuture.then((list) {
      list.forEach((x) {
        _list.add(x);
      });
      setState(() {
        _list;
      });
    });
  }
}
