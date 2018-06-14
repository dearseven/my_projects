package teetaa.com.ascpppro.nativefuncs;

import java.util.ArrayList;

import teetaa.com.ascpppro.bean.Item;

/**
 * Created by wx on 2018/6/14.
 */

public class CppLambda {
    static {
        System.loadLibrary("CppLambda");
    }

    public static native ArrayList<Item> sortByInt(ArrayList<Item> arrayList);

    /**
     * 排序
     *
     * @param arrayList
     * @param filedName 这是Item里的某一个int类型的字段
     */
    public static native void sortByIntWithFiledName(ArrayList<Item> arrayList, String filedName);

    /**
     * 按照名字的首字母的ASCII排序
     * @param arrayList
     */
    public static native void sortByItemNameFirstChar(ArrayList<Item> arrayList);


}
