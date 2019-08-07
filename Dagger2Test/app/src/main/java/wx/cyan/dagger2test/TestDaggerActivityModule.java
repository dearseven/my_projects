package wx.cyan.dagger2test;

import dagger.Module;
import dagger.Provides;

/**
 * TestDaggerPresent1标识为供应端
 */
@Module
public class TestDaggerActivityModule {
    @Provides
    TestDaggerPresent1 testDaggerPresent1() {
        return new TestDaggerPresent1();
    }


}
