#include <jni.h>
#include <android/log.h>
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include<algorithm>

using namespace std;

extern "C"
JNIEXPORT jobject JNICALL
Java_teetaa_com_ascpppro_nativefuncs_CppLambda_sortByInt(JNIEnv *env, jclass type,
                                                         jobject arrayList) {
    //获取列表的size
    jclass arrayListClz = env->GetObjectClass(arrayList);
    jmethodID sizeMethod = env->GetMethodID(arrayListClz, "size", "()I");
    int size = env->CallIntMethod(arrayList, sizeMethod);
    //这个功能以后可以封装一个公共类，就是让javalist的对象变成c++的vector
    jmethodID getMethod = env->GetMethodID(arrayListClz, "get", "(I)Ljava/lang/Object;");
    vector<jobject> v;
    for (int i = 0; i < size; i++) {
        //虽然泛型了Integer在java层，但是class里的方法的返回值还是Object类型,然后取得这个object
        jobject o = env->CallObjectMethod(arrayList, getMethod, i);
        v.push_back(o);
    }
    //排序
    sort(v.begin(), v.end(), [env](jobject a, jobject b) {
        jclass itemClass = env->GetObjectClass(a);//FindClass("teetaa.com.ascpppro.bean.Item");
        jfieldID _indexId = env->GetFieldID(itemClass, "index", "I");
        int _a = env->GetIntField(a, _indexId);
        int _b = env->GetIntField(b, _indexId);
        // __android_log_print(ANDROID_LOG_INFO, "Cyan_JNI", "_a,_b is:%i,%i", _a, _b);
        return _a <= _b;
    });
    //数据刷回去
    jmethodID arrayClear = env->GetMethodID(arrayListClz, "clear", "()V");
    env->CallVoidMethod(arrayList, arrayClear);
    jmethodID addMethod = env->GetMethodID(arrayListClz, "add", "(Ljava/lang/Object;)Z");
    std::for_each(v.begin(), v.end(), [env, arrayList, addMethod](jobject x) {
        env->CallBooleanMethod(arrayList, addMethod, x);
    });
    return arrayList;
}
extern "C"
JNIEXPORT void JNICALL
Java_teetaa_com_ascpppro_nativefuncs_CppLambda_sortByIntWithFiledName(JNIEnv *env, jclass type,
                                                                      jobject arrayList,
                                                                      jstring filedName_) {
    const char *filedName = env->GetStringUTFChars(filedName_, 0);
//========
    //获取列表的size
    jclass arrayListClz = env->GetObjectClass(arrayList);
    jmethodID sizeMethod = env->GetMethodID(arrayListClz, "size", "()I");
    int size = env->CallIntMethod(arrayList, sizeMethod);
    //这个功能以后可以封装一个公共类，就是让javalist的对象变成c++的vector
    jmethodID getMethod = env->GetMethodID(arrayListClz, "get", "(I)Ljava/lang/Object;");
    vector<jobject> v;
    for (int i = 0; i < size; i++) {
        //虽然泛型了Integer在java层，但是class里的方法的返回值还是Object类型,然后取得这个object
        jobject o = env->CallObjectMethod(arrayList, getMethod, i);
        v.push_back(o);
    }
    //排序
    sort(v.begin(), v.end(), [env, filedName](jobject a, jobject b) {
        jclass itemClass = env->GetObjectClass(a);//FindClass("teetaa.com.ascpppro.bean.Item");
        jfieldID _indexId = env->GetFieldID(itemClass, filedName, "I");
        int _a = env->GetIntField(a, _indexId);
        int _b = env->GetIntField(b, _indexId);
        //__android_log_print(ANDROID_LOG_INFO, "Cyan_JNI", "_a,_b is:%i,%i", _a, _b);
        return _a <= _b;
    });
    //数据刷回去
    jmethodID arrayClear = env->GetMethodID(arrayListClz, "clear", "()V");
    env->CallVoidMethod(arrayList, arrayClear);
    jmethodID addMethod = env->GetMethodID(arrayListClz, "add", "(Ljava/lang/Object;)Z");
    std::for_each(v.begin(), v.end(), [env, arrayList, addMethod](jobject x) {
        env->CallBooleanMethod(arrayList, addMethod, x);
    });
//========
    env->ReleaseStringUTFChars(filedName_, filedName);
}

extern "C"
JNIEXPORT void JNICALL
Java_teetaa_com_ascpppro_nativefuncs_CppLambda_sortByItemNameFirstChar(JNIEnv *env, jclass type,
                                                                       jobject arrayList) {

    //获取列表的size
    jclass arrayListClz = env->GetObjectClass(arrayList);
    jmethodID sizeMethod = env->GetMethodID(arrayListClz, "size", "()I");
    int size = env->CallIntMethod(arrayList, sizeMethod);
    //这个功能以后可以封装一个公共类，就是让javalist的对象变成c++的vector
    jmethodID getMethod = env->GetMethodID(arrayListClz, "get", "(I)Ljava/lang/Object;");
    vector<jobject> v;
    for (int i = 0; i < size; i++) {
        //虽然泛型了Integer在java层，但是class里的方法的返回值还是Object类型,然后取得这个object
        jobject o = env->CallObjectMethod(arrayList, getMethod, i);
        v.push_back(o);
    }
    //排序
    sort(v.begin(), v.end(), [env](jobject a, jobject b) {
        jclass itemClass = env->GetObjectClass(a);//FindClass("teetaa.com.ascpppro.bean.Item");
        jfieldID _name = env->GetFieldID(itemClass, "name", "Ljava/lang/String;");
        jstring _a = (jstring) env->GetObjectField(a, _name);
        jstring _b = (jstring) env->GetObjectField(b, _name);
        const char *__a = env->GetStringUTFChars(_a, (jboolean *) 0);
        const char *__b = env->GetStringUTFChars(_b, (jboolean *) 0);
        //__android_log_print(ANDROID_LOG_INFO, "Cyan_JNI", "_a,_b is:%i,%i", __a[0], __b[0]);
        int aInt = __a[0];
        int bInt = __b[0];
//        env->ReleaseStringUTFChars(_a, __a.data());
//        env->ReleaseStringUTFChars(_a, __a);
//        env->ReleaseStringUTFChars(_b, __b);
        return bInt < aInt;
    });

    //数据刷回去
    jmethodID arrayClear = env->GetMethodID(arrayListClz, "clear", "()V");
    env->CallVoidMethod(arrayList, arrayClear);
    jmethodID addMethod = env->GetMethodID(arrayListClz, "add", "(Ljava/lang/Object;)Z");
    std::for_each(v.begin(), v.end(), [env, arrayList, addMethod](jobject x) {
        env->CallBooleanMethod(arrayList, addMethod, x);
    });

}