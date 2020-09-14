package chat.sh.orz.cyan.runbackrun;

import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import chat.sh.orz.cyan.runbackrun.workmanager.FirstWork;

/**
 * 最小的间隔都要15分钟哦~~~用WorkManager,
 * 过接下来到底多久唤醒一次还是要看系统
 * 定时任务有最小间隔时间的限制，是 15 分钟
 * 只有程序运行时，任务才会得到执行
 * 无法拉起 Activity
 */
public class WorkManagerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager_main);
        initWork();
    }

    private void initWork() {
        //1.创建约束
        Constraints.Builder builder = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)  // 网络状态
                .setRequiresBatteryNotLow(true)                 // 不在电量不足时执行
                .setRequiresCharging(true)                      // 在充电时执行
                .setRequiresStorageNotLow(true);                // 不在存储容量不足时执行
        // 只在待机状态下执行，需要 min API>= 23,所以要置为false啦
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.setRequiresDeviceIdle(false);
        }
        Constraints constraints = builder.build();
        //2.传入参数
        Data data = new Data.Builder().putString("demo", "helloworld").build();
        //3.构造work
        // OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(FirstWork.class).setConstraints(constraints).setInputData(data).build();
        PeriodicWorkRequest work = new PeriodicWorkRequest.Builder(FirstWork.class, 15, TimeUnit.MINUTES).setConstraints(constraints).setInputData(data).build();
        //4.放入执行队列
        WorkManager.getInstance().enqueue(work);
    }
}
