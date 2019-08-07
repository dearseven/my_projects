package wx.cyan.dagger2test;

import dagger.BindsInstance;
import dagger.Component;

/**
 * 使用@Component标注接口，表示它为桥梁。
 * （如果使用1.3.1的方式供应实例，则需在@Component(modules=xxx.class)中指明module。）
 * 一个Component可以没有module，也可以同时有多个module。
 * 添加 void inject(实例需求端) 方法，表明实例供应端中的实例将交给该实例需求端。
 * 通过这个方法，查找实例需求端中需要注入的实例有哪些（使用@Inject标注的那些实例），然后在实例供应端中获取所需的实例，最后注入到实例需求端中
 * 用来注入的方法，它的方法名不一定要是inject，可以随便取，一般都取inject。但该方法的返回类型必须为void
 *
 */
@Component(modules ={ TestDaggerActivityModule.class,TestDagger3ActivityModule.class})
public interface TestDaggerActivityComponent {

    void inject(TestDaggerActivity testDaggerActivity);

}
