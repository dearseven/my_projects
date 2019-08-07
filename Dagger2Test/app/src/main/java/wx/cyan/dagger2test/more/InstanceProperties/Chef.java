package wx.cyan.dagger2test.more.InstanceProperties;


import javax.inject.Inject;
import java.util.Map;

public class Chef implements Cooking {

    Menu menu;

    @Inject
    public Chef(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String cook() {
        //key菜名， value是否烹饪
        Map<String, Boolean> menuList = menu.getItems();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Boolean> entry : menuList.entrySet()) {
            if (entry.getValue()) {
                sb.append(entry.getKey()).append(",");
            }
        }

        return sb.toString();
    }
}