package app.cyan.cyanalbum.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.cyan.cyanalbum.R;
import app.cyan.cyanalbum.beans.TimeShow;
import app.cyan.cyanalbum.databinding.ActivityPhotoBoardBinding;
import app.cyan.cyanalbum.fragment.PhotoBoradFragment;
import app.cyan.cyanalbum.utils.DLog;
import app.cyan.cyanalbum.utils.SoftAny;

public class PhotoBoardActivity extends AppCompatActivity {
    private static SoftAny _this = null;
    private ActivityPhotoBoardBinding binding = null;
    private TimeShow ts_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_photo_board);
        //获取binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_board);
        _this = new SoftAny(this);
//初始化数据bean
        ts_ = new TimeShow();
        ts_.updateTimeStr();
        //把对象放到binding的变量上
        binding.setTimeShow(ts_);
        h.sendEmptyMessageDelayed(1, 1000);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            addFragment();
        } else {
            requestReadExternalPermission();
        }
    }

    @SuppressLint("NewApi")
    private void requestReadExternalPermission() {
        DLog.i(getClass(),"checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)="+checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE));
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            DLog.i(getClass(),"1 ask again");
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            DLog.i(getClass(),"2 ask again");

//            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//            } else {
//                // 0 是自己定义的请求coude
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//            }
        } else {
            addFragment();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addFragment();

                } else {

                }
                return;
            }
            default:
                break;

        }
    }

    /**
     * 添加fragment
     */
    private void addFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        PhotoBoradFragment fragment = new PhotoBoradFragment();
        ft.add(binding.photoBoardContainer.getId(), fragment, PhotoBoradFragment.TAG).show(fragment).commit();
    }

    @Override
    protected void onDestroy() {
        _this = null;
        super.onDestroy();
    }

    public static Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1 && _this != null) {
                PhotoBoardActivity activity = _this.get(PhotoBoardActivity.class);
                if (activity != null) {
                    //每次更新时间的时候，也会更新ui
                    activity.ts_.updateTimeStr();
                    h.sendEmptyMessageDelayed(1, 1000);
                }
            } else
                super.handleMessage(msg);
        }
    };
}
