package cyan.cyan_todo.activities;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cyan.cyan_todo.plugins.ToastPlugin;
import cyan.cyan_todo.plugins.TodoDBPlugin;
import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "cyan.todo.main/plugin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);

        createMethodHandler();
    }

    private void createMethodHandler() {
        //这里是flutter调用的通道名，一个通道下可以有多个方法啊
        new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                        //调用的方法名是从flutter传来的，
                        if (methodCall.method.equals(ToastPlugin.FLUTTER_CALL_NAME)) {
                            //传递过来的参数是一个ArrayList
                            ArrayList paraList = (ArrayList) methodCall.arguments;
                            //根据调用的方法名来决定调用真正实现的方法
                            ToastPlugin.showToast(MainActivity.this, (String) paraList.get(0));
                            result.success(null);
//                            int batteryLevel = getBatteryLevel();
//                            if (batteryLevel != -1) {
//                                result.success(batteryLevel);
//                            } else {
//                                result.error("UNAVAILABLE", "Battery level not available.", null);
//                            }
                        } else if (methodCall.method.equals(TodoDBPlugin.FLUTTER_CALL_NAME)) {
                            //传递过来的参数是一个ArrayList
                            ArrayList paraList = (ArrayList) methodCall.arguments;
                            //根据调用的方法名来决定调用真正实现的方法
                            TodoDBPlugin.saveTodo(MainActivity.this, (String) paraList.get(0),
                                    (String) paraList.get(1), (Integer) paraList.get(2),
                                    (String) paraList.get(3),
                                    (String) paraList.get(4));
                            result.success(true);
                        } else if (methodCall.method.equals(TodoDBPlugin.FLUTTER_CALL_NAME_getTodos)) {
                            List<Map<String, String>> listMap = TodoDBPlugin.getTodos(MainActivity.this);
                            result.success(listMap);
                        } else {
                            result.notImplemented();
                        }
                    }
                }
        );
    }
}
