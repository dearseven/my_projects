package wx.cyan.dagger2test.more.InstanceProperties;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import dagger.android.DaggerActivity;
import wx.cyan.dagger2test.R;
import wx.cyan.dagger2test.TestDaggerActivity;

import javax.inject.Inject;

/**
 * Activity类实例上的属性通过Dagger注入
 */
public class InstancePropertyActivity extends DaggerActivity {
    @Inject
    Chef chef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instance_property);
        Log.d(TestDaggerActivity.TAG,chef.cook());
    }
}
