package wx.cyan.dagger2test.more.fordagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import wx.cyan.dagger2test.more.InstanceProperties.InstancePropertyActivity;

@Module
public abstract  class ActivityModules {
    @ContributesAndroidInjector
    abstract InstancePropertyActivity contributeInstancePropertyActivity();

}
