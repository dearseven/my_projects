package chat.sh.orz.cyan.recyclerlist.slide;

/**
 * @deprecated  系统提供的效果不是我要的
 * 这个接口让适配器实现{@link chat.sh.orz.cyan.recyclerlist.adapter.CRecyclerListAdapter}
 */
public interface ItemTouchHelperAdapter {
    //数据交换
    void onItemMove(int fromPosition,int toPosition);
    //数据删除
    void onItemDissmiss(int position);


}
