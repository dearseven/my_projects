package chat.sh.orz.cyan.runbackrun._jobscheduler

import android.annotation.SuppressLint
import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import android.util.SparseArray


/**
 * android L(5)以上用这个来执行一些低等级的后台服务
 */
@SuppressLint("NewApi")
class CyanJobService : JobService() {
    // Cancell JobScheduler
    var mCurrentId = 0
    private val mJobParametersMap = SparseArray<JobParameters>()

    /**
     *
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * false: 该系统假设任何任务运行不需要很长时间并且到方法返回时已经完成。
     * true: 该系统假设任务是需要一些时间并且当任务完成时需要调用jobFinished()告知系统。
     */
    override fun onStartJob(param: JobParameters?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 当收到取消请求时，该方法是系统用来取消挂起的任务的。
     * 如果onStartJob()返回false，则系统会假设没有当前运行的任务，故不会调用该方法。
     */
    override fun onStopJob(param: JobParameters?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    //job----------




}
