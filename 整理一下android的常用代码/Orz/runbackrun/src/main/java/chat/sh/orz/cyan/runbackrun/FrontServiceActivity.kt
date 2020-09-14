package chat.sh.orz.cyan.runbackrun

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import java.lang.ref.WeakReference
import android.widget.Toast
import chat.sh.orz.cyan.runbackrun.frontservice.CFrontService


class FrontServiceActivity : AppCompatActivity() {
    //Service端的Messenger对象,通过onServiceConnected得到
    private var mServiceMessenger: Messenger? = null
    //Activity端的Messenger对象,在onServiceConnected后传递给service
    private var mActivityMessenger: Messenger? = null
    //
    private var connect: ServiceConnection? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front_service)
        initService();
    }

    private fun initService() {
        if(mActivityMessenger == null) {
            mActivityMessenger =  Messenger(h);
        }

        //开启服务
        val intent = Intent(this@FrontServiceActivity, CFrontService::class.java)
        intent.setPackage(packageName)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8不允许开启后台服务
            startForegroundService(intent)
        } else {
            startService(intent);
        }
        //初始化绑定的对象
        connect = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                mServiceMessenger=null;
                Log.i(CFrontService.TAG, "onServiceDisconnected mServiceMessenger removed!")
            }

            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                //获取service的通信对象
                mServiceMessenger = Messenger(binder);
                Log.i(CFrontService.TAG, "onServiceConnected mServiceMessenger ${mServiceMessenger} assignmented!")

                //
                var message = Message.obtain();
                message.what = 0x11;
                message.arg1 = 2016;
                message.arg2 = 1;
                //设定消息要回应的Messenger
                message.replyTo = mActivityMessenger;
                //通过ServiceMessenger将消息发送到Service中的Handler
                mServiceMessenger?.send(message)
            }
        }
        //绑定
        bindService(intent, connect, Service.BIND_AUTO_CREATE);
    }

    override fun onStart() {
        if (mServiceMessenger != null) {
            var message = Message.obtain();
            message.what = 0x21;
            //设定消息要回应的Messenger
            message.replyTo = mActivityMessenger;
            //通过ServiceMessenger将消息发送到Service中的Handler
            mServiceMessenger?.send(message)
        }
        super.onStart()
    }

    override fun onStop() {
        if (mServiceMessenger != null) {
            var message = Message.obtain();
            message.what = 0x22;//移除mActivityMessenger
            //通过ServiceMessenger将消息发送到Service中的Handler
            mServiceMessenger?.send(message)
        }
        super.onStop()
    }


    override fun onDestroy() {
        unbindService(connect)
        connect=null
        super.onDestroy()
    }

    //------handler----------
    private val h = object : WH(this@FrontServiceActivity) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg?.what == 0x12) {
                Toast.makeText(this@FrontServiceActivity, "Service发送过来的结果是......" + msg.arg1, Toast.LENGTH_SHORT).show();
            }
        }
    }

    open class WH : Handler {
        var activity: WeakReference<FrontServiceActivity>? = null;

        constructor(activity: FrontServiceActivity) : super() {
            this.activity = WeakReference(activity);
        }
    }
}
