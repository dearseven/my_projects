package chat.sh.orz.cyan.runbackrun.frontservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.*
import chat.sh.orz.cyan.runbackrun.FrontServiceActivity
import androidx.core.app.BundleCompat.getBinder
import androidx.core.app.NotificationCompat
import android.util.Log
import chat.sh.orz.cyan.runbackrun.R

/**
 * 前台Service
 */
class CFrontService : Service() {
    companion object {
        val TAG = "CFrontService"
    }

    //Activity端的Messenger对象,在handler中得到(msg.replyTo)
    private var mActivityMessenger: Messenger? = null
    private var handler: Handler? = null
    //Service端的Messenger对象,在initMessageHander初始化然后onBind中返回binder到activity
    private var mServiceMessenger: Messenger? = null

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate()")
        initMessageHander()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8
            val channel =  NotificationChannel("fore_service", "前台服务", NotificationManager.IMPORTANCE_HIGH)
            val notificationManager =  getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            val com=(ComponentName("chat.sh.orz.cyan.orz", "chat.sh.orz.cyan.orz.main.FunctionActivity"))
            val intentForeSerive = Intent()
            intentForeSerive.setPackage(packageName)
            intentForeSerive.setComponent(com)
            val pendingIntent = PendingIntent.getActivity(this, 0, intentForeSerive, 0)
            val notification =  NotificationCompat.Builder(this, "fore_service")
                    .setContentTitle("This is content title")
                    .setContentText("This is content text")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1, notification);
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }




    /**
     *返回用于通信的message的binder,在initMessageHander方法中初始化
     */
    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "onBind()")
        return mServiceMessenger!!.binder
    }
//---------------------------------------------------------------
    /**
     * 1初始化一个handler可以接受activity的messager赋值给mActivityMessenger
     * 2初始化mServiceMessenger
     * ===========
     * HandlerThread是Android系统专门为Handler封装的一个线程类，
     * 通过HandlerThread创建的Handler便可以进行耗时操作了
     * HandlerThread是一个子线程,在调用handlerThread.getLooper()之前必须先执行
     * HandlerThread的start方法。
     */
    private fun initMessageHander() {
        val handlerThread = HandlerThread("serviceCalculate")
        handlerThread.start()
        handler = object : Handler(handlerThread.looper) {
            override fun handleMessage(msg: Message) {
                if (msg.what == 0x21) {
                    if (mActivityMessenger == null) {
                        mActivityMessenger = msg.replyTo
                        Log.i(TAG, "0x21 mActivityMessenger:${mActivityMessenger} ASSIGNMENTED!")
                    }
                } else if (msg.what == 0x22) {
                    mActivityMessenger=null;
                    Log.i(TAG, "0x22 mActivityMessenger:${mActivityMessenger} removed!")

                } else if (msg.what === 0x11) {
                    //接受传过来的mActivityMessenger,这里不判定空了,因为可能是多次传递
                    if (mActivityMessenger == null) {
                        mActivityMessenger = msg.replyTo
                        Log.i(TAG, "0x11 mActivityMessenger:${mActivityMessenger} ASSIGNMENTED!")
                    }
                    //模拟耗时任务
                    try {
                        Thread.sleep(10000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    //发送结果回Activity
                    val message = this.obtainMessage()
                    message.what = 0x12
                    message.arg1 = msg.arg1 + msg.arg2
                    try {
                        mActivityMessenger?.send(message)
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        mServiceMessenger = Messenger(handler)
    }
}
