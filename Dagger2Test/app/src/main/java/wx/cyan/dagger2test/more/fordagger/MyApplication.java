package wx.cyan.dagger2test.more.fordagger;


import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import wx.cyan.dagger2test.more.InstanceProperties.Chef;

import javax.inject.Inject;
//如果用这个自动注入，则需要用这个替换Application
public class MyApplication extends DaggerApplication {
    @Inject
    Chef chef;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerCookAppComponent.builder().create(this);
    }
}
