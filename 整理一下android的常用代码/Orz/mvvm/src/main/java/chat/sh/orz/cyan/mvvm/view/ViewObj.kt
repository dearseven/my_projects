package chat.sh.orz.cyan.mvvm.view

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

class ViewObj : BaseObservable() {
    //private val userRepository = UserRepository()
    val name = ObservableField<String>()
    val age = ObservableInt()
}