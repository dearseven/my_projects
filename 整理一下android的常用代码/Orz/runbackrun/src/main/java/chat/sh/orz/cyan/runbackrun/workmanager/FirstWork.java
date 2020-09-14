package chat.sh.orz.cyan.runbackrun.workmanager;

import androidx.annotation.NonNull;

import android.content.Context;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class FirstWork extends Worker {
    public static final String TAG = "FirstWork";

    public FirstWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {

        String str = this.getInputData().getString("demo");
        Log.i(TAG, "FirstWork执行了哦:" + str);
        return Result.success();
    }
}
