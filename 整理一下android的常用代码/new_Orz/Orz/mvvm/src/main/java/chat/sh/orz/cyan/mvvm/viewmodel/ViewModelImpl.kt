package chat.sh.orz.cyan.mvvm.viewmodel

import chat.sh.orz.cyan.mvvm.databinding.ActivityMvvmBinding
import chat.sh.orz.cyan.mvvm.model.MVVMActivity
import chat.sh.orz.cyan.mvvm.view.ViewObj

class ViewModelImpl : IViewModel {
    private var binding: ActivityMvvmBinding? = null
    private var ctx: MVVMActivity? = null

    //按照google的思路应该是model来做数据交互返回数据,但是总感觉不太对啊
    //一个类似实体类的东西再做数据交互嘛?emmmmmmmmmmmmmmmmm感觉总是不舒服
    private val model by lazy { ViewObj() }

    constructor(binding: ActivityMvvmBinding,ctx:MVVMActivity) {
        this.binding = binding
        this.ctx=ctx
        //
        //按照google的思路应该是model来做数据交互返回数据,但是总感觉不太对啊
        //一个类似实体类的东西再做数据交互嘛?emmmmmmmmmmmmmmmmm感觉总是不舒服
        model.age.set(1000)
        model.name.set("DEFAULT NAME")
        //
        binding.ko=model
    }

    override fun reverseName() {
        model.name.set(model.name.get()!!.reversed())
    }

    override fun recycle() {
        this.binding=null;
        this.ctx=null;
    }
}