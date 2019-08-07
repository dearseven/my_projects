package wx.cyan.dagger2test;

import dagger.Module;
import dagger.Provides;

@Module
public class TestDagger3ActivityModule {
    ITestDaggerView iTestDaggerView;
    public TestDagger3ActivityModule(ITestDaggerView iTestDaggerView) {
        this.iTestDaggerView = iTestDaggerView;
    }

    @Provides
    ITestDaggerView iTestDaggerView(){
        return iTestDaggerView;
    }

    @Provides
    TestDaggerPresent3 testDaggerPresent3(ITestDaggerView iTestDaggerView) {
        return new TestDaggerPresent3(iTestDaggerView);
    }
}
