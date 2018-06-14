package teetaa.com.ascpppro.bean;

/**
 * 这是一个javabean最重要的作用就是没啥太多用
 * Created by wx on 2018/6/14.
 */
public class Item {
    public int index;
    public String name;

    public Item() {
    }

    public Item(int index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "index=" + index +
                ", name='" + name + '\'' +
                '}';
    }
}
