package chat.sh.orz.cyan.orz.main

import android.databinding.ObservableField

class FunctionViewModel() {
    constructor(_name: String, _c: Class<*>) : this() {
        name.set(_name)
        c = _c
    }

    val name = ObservableField<String>();
    //get set里用field替代变量 不然会出现循环调用 因为调用变量就是调用set get
    var c: Class<*>? = null
        set(value) {
            if (value == null) {
                field = Any::class.java
            } else {
                field = value
            }
        }
        get() = field
}