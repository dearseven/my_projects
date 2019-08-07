package wx.cyan.dagger2test.more.present3inject;

import dagger.Module;
import dagger.Provides;
import wx.cyan.dagger2test.more.InstanceProperties.Chef;

import javax.inject.Scope;
import javax.inject.Singleton;
import java.util.LinkedHashMap;
import java.util.Map;

@Module
public class Present3Module {

    @Singleton
    @Provides
    public ListOfData providerListOfData(){
      ListOfData data=new ListOfData();
      return data;
    }
}
