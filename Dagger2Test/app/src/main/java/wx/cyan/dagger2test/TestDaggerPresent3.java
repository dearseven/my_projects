package wx.cyan.dagger2test;

import android.util.Log;
import wx.cyan.dagger2test.more.InstanceProperties.Chef;
import wx.cyan.dagger2test.more.present3inject.DaggerPresent3Component;
import wx.cyan.dagger2test.more.present3inject.ListOfData;
import wx.cyan.dagger2test.more.present3inject.MyName;
import wx.cyan.dagger2test.more.present3inject.Present3ModuleWithParam;

import javax.inject.Inject;

/**
 * 此类直接通过TestDaggerActivityModule来把自己标记为供应端
 */
public class TestDaggerPresent3 {

    ITestDaggerView iview;

    public TestDaggerPresent3(ITestDaggerView iview) {
        this.iview=iview;
    }

    public void printView() {
        Log.d(TestDaggerActivity.TAG,   "iview="+iview);
        //之所以是DaggerPresent3Component这个名字
        //是因为Component的名字是Present3Component，就是在前面加Dagger
        DaggerPresent3Component.builder()
                //这个是那个有参数的module，所以要调用这样写一下别问为什么，就是这样
                .present3ModuleWithParam(new Present3ModuleWithParam("WO SHI SHEI ??"))
                .build().inject(this);
        listOfData.forEach();
        Log.d(TestDaggerActivity.TAG,"myname="+myname.myName());

    }

    @Inject
    ListOfData listOfData;

    @Inject
    MyName myname;

}
