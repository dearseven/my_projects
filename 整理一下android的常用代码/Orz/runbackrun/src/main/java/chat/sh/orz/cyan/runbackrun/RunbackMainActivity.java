package chat.sh.orz.cyan.runbackrun;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import chat.sh.orz.cyan.runbackrun._jobscheduler.CJobService;

/**
 *  原本最小的间隔都要15分钟,
 *  但是用                    .setMinimumLatency(5000)// 设置任务运行最少延迟时间
 *  不过接下来到底多久唤醒一次还是要看系统
 */
public class RunbackMainActivity extends AppCompatActivity {
    private ComponentName jobService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runback_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initJobService();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initJobService() {
        //先启动服务
        Intent service = new Intent(this, CJobService.class);
        startService(service);
        //设置job
        jobService = new ComponentName(this, CJobService.class);
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int jobId;
        for (int i = 0; i < 3; i++) {
            jobId = i;
            JobInfo jobInfo = new JobInfo.Builder(jobId, jobService)
                   // .setPeriodic(3000)//
                    .setMinimumLatency(5000)// 设置任务运行最少延迟时间
                    //Can't call setOverrideDeadline() on a periodic job.
                    .setOverrideDeadline(10000)// 设置deadline，若到期还没有达到规定的条件则会开始执行
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)// 设置网络条件
                    .setRequiresCharging(true)// 设置是否充电的条件
                    .setRequiresDeviceIdle(false)// 设置手机是否空闲的条件
                    .setPersisted(true) // 设置是否重启后继续调度，注意设置true是需要添加重启权限
                    .build();
            //result.append("scheduling job " + i + "!\n");
            scheduler.schedule(jobInfo);
        }

    }
}
