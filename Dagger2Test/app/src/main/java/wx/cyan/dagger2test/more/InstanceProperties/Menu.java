package wx.cyan.dagger2test.more.InstanceProperties;

import javax.inject.Inject;
import java.util.Map;

public class Menu {
    private Map<String, Boolean> items;

    @Inject
    public Menu(Map<String, Boolean> items) {
        this.items = items;
    }

    Map<String, Boolean> getItems() {
        return items;
    }
}
