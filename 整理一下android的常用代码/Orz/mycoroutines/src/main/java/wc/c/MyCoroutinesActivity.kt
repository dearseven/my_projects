package wc.c

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import wc.c.lefecyclers.R

class MyCoroutinesActivity : AppCompatActivity() {
    companion object {
        val TAGA = "Tag-A"
        val TAGB = "Tag-B"
        val TAGC = "Tag-C"
        val TAGD = "Tag-D"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_coroutines)

        //
        firstTest()
        secondTest()
        thirdTest()

        //
        forthTest()

    }

    /**
     * 注意我外面要包裹一层 GlobalScope.launch，要不运行不了。
     * 这里我们搞了2个协程出来，但是我们在这里使用了await，
     * 这样就会阻塞外部协程，所以代码还是按顺序执行的。
     * 这样适用于多个同级 IO 操作的情况，这样写比 rxjava 要省事不少
     */
    private fun forthTest() {
        GlobalScope.launch(Dispatchers.Unconfined) {
            var token = GlobalScope.async(Dispatchers.Unconfined) {
                delay(5000)
                return@async getTokenForthTest()
            }.await()

            var response = GlobalScope.async(Dispatchers.Unconfined) {
                return@async getResponseForthTest(token)
            }.await()

            Log.i(TAGD, response)
        }

    }

    fun getResponseForthTest(token: Long): String {
        return "getResponse:${token}"
    }

    fun getTokenForthTest(): Long {
        return System.currentTimeMillis()
    }

    /**
     * runBlocking 和 launch 区别的地方就是 runBlocking 的 delay 方法是可以阻塞当前的线程的，
     * 和Thread.sleep() 一样
     *
     * runBlocking 通常的用法是用来桥接普通阻塞代码和挂起风格的非阻塞代码，
     * 在 runBlocking 闭包里面启动另外的协程，协程里面是可以嵌套启动别的协程的
     */
    private fun thirdTest() {
        runBlocking {
            // 阻塞1s
            delay(1000L)
            Log.d(TAGC, "runBlocking可以阻塞主线程:  ${System.currentTimeMillis()}")
        }

        // 阻塞2s
        Thread.sleep(500L)
        Log.d(TAGC, "runBlocking后的主线程:  ${System.currentTimeMillis()}")
    }

    //async 同 launch 唯一的区别就是 async 是有返回值的
    private fun secondTest() {
        GlobalScope.launch(Dispatchers.Unconfined) {
            /**
             * async 返回的是 Deferred 类型，Deferred 继承自 Job 接口，Job有的它都有，
             * 增加了一个方法 await ，这个方法接收的是 async 闭包中返回的值，
             * async 的特点是不会阻塞当前线程，但会阻塞所在协程，也就是挂起
             *但是注意啊，async 并不会阻塞线程，只是阻塞锁调用的协程
             */
            val deferred = GlobalScope.async {
                delay(1000L)
                Log.d(TAGB, "This is the ‘async’ ")
                return@async "taonce"
            }

            Log.d(TAGB, "协程 other start")
            //await
            val result = deferred.await()
            Log.d(TAGB, "‘async’ result = $result")
            Log.d(TAGB, "协程 other end ")
        }

        Log.d(TAGB, "主线程位于协程之后的代码执行，时间:  ${System.currentTimeMillis()}")
    }

    private fun firstTest() {
        Log.d(TAGA, "协程初始化开始，时间: " + System.currentTimeMillis())

        GlobalScope.launch(Dispatchers.Unconfined) {
            Log.d(TAGA, "协程初始化完成，时间: " + System.currentTimeMillis())
            for (i in 1..3) {
                Log.d(TAGA, "协程任务1打印第$i 次，时间: " + System.currentTimeMillis())
            }
            delay(1500)//这里会导致切换到主线程
            Log.d(TAGA, "协程被切换回来，时间: " + System.currentTimeMillis())
            for (i in 1..3) {
                Log.d(TAGA, "协程任务2打印第$i 次，时间: " + System.currentTimeMillis())
            }
        }
        //
        Log.d(TAGA, "主线程 sleep ，时间: " + System.currentTimeMillis())
        Thread.sleep(1000)
        Log.d(TAGA, "主线程运行，时间: " + System.currentTimeMillis())

        for (i in 1..3) {
            Log.d(TAGA, "主线程打印第$i 次，时间: " + System.currentTimeMillis())
        }
    }

}
