#include <jni.h>
#include <android/log.h>
#include <string>

extern "C"
JNIEXPORT jint JNICALL
Java_teetaa_com_ascpppro_nativefuncs_Cyan_1Hello_divide(JNIEnv *env, jclass type, jint a, jint b) {
    return a / b;
}

extern "C"
JNIEXPORT jint JNICALL
Java_teetaa_com_ascpppro_nativefuncs_Cyan_1Hello_multiply(JNIEnv *env, jclass type, jint a,
                                                          jint b) {
    return a * b;
}

extern "C"
JNIEXPORT jint JNICALL
Java_teetaa_com_ascpppro_nativefuncs_Cyan_1Hello_subtract(JNIEnv *env, jclass type, jint a,
                                                          jint b) {
    return a - b;
}

extern "C"
JNIEXPORT jint JNICALL
Java_teetaa_com_ascpppro_nativefuncs_Cyan_1Hello_add(JNIEnv *env, jclass type, jint a, jint b) {
    return a + b;
}

extern "C"
JNIEXPORT jint JNICALL
Java_teetaa_com_ascpppro_nativefuncs_Cyan_1Hello_sumOfIntList(JNIEnv *env, jclass type,
                                                              jobject list) {
    jint sum = 0;
    //-----1 获取类型，找到size方法，然后调用方法来获取长度
    //传过来的类型是ArrayList<Integer>
    jclass arrayListClass = env->GetObjectClass(list);
    //得到list的长度
    jmethodID sizeMethod = env->GetMethodID(arrayListClass, "size", "()I");
    jint size = env->CallIntMethod(list, sizeMethod);
    __android_log_print(ANDROID_LOG_INFO, "Cyan_JNI", "size is:%i", size);
    //-------2 循环的时候，回去get方法，然后回去每一个list里的每一个元素
    //循环这个list求和
    jmethodID getMethod = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");
    for (int i = 0; i < size; i++) {
        //虽然泛型了Integer在java层，但是class里的方法的返回值还是Object类型,然后取得这个object
        jobject o = env->CallObjectMethod(list, getMethod, i);
        //-----3这里比java多一步，因为上一步获得的是jobject，所以我们用它的实际类型Integer的intValue方法来获取真的值 然后累加
        jclass wrapIntClass = env->GetObjectClass(o);
        jmethodID getIntMid = env->GetMethodID(wrapIntClass, "intValue", "()I");
        sum += env->CallIntMethod(o, getIntMid);
    }
    return sum;
}