import 'package:flutter/material.dart';

class DraggableOne extends StatelessWidget {
  final data;
  const DraggableOne({this.data = "my draggle", Key key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "draggable demo",
      theme: ThemeData(primaryColor: Colors.blue),
      home: Scaffold(
        appBar: AppBar(
          title: Text("draggable"),
        ),
        body: Container(
          child: Draggable(
            data: data,
            child: Container(
              width: 150.0,
              height: 150.0,
              color: Colors.yellow[500],
              child: Center(
                  // child: Text("DRAGGABLE"),
                  ),
            ),
            feedback: Container(
              width: 150.0,
              height: 150.0,
              color: Colors.blue[500],
              child: Icon(Icons.feedback),
            ),
          ),
        ),
      ),
    );
  }
}
