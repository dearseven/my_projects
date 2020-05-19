package wc.c.viewmodelivedatalifecycle

import androidx.lifecycle.Lifecycle
import wc.c.viewmodelivedatalifecycle.simplehttp.SimpleHttp

interface IMainView {
   open fun getMainLifeCycle():Lifecycle

   open fun onHttpRequestEnd(result: SimpleHttp.Result)
}