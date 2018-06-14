package teetaa.com.ascpppro.nativefuncs;

import java.util.ArrayList;

/**
 * Created by wx on 2018/6/13.
 */

public class Cyan_Hello {
    static {
        System.loadLibrary("Cyan_Hello");
    }

    public static native int sumOfIntList(ArrayList<Integer> list);

    /**
     * 加
     *
     * @param a
     * @param b
     * @return
     */
    public static native int add(int a, int b);

    /**
     * 减
     *
     * @param a
     * @param b
     * @return
     */
    public static native int subtract(int a, int b);

    /**
     * 乘
     *
     * @param a
     * @param b
     * @return
     */
    public static native int multiply(int a, int b);

    /**
     * 除
     *
     * @param a
     * @param b
     * @return
     */
    public static native int divide(int a, int b);
}
