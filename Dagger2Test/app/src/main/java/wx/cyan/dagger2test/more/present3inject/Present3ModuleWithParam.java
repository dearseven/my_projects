package wx.cyan.dagger2test.more.present3inject;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class Present3ModuleWithParam {
    private String name = null;

    public Present3ModuleWithParam(String name) {
        this.name = name;
    }


    @Singleton
    @Provides
    public MyName getMyName() {
        MyName myname=new MyName();
        myname.setName(name);
        return myname;
    }
}
