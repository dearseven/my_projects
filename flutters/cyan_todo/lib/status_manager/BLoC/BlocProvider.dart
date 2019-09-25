import 'package:cyan_todo/status_manager/BLoC/IncrementBloc.dart';
import 'package:flutter/material.dart';

/**
 * 其实这就是一个包括了BLoC实现类和View的有状态组件
 * 他的child属性用来显示，他的bloc属性用来控制逻辑，他就是一个组合体~
 */
class BlocProvider<T extends BlocBase> extends StatefulWidget {
  BlocProvider({
    Key key,
    @required this.child,
    @required this.bloc,
  }) : super(key: key);

  final T bloc;
  final Widget child;

  @override
  State<StatefulWidget> createState() {
    return _BlocProviderState<T>();
  }
  //_BlocProviderState<T> createState() => _BlocProviderState<T>();

  static T of<T extends BlocBase>(BuildContext context) {
    // 获取当前 Bloc 的类型
    final type = _typeOf<BlocProvider<T>>();
    // 通过类型获取相应的 Provider，再通过 Provider 获取 bloc 实例
    // 比如列子中的类型是IncrementBloc，所以其实需要很多个BlocBase的实现哦~如果要做很多不同的业务
    BlocProvider<T> provider = context.ancestorWidgetOfExactType(type);
    return provider.bloc;
  }

  static Type _typeOf<T>() => T;
}

class _BlocProviderState<T> extends State<BlocProvider<BlocBase>> {
  @override
  void dispose() {
    widget.bloc.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return widget.child;
  }
}
