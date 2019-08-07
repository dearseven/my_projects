package wx.cyan.dagger2test.more.fordagger;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.LinkedHashMap;
import java.util.Map;

@Module
public class CookModules {
    @Singleton
    @Provides
    public Map<String, Boolean> providerMenus(){
        Map<String, Boolean> menus = new LinkedHashMap<>();
        menus.put("酸菜鱼", true);
        menus.put("土豆丝", true);
        menus.put("铁板牛肉", true);
        return menus;
    }
}
