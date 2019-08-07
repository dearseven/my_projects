package wx.cyan.dagger2test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import dagger.android.DaggerActivity;
import wx.cyan.dagger2test.more.InstanceProperties.Chef;
import wx.cyan.dagger2test.more.InstanceProperties.InstancePropertyActivity;

import javax.inject.Inject;

public class TestDaggerActivity extends AppCompatActivity implements ITestDaggerView {
    public static String TAG = "TestDagger";
    //使用@Inject标注需要注入的实例变量。
    @Inject
    TestDaggerPresent1 present1;

    @Inject
    TestDaggerPresent2 present2;

    @Inject
    TestDaggerPresent3 present3;


    //------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注入实例
        DaggerTestDaggerActivityComponent.builder()
                .testDagger3ActivityModule(new TestDagger3ActivityModule(this))
                .build()
                .inject(this);

        Log.d(TAG, present1 + "");
        Log.d(TAG, present2 + "");
        Log.d(TAG, present3 + "");
        present3.printView();


        if (false) {
            int index = 0;
            Class z = null;
            if (index == 0) {
                //Activity类实例上的属性通过Dagger注入
                z = InstancePropertyActivity.class;
            }
            Intent i = new Intent();
            i.setPackage(getPackageName());
            i.setClass(this, z);
            startActivity(i);
        }

    }
}
