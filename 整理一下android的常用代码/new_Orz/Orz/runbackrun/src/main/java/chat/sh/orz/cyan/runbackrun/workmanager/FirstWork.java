package chat.sh.orz.cyan.runbackrun.workmanager;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

public class FirstWork extends Worker {
    public static final String TAG = "FirstWork";


    @NonNull
    @Override
    public Result doWork() {
        String str = this.getInputData().getString("demo");
        Log.i(TAG, "FirstWork执行了哦:" + str);
        return Result.SUCCESS;
    }
}
