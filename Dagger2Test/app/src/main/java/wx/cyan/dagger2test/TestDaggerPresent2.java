package wx.cyan.dagger2test;

import javax.inject.Inject;

/**
 * 此类直接通过Inject标识构造方法来把自己标记为供应端
 */
public class TestDaggerPresent2 {
    //使用@Inject标注构造函数   ,标记为供应端。
    @Inject
    public TestDaggerPresent2() {
    }
}
