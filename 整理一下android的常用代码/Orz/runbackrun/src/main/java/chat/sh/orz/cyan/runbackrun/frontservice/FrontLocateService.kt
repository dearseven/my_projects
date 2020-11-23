package chat.sh.orz.cyan.runbackrun.frontservice

import android.app.*
import android.content.ComponentName
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import chat.sh.orz.cyan.runbackrun.R


/**2020新版本*/
class FrontLocateService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.also {
            it.action?.also { action ->
                if (action.equals("stop")) {
//                    stopLocation()
                    stopForeground(true)
                    stopSelf()
                } else if (action.equals("start")) {
                    //startLocation();
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }



    //------------------------------------------------------下面都是开启前台服务------------------------------------------------------------
    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    var notification: Notification? = null
    var notificationManager: NotificationManager? = null
    val notifyId = 1001

    companion object {
        val NAME = "com.teetaa.outsourcing.carinspection.locate.FrontLocateService"

        //
        val SERVICE_ID = "FrontLocateService1"
        val SERVICE_NAME = "FrontLocateService2"
        val PACKAGE_NAME = "com.teetaa.outsourcing.carinspection"
        val CHANNEL_ID = "FrontLocateService1"
        val startForegroundID = 10001

        //轮询
        val POLLING = "POLLING_CHAT"

        fun startFrontServices(activity: AppCompatActivity) {
            Intent(activity, FrontLocateService::class.java).also {
                it.action = "start"
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    activity.startForegroundService(it)
                } else {
                    activity.startService(it)
                }
            }
        }

        fun stopFrontServices(activity: AppCompatActivity) {
            Intent(activity, FrontLocateService::class.java).also {
                it.action = "stop"
                activity.startService(it)
            }
        }
    }


    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //var notification: Notification? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8
            val channel =
                NotificationChannel(CHANNEL_ID, SERVICE_NAME, NotificationManager.IMPORTANCE_HIGH)

            notificationManager?.createNotificationChannel(channel)
            val com =
                ComponentName(packageName, "com.teetaa.outsourcing.carinspection.MainActivity")
            val intentForeSerive = Intent()
            intentForeSerive.setPackage(packageName)
            intentForeSerive.component = com
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intentForeSerive,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("车检")
                .setContentText("Running")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        getResources(),
                        R.mipmap.ic_launcher
                    )
                )
                .setContentIntent(pendingIntent)
                .let {
                    if (android.os.Build.VERSION.SDK_INT >= 16) {
                        it.build();
                    } else {
                        it.getNotification();
                    }
                }
        } else {
            //  val channel = NotificationChannel(CHANNEL_ID, SERVICE_NAME, NotificationManager.IMPORTANCE_HIGH)
            // notificationManager.createNotificationChannel(channel)
            val com = ComponentName(
                packageName,
                "com.teetaa.intellivusdemo3.activities.TransferProgressActivity"
            )
            val intentForeSerive = Intent()
            intentForeSerive.setPackage(packageName)
            intentForeSerive.component = com
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intentForeSerive,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("车检")
                .setContentText("Running")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        getResources(),
                        R.mipmap.ic_launcher
                    )
                )
                .setContentIntent(pendingIntent).let {
                    if (android.os.Build.VERSION.SDK_INT >= 16) {
                        it.build();
                    } else {
                        it.getNotification();
                    }
                }
        }

        notification?.also {
            startForeground(startForegroundID, notification);
            //notificationManager?.notify(notifyId, it)
        }
    }


}
