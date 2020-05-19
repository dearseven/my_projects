package wc.c.viewmodelivedatalifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import wc.c.viewmodelivedatalifecycle.lefecycles.MyLifeObserver
import wc.c.viewmodelivedatalifecycle.simplehttp.SimpleHttp
import wc.c.viewmodelivedatalifecycle.viewmodels.Datas

/**
 * 1 这个实例的核心是用协程发起请求，
 * 用ViewModel在Activity和Fragment共享数据，用LiveData刷新数据
 * 用LifeCycle监听生命周期
 *
 * 2 LifeCycle主要是实现MyLifeObserver，然后添加到Activity或Fragment的Observer里；
 *
 * 3 协程网络请求参考presenter.requestHttp
 *
 * 4 ViewModel的提现主要在MainActivity::onHttpRequestEnd和Fragment的whenResume中，
 *  在这里主要提现了他可以被Activity和Fragment共享，
 *
 * 5 LiveData的修改在MainActivity::onHttpRequestEnd,监听他的修改在Fragment里
 */
class MainActivity : AppCompatActivity(), IMainView {

    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)

        registerLifeCycle()

    }

    /**
     * 注册生命周期
     */
    private fun registerLifeCycle() {
        //所以是可以通过这种方法把生命周期放到所有地方（比如Presenter），很方便的一种方式
        lifecycle.addObserver(object : MyLifeObserver() {
            override fun whenCreate() {
            }

            override fun whenStart() {
                javaClass.signers
            }

            override fun whenResume() {
                DLog.i(MainActivity::class.java.simpleName, "whenResume")
                //做一个网络请求
                presenter.requestHttp()
                DLog.i(javaClass.simpleName, "whenResume，等待requestHttp运行结果")
            }

            override fun whenPause() {
                DLog.i(MainActivity::class.java.simpleName, "whenPause")
            }

            override fun whenStop() {
            }

            override fun whenDestroy() {
            }
        })
    }

    //------------IMainView---------
    override fun getMainLifeCycle() = lifecycle

    //------------IMainView---------
    override fun onHttpRequestEnd(result: SimpleHttp.Result) {
        //获取ViewModel
        val model = ViewModelProvider(this).get(Datas::class.java)
        //--更新ViewModel里面的LiveData的数据，在Fragment里监听了数据的修改
        val ts = System.currentTimeMillis()
        model.requestCode.value = result.returnCode
        //这个变动会引起requestCodeAndTs的map执行
        model.requestTs.value = ts
    }
}
