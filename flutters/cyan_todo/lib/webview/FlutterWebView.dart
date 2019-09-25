import 'package:flutter/material.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';

class WebViewDemo extends StatefulWidget {
  WebViewDemo({Key key}) : super(key: key);

  _WebViewDemoState createState() => _WebViewDemoState();
}

class _WebViewDemoState extends State<WebViewDemo> {
  TextEditingController controller = TextEditingController();
  FlutterWebviewPlugin flutterWebviewPlugin = FlutterWebviewPlugin();
  var urlString = "http://www.baidu.com";

  launchUrl() {
    print("launchUrl:${controller.text}");
    setState(() {
      urlString = controller.text;
      flutterWebviewPlugin.reloadUrl("http://${urlString}");
    });
  }

  @override
  void initState() {
    super.initState();

    flutterWebviewPlugin.onStateChanged.listen((WebViewStateChanged wvs) {
      print("WebViewStateChanged:${wvs.type}");
    });

    //  监听url地址改变事件
    flutterWebviewPlugin.onUrlChanged.listen((String url) {});

    flutterWebviewPlugin.onScrollYChanged.listen((double offsetY) {});

    flutterWebviewPlugin.onScrollXChanged.listen((double offsetX) {});

//隐藏webview：flutterWebviewPlugin.launch(url, hidden: true);
//webview：flutterWebviewPlugin.close();
  }

  @override
  Widget build(BuildContext context) {
    return WebviewScaffold(
      appBar: AppBar(
        title: TextField(
          autofocus: false,
          controller: controller,
          textInputAction: TextInputAction.go,
          onSubmitted: (url) => launchUrl(),
          style: TextStyle(color: Colors.white),
          decoration: InputDecoration(
            border: InputBorder.none,
            hintText: "Enter Url Here",
            hintStyle: TextStyle(color: Colors.white),
          ),
        ),
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.navigate_next),
            onPressed: () => launchUrl(),
          )
        ],
      ),
      url: urlString,
      withZoom: false,
    );
  }
}
