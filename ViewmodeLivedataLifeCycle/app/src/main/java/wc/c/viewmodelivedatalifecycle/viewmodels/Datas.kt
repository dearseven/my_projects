package wc.c.viewmodelivedatalifecycle.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

//作为一个ViewModel，他可以。。。
/*
我们都知道，当我们的Activity/Fragment因为某些因素被销毁重建时，
我们的成员变量便失去了意义。
因此我们常常需要通过 onSaveInstanceState()
和onCreate()/onSaveInstanceState(Bundle)完成对数据的恢复（通常还要保证其正确的序列化）。
并且对于大型数据来书，便有些乏力，比如：List、Bitmap...

而ViewModel就是解决此问题。

！！并且可以在Activity和Fragment里共享
 */
class Datas : ViewModel() {
//    var userId = MutableLiveData<Long>()
//    var userName = MutableLiveData<String>()

    //注意这里数据本身用了MutableLiveData来封装噢！！是LiveData
    //所以其实LiveData和ViewModel配合使用是非常不错的啊！
    val requestTs = MutableLiveData<Long>()
    val requestCode = MutableLiveData<Int>()

    //当requestTs发生变化时，requestCodeAndTs中的map就会调用，那么我们就可以得到新的requestCodeAndTs
    val requestCodeAndTs=Transformations.map(requestTs){
        "after map=${requestCode.value}|$it"
    }
}
/*
文档在此处，有一个大大的警告：
Caution: A ViewModel must never reference a view, Lifecycle,
 or any class that may hold a reference to the activity context.

为啥？从上述解决的问题来看，ViewModel很明显生命周期会比Activity要长，
因此如果持有Activity相关实例，必然会带来内存泄漏。
（那如果的确有业务需要咋整？使用AndroidViewModel(application)即可。）

 */

//另外还有一个复杂一点的叫做MediatorLiveData
/**
 *MediatorLiveData 中介者LiveData,

它可以监听另一个LiveData的数据变化，
同时也可以做为一个liveData，被其他Observer观察。
 */