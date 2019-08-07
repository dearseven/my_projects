package wx.cyan.dagger2test.more.present3inject;

import dagger.Component;
import wx.cyan.dagger2test.TestDaggerPresent3;

import javax.inject.Singleton;

@Singleton
//@AScope
@Component(modules = {Present3Module.class,Present3ModuleWithParam.class})
public interface Present3Component {
    void inject(TestDaggerPresent3 testDaggerPresent3);
}
