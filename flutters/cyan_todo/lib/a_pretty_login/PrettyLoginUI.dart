import 'package:flutter/material.dart';
import 'package:groovin_material_icons/groovin_material_icons.dart';

class PrettyLoginPage extends StatefulWidget {
  PrettyLoginPage({Key key}) : super(key: key);

  _PrettyLoginPageState createState() => _PrettyLoginPageState();
}

class _PrettyLoginPageState extends State<PrettyLoginPage> {
  //表单的key
  final _formKey = GlobalKey<FormState>();

  //保存数据的变量
  String _email, _password;

  //密码是否遮掩
  bool _isObscure = true;

  //小眼睛的颜色
  Color _eyeColor;

  //小图标
  List _loginMethod = [
    {
      "title": "facebook",
      "icon": GroovinMaterialIcons.facebook,
    },
    {
      "title": "google",
      "icon": GroovinMaterialIcons.google,
    },
    {
      "title": "twitter",
      "icon": GroovinMaterialIcons.twitter,
    },
  ];

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Scaffold(
        body: Form(
            key: _formKey,
            child: Container(
              //通过Container的decoration来给页面设置了一个大背景~
              padding: const EdgeInsets.all(20.0),
              alignment: Alignment.center,
              decoration: BoxDecoration(
                  image: DecorationImage(
                      //注意这个colorFilter，
                      // 其实大背景是一个彩色图，但是我给了他filter ，所以就变成了，实际中这个很有用
                      colorFilter: ColorFilter.mode(
                          Colors.purple, BlendMode.color),
//ColorFilter.mode(Color.fromARGB(125, 255, 255, 255), BlendMode.color),
                      image: AssetImage('images/login_bg.png'),
                      fit: BoxFit.cover)),
              child: ListView(
                children: <Widget>[
                  SizedBox(
                    child: Container(
                      color: Color.fromARGB(100, 100, 0, 50),
                      child: Text("我就是撑开一个高度"),
                    ),
                    height:
                        kToolbarHeight, //这里是toolbar的高度 是dart的一个const，可以点解进去看
                  ),
                  buildTitle(),
                  buildTitleLine(),
                  SizedBox(height: 70.0),
                  buildEmailTextField(),
                  SizedBox(height: 30.0),
                  buildPasswordTextField(context), //输入框的装饰器用得不错哦
                  buildForgetPasswordText(context), //控件用的是FlatButton
                  SizedBox(height: 60.0),
                  buildLoginButton(context),
                  SizedBox(height: 30.0),
                  buildOtherLoginText(), //采用其他账号登陆 文本
                  buildOtherMethod(
                      context), //返回得最外层是ButtonBar，我没什么印象，这个方法得写法我可以好好学一下
                  buildRegisterText(context), //返回的是Align
                ],
              ),
            )),
      ),
    );
  }

//------------------------------------------------------------------------------------------------------
  //最上方的大Login文字
  Padding buildTitle() {
    return Padding(
      padding: EdgeInsets.all(8.0),
      child: Text(
        'Login',
        style: TextStyle(fontSize: 42.0),
      ),
    );
  }

//在login下方提供一个小横线
  Padding buildTitleLine() {
    return Padding(
      padding: EdgeInsets.only(left: 32.0, top: 4.0),
      child: Align(
        alignment: Alignment.bottomLeft,
        child: Container(
          color: Colors.black,
          width: 40.0,
          height: 2.0,
        ),
      ),
    );
  }

  //设置email的输入区域
  Padding buildEmailTextField() {
    return Padding(
        padding: EdgeInsets.only(left: 20, right: 20),
        child: TextFormField(
          decoration: InputDecoration(
            labelText: 'Emall Address',
          ),
          validator: (String value) {
            var emailReg = RegExp(
                r"[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?");
            if (!emailReg.hasMatch(value)) {
              return '请输入正确的邮箱地址';
            }
          },
          onSaved: (String value) => _email = value,
        ));
  }

//忘记密码的文本，这里用的FlatButton
  Padding buildForgetPasswordText(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(top: 8.0),
      child: Align(
        alignment: Alignment.centerRight,
        child: FlatButton(
          child: Text(
            '忘记密码？',
            style: TextStyle(fontSize: 14.0, color: Colors.grey),
          ),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
      ),
    );
  }

  //输入密码的框框
  Padding buildPasswordTextField(BuildContext context) {
    return Padding(
        padding: EdgeInsets.only(left: 20, right: 20),
        child: TextFormField(
          onSaved: (String value) => _password = value,
          obscureText: _isObscure,
          validator: (String value) {
            if (value.isEmpty) {
              return '请输入密码';
            }
          },
          decoration: InputDecoration(
              labelText: 'Password',
              suffixIcon: IconButton(
                  icon: Icon(
                    Icons.remove_red_eye,
                    color: _eyeColor,
                  ),
                  onPressed: () {
                    setState(() {
                      _isObscure = !_isObscure;
                      _eyeColor = _isObscure
                          ? Colors.grey
                          : Theme.of(context).iconTheme.color;
                    });
                  })),
        ));
  }

  Align buildLoginButton(BuildContext context) {
    return Align(
      child: SizedBox(
        height: 45.0,
        width: 270.0,
        child: RaisedButton(
          child: Text(
            'Login',
            style: Theme.of(context).primaryTextTheme.headline,
          ),
          color: Colors.black,
          onPressed: () {
            if (_formKey.currentState.validate()) {
              ///只有输入的内容符合要求通过才会到达此处
              _formKey.currentState.save();
              //TODO 执行登录方法
              print('email:$_email , assword:$_password');
            }
          },
          shape: StadiumBorder(side: BorderSide()),
        ),
      ),
    );
  }

  Align buildOtherLoginText() {
    return Align(
        alignment: Alignment.center,
        child: Text(
          '其他账号登录',
          style: TextStyle(color: Colors.grey, fontSize: 14.0),
        ));
  }

  ButtonBar buildOtherMethod(BuildContext context) {
    return ButtonBar(
      alignment: MainAxisAlignment.center,
      children: _loginMethod
          .map((item) => Builder(
                builder: (context) {
                  return IconButton(
                      icon: Icon(item['icon'],
                          color: Theme.of(context).iconTheme.color),
                      onPressed: () {
                        //TODO : 第三方登录方法
                        Scaffold.of(context).showSnackBar(new SnackBar(
                          content: new Text("${item['title']}登录"),
                          action: new SnackBarAction(
                            label: "取消",
                            onPressed: () {
                              print("${item['title']}点击了");
                            },
                          ),
                        ));
                      });
                },
              ))
          .toList(),
    );
  }

  Align buildRegisterText(BuildContext context) {
    return Align(
      alignment: Alignment.center,
      child: Padding(
        padding: EdgeInsets.only(top: 10.0),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text('没有账号？'),
            GestureDetector(
              child: Text(
                '点击注册',
                style: TextStyle(color: Colors.green),
              ),
              onTap: () {
                //TODO 跳转到注册页面
                print('去注册');
                Navigator.pop(context);
              },
            ),
          ],
        ),
      ),
    );
  }
}
