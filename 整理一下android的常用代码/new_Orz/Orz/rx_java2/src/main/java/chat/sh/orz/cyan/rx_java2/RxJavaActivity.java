package chat.sh.orz.cyan.rx_java2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static java.lang.Thread.sleep;

public class RxJavaActivity extends AppCompatActivity {
    private static final String TAG = "rx_java2";
    private Disposable mDisposable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        //最基本的使用
        one();
        //线程切换
        two();
        //一个小的异步场景
        three();
        //一个Interval和一个假后台耗时行为,演示怎么样让rxjava在页面关闭时释放资源
        four();
        //
        simpleNetWork();
    }

    /**
     * 一个简单的网络操作
     */
    private void simpleNetWork() {
        Log.i(TAG, "start simpleNetWork ~~~~~~~~~~~~~~");
        //首先通过网络请求获取一个observable
        Observable<ResponseBody> observable = ClientAPIUtil.getInstance().seeBaidu("google");
        //然后通过subscribeWith通过DisposableObserver来订阅消息
        DisposableObserver<ResponseBody> disposableObserver =
                observable.subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribeWith(
                                new DisposableObserver<ResponseBody>() {
                                    @Override
                                    public void onNext(ResponseBody value) {
                                        try {
                                            Log.i(TAG, "simpleNetWork:" + value.string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Log.i(TAG, "onNext end simpleNetWork ~~~~~~~~~~~~~~");
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.i(TAG, "onComplete end simpleNetWork ~~~~~~~~~~~~~~");
                                    }
                                }
                        );
        //放到管理容器中,避免内存溢出
        mCompositeDisposable.add(disposableObserver);
    }

    //----start four----------------------------------
    //创建容器来管理“被观察者”
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private void four() {
        //这是一个interval任务,不停的轮询
        DisposableObserver<Object> disposableObserver = Observable.interval(0, 3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())//发射在耗时IO线程(interval)
                .observeOn(AndroidSchedulers.mainThread())//订阅在主线程(DisposableObserver)
                .subscribeWith(new DisposableObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        Log.d(TAG, "#####开始#####");
                        Log.d(TAG, String.valueOf(o));
                        Log.d(TAG, "#####结束#####");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.toString(), e);
                    }
                });
        //这是一个普通的任务,假设在后台执行耗时工作
        DisposableObserver<String> disposableObserver_ = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("这是第一步");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }
                e.onNext("这是第二步");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }
                e.onNext("这是第三步");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }
                e.onNext("这是第四步");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }
                e.onNext("这是第五步");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String value) {
                        Log.e(TAG, "onNext一个普通的不会内存溢出的订阅:" + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete一个普通的不会内存溢出的订阅:");
                    }
                });
        //嗯 把disposableObserver加入到mCompositeDisposable,以便在onDestroy的时候释放资源
        mCompositeDisposable.
                add(disposableObserver);
        mCompositeDisposable.add(disposableObserver_);
    }

    @Override
    protected void onDestroy() {
        // 如果退出程序，就清除后台任务
        mCompositeDisposable.clear();
        super.onDestroy();
    }
    //----end four------------------------------------

    private void three() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(123);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }
                emitter.onNext(456);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, integer + "");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }


    private void one() {
        /**
         * bservable中文意思就是被观察者，通过create方法生成对象，
         * 里面放的参数ObservableOnSubscribe<T>，可以理解为一个计划表，
         * 泛型T是要操作对象的类型，重写subscribe方法，
         * 里面写具体的计划，本文的例子就是推送连载1、连载2和连载3，
         * 在subscribe中的ObservableEmitter<String>对象的Emitter是发射器的意思。
         * ObservableEmitter有三种发射的方法，
         * 分别是void onNext(T value)、
         * void onError(Throwable error)、
         * void onComplete()，
         * onNext方法可以无限调用，
         * Observer（观察者）所有的都能接收到，
         * onError和onComplete是互斥的，Observer（观察者）只能接收到一个，
         * OnComplete可以重复调用，但是Observer（观察者）只会接收一次，
         * 而onError不可以重复调用，第二次调用就会报异常。
         */
        Observable novel = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        });

        Observer<String> reader = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                if ("2".equals(value)) {
                    mDisposable.dispose();
                    return;
                }
                Log.i(TAG, "onNext:" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError=" + e.getMessage());
                mDisposable = null;
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete()");
                mDisposable.dispose();
                mDisposable = null;
            }
        };
        /**
         * 从文本理解
         * 怎么是小说订阅了读者，之所以这样，
         * 是因为RxJava主要是想保持自己的链式编程，
         * 不得不把Observable(被观察者)放在前面,这里大家可以理解为小说被读者订阅了
         */
        novel.subscribe(reader);//一行代码搞定
    }

    private void two() {
        /*
        RxJava是支持异步的，但是RxJava是如何做到的呢？
        这里就需要Scheduler。Scheduler，英文名调度器，
        它是RxJava用来控制线程。当我们没有设置的时候，
        RxJava遵循哪个线程产生就在哪个线程消费的原则，
        也就是说线程不会产生变化，始终在同一个。然后
        我们一般使用RxJava都是后台执行，前台调用，本
        着这个原则，我们需要调用
        observeOn(AndroidSchedulers.mainThread())，
        observeOn是事件回调的线程，
        AndroidSchedulers.mainThread()一看就知道是主线程，
        subscribeOn(Schedulers.io())，subscribeOn是事件执行的线程，
        Schedulers.io()是子线程，这里也可以用Schedulers.newThread()，
        只不过io线程可以重用空闲的线程，
        因此多数情况下 io() 比 newThread() 更有效率。
         */
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(this + " 连载1,");
                emitter.onNext(this + " 连载2");
                emitter.onNext(this + " 连载3");
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在io线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        Log.e(TAG, this + " onNext:" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError=" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete()");
                    }
                });
    }
}
