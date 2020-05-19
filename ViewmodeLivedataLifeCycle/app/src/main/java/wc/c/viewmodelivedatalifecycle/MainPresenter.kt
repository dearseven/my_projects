package wc.c.viewmodelivedatalifecycle

import kotlinx.coroutines.*
import wc.c.viewmodelivedatalifecycle.lefecycles.MyLifeObserver
import wc.c.viewmodelivedatalifecycle.simplehttp.SimpleHttp
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext


class MainPresenter : CoroutineScope {
    //----start 使用了CoroutineScope-------------------
    // 创建一个Job,并用这个job来管理你的SomethingWithLifecycle的所有子协程
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    // 当生命周期销毁时，结束所有子协程
    fun closeCoroutines() {
        job.cancel()
    }

    //---end 使用了CoroutineScope------------------


    lateinit var weakView: WeakReference<IMainView>

    constructor(view: IMainView) {
        weakView = WeakReference(view)

        //所以是可以通过这种方法把生命周期放到所有地方，很方便的一种方式
        weakView.get()?.getMainLifeCycle()?.addObserver(object : MyLifeObserver() {
            override fun whenCreate() {
            }

            override fun whenStart() {
            }

            override fun whenResume() {
                DLog.i(MainPresenter::class.java.simpleName, "whenResume")
            }

            override fun whenPause() {
                DLog.i(MainPresenter::class.java.simpleName, "whenPause")
            }

            override fun whenStop() {
            }

            override fun whenDestroy() {
                closeCoroutines()
                weakView?.also {
                    it.clear()
                }
            }

        })
    }

    /**
     * 通过协程来请求网络
     */
    fun requestHttp() {
        //不要忘了是因为Presenter实现了CoroutineScope接口才可以直接调用launch噢
        launch {
            /*因为Android规定网络请求必须在子线程,
          所以这里我们通过withContext获取请求结果,
          通过调度器Dispatcher切换到IO线程,
          这个操作会挂起当前协程,但是不会阻塞当前线程*/
            val result = withContext(Dispatchers.IO) {
                delay(3000)
                //注意我上面故意休眠了3秒，
                //而且然后这个SimpleHttp.rawGet是没有再包含线程了，直接访问的网络！！
                val result = SimpleHttp.rawGet("https://www.baidu.com", "")
                result
            }
            //******上面请求结束之后,协程又返回到了主线程****
            DLog.i(javaClass.simpleName, "requestHttp请求结束")
            DLog.i(javaClass.simpleName, "result.returnCode=${result.returnCode}")
            //DLog.i(javaClass.simpleName, "${result.retString}")
            //调用view层的接口，其实本来就是在主线程噢，我只是又写了一下
            withContext(Dispatchers.Main) {
                //调用Activity的方法，这个方法会更新ViewModel
                weakView?.get()?.onHttpRequestEnd(result)
            }
        }
    }
}
