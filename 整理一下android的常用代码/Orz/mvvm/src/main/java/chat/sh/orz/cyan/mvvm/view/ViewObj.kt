package chat.sh.orz.cyan.mvvm.view

import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.databinding.ObservableInt

class ViewObj : BaseObservable() {
    //private val userRepository = UserRepository()
    val name = ObservableField<String>()
    val age = ObservableInt()
}