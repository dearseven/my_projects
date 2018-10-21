package chat.sh.orz.cyan.mvvm.model

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import chat.sh.orz.cyan.mvvm.R
import chat.sh.orz.cyan.mvvm.databinding.ActivityMvvmBinding
import chat.sh.orz.cyan.mvvm.view.ViewObj
import chat.sh.orz.cyan.mvvm.viewmodel.IViewModel
import chat.sh.orz.cyan.mvvm.viewmodel.ViewModelImpl
import kotlinx.android.synthetic.main.activity_mvvm.*

/**
 * View层注册事件,做与业务无关的UI更新,例如给RecyclerView添加分割线,改变Activity的颜色之类的
 */
class MVVMActivity : AppCompatActivity(), View.OnClickListener, IMVVM {
    //viewModel
    private var viewModel: IViewModel? = null
    //binding
    private var binding: ActivityMvvmBinding? = null;

    override fun onClick(v: View?) {
//        when (v!!.id) {
//            button.getId() -> {
//                viewModel!!.reverseName()
//            }
//        }
    }

    fun reverseName(v: View) {
        viewModel!!.reverseName()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_mvvm)
        //获得bingding
        binding = DataBindingUtil.setContentView<ActivityMvvmBinding>(this, R.layout.activity_mvvm)
        //初始化viewmodel
        viewModel = ViewModelImpl(binding!!, this@MVVMActivity)
        //view层设置事件
        // button.setOnClickListener(this@MVVMActivity)
    }

    override fun onDestroy() {
        viewModel!!.recycle()
        super.onDestroy()
    }
}
