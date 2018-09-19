一:GIF
https://github.com/koral--/android-gif-drawable

二:下拉刷新+上拉加载更多
引用的Activity在app module chat.sh.orz.cyan.orz.main.FunctionActivity
这个部分在lib module recyclerlist中
MyRecyclerView+MyWrapAdapter主要负责
MyRecyclerView封装了滑动事件的捕捉,header和footer的滑出
MyWrapAdapter负责添加头和尾,因为MyRecyclerView重写了setAdapter实际上是用另一个适配器(MyWrapAdapter)添加了header和footer
然后内容部分会调用这个代码里的适配器来实现.

三:滑动删除
这个部分在app module chat.sh.orz.cyan.orz.main.MyRecyclerViewItem
然后recyclerview的item要是使用它做根布局
<chat.sh.orz.cyan.orz.main.MyRecyclerViewItem
        android:scrollbars="none"
        android:id="@+id/scroll_item"
        android:layout_width="match_parent"
        android:layout_height="90dp"
        android:orientation="horizontal">.....</chat.sh.orz.cyan.orz.main.MyRecyclerViewItem>
它的事件就在onBindViewHolder种实现好了 
 holder.itemView.findViewById<TextView>(R.id.click).setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    Toast.makeText(this@FunctionActivity, "删除：$position", Toast.LENGTH_LONG).show()
                }

            })

