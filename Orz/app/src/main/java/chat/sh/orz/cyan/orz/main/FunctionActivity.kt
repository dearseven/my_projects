package chat.sh.orz.cyan.orz.main

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import chat.sh.orz.cyan.orz.R
import chat.sh.orz.cyan.orz.databinding.ActivityFunctionBinding
import chat.sh.orz.cyan.orz.databinding.FunRecyclerItemLayoutBinding
import chat.sh.orz.cyan.phototaker.MainActivity
import kotlinx.android.synthetic.main.activity_function.*
import chat.sh.orz.cyan.recyclerlist.MyItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import chat.sh.orz.cyan.recyclerlist.MyRecyclerView.MyRecyclerViewListener
import chat.sh.orz.cyan.recyclerlist.adapter.CItemView
import chat.sh.orz.cyan.recyclerlist.adapter.CRecyclerListAdapter
import chat.sh.orz.cyan.recyclerlist.slide.SimpleItemTouchHelperCallback
import java.util.*
import android.widget.Toast
import android.app.ListActivity
import android.content.Intent
import android.view.View
import android.widget.TextView


class FunctionActivity : AppCompatActivity() {

    val funcList by lazy {
        ArrayList<FunctionViewModel>()
    }

    var adapter: Adapter? = null;
    var binding: ActivityFunctionBinding? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取绑定
        binding = DataBindingUtil.setContentView<ActivityFunctionBinding>(this, R.layout.activity_function)
        //setContentView(R.layout.activity_function)
        //初始化一些数据
        funcList.add(FunctionViewModel("图片选取or拍照1", MainActivity::class.java))
        funcList.add(FunctionViewModel("图片选取or拍照2", MainActivity::class.java))
        funcList.add(FunctionViewModel("图片选取or拍照3", MainActivity::class.java))
        funcList.add(FunctionViewModel("图片选取or拍照4", MainActivity::class.java))
        funcList.add(FunctionViewModel("图片选取or拍照5", MainActivity::class.java))
        funcList.add(FunctionViewModel("图片选取or拍照6", MainActivity::class.java))
        funcList.add(FunctionViewModel("图片选取or拍照7", MainActivity::class.java))
        funcList.add(FunctionViewModel("图片选取or拍照8", MainActivity::class.java))
        funcList.add(FunctionViewModel("图片选取or拍照9", MainActivity::class.java))
        funcList.add(FunctionViewModel("图片选取or拍照10", MainActivity::class.java))
        funcList.add(FunctionViewModel("图片选取or拍照11", MainActivity::class.java))

        //初始化adapter
        adapter = Adapter()

        //***1***侧滑和拖动数据 这个没用了 因为我没有用sdk提供的swipe和拖拽
        //先实例化Callback 这个没用了 因为我没有用sdk提供的swipe和拖拽
        var callback = SimpleItemTouchHelperCallback(adapter!!);
        //用Callback构造ItemtouchHelper 这个没用了 因为我没有用sdk提供的swipe和拖拽
        var touchHelper = ItemTouchHelper(callback);
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        //这个没用了 因为我没有用sdk提供的swipe和拖拽
        touchHelper.attachToRecyclerView(funRv);
        //***2***基本设置
        funRv.layoutManager = LinearLayoutManager(this)
        funRv.addItemDecoration(MyItemDecoration())
        //---这个adapter就有意思
        // 实际上是上是装饰器模式,MyRecyclerView重写了原本的setAdapter
        //可以追进去看,实际上是用另一个适配器(MyWrapAdapter)添加了header和footer
        //然后内容部分会调用这个代码里的适配器来实现
        funRv.adapter = adapter!!
        //***2***下拉刷新和上来加载更多的回调
        funRv.myRecyclerViewListener = object : MyRecyclerViewListener {
            //下拉刷新的回调,做一些事件和回收header
            override fun onRefresh() {
                h.postDelayed({ funRv.setRefreshComplete() }, 1500)
            }

            //加载更多的回调,做一些事件和回收footer
            override fun onLoadMore() {
                h.postDelayed({
                    for (i in 0..4) {
                        funcList.add(FunctionViewModel("i.toString()~~ok", MainActivity::class.java))
                    }
                    adapter!!.notifyDataSetChanged()
                    funRv.setLoadMoreComplete()
                }, 1000)
            }
        }

    }

    inner class Adapter : CRecyclerListAdapter() {
        /**
         * 我在SimpleItemTouchHelperCallback的isItemViewSwipeEnabled禁止了swipe
         */
        @Deprecated("没用自带的")
        override fun onItemDissmiss(position: Int) {
//            Log.d("ORZ", "11!!!onItemDissmiss###:"+funcList.size)
//            //移除数据
//            funcList.removeAt(position);
//            Log.d("ORZ", "22!!!onItemDissmiss###:"+funcList.size)
//            //由于有封装一层WrapAdapter增加了下拉刷新的头和加载更多的底部,
//            //其实真正的adapter不是CRecyclerListAdapter
//            funRv.adapter.notifyItemRemoved(position);
        }

        /**
         * 我在SimpleItemTouchHelperCallback的isLongPressDragEnabled禁止了拖动
         */
        @Deprecated("没用自带的")
        override fun onItemMove(fromPosition: Int, toPosition: Int) {
//            //交换位置
//            Collections.swap(funcList, fromPosition, toPosition);
//            //由于有封装一层WrapAdapter增加了下拉刷新的头和加载更多的底部,
//            //其实真正的adapter不是CRecyclerListAdapter
//            funRv.adapter.notifyItemMoved(fromPosition, toPosition);
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CItemView {
            var bind = DataBindingUtil.inflate<FunRecyclerItemLayoutBinding>(LayoutInflater.from(this@FunctionActivity), R.layout.fun_recycler_item_layout, parent, false)
            return CItemView(bind.root);
        }

        override fun onBindViewHolder(holder: CItemView, position: Int) {
            //
            val data = funcList[position];
            var bind: FunRecyclerItemLayoutBinding = DataBindingUtil.getBinding<FunRecyclerItemLayoutBinding>(holder.itemView)!!
            bind.funs = data;
            bind.executePendingBindings();
            //---这个是点击事件
            holder.itemView.findViewById<TextView>(R.id.show).setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    Toast.makeText(this@FunctionActivity, "编号：$position", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@FunctionActivity, data.c))
                }
            })
            //---这个是滑动出现的删除按钮的点击事件
            holder.itemView.findViewById<TextView>(R.id.click).setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    Toast.makeText(this@FunctionActivity, "删除：$position", Toast.LENGTH_LONG).show()
                }

            })
            //恢复滚动条的状态,在view重复使用的时候,就不会出现一开始删除区块就滑出来了
            holder.itemView.findViewById<MyRecyclerViewItem>(R.id.scroll_item).reset()
        }

        override fun getItemCount(): Int = funcList.size

        override fun getItemViewType(position: Int): Int {
//            return super.getItemViewType(position);
            return 0;
        }
    }

    var h = Handler()
}
