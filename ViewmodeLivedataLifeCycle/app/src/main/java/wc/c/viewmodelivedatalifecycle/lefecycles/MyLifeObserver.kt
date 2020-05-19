package wc.c.viewmodelivedatalifecycle.lefecycles

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


/**
 * 在要用的地方获取Activity或者Fragment的lifecycle；然后调用addObserver方法就可以了
 */
abstract class MyLifeObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    abstract fun whenCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    abstract fun whenStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    abstract fun whenResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    abstract fun whenPause()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    abstract fun whenStop()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    abstract fun whenDestroy()
}