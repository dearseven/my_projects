package teetaa.com.ascpppro

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_cpp_lambda.*
import teetaa.com.ascpppro.bean.Item
import teetaa.com.ascpppro.nativefuncs.CppLambda

class CppLambdaActivity : AppCompatActivity() {
    private var list: ArrayList<Item> = ArrayList<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpp_lambda)

        list.add(Item(3, "A"))
        list.add(Item(5, "D"))
        list.add(Item(1, "B"))
        list.add(Item(4, "E"))
        list.add(Item(2, "C"))
        //Log.i("CppLambdaActivity", "list instance:$list");
        //CppLambda.sortByInt(list)
        CppLambda.sortByIntWithFiledName(list, "index");
        Log.i("CppLambdaActivity", "after sortByIntWithFiledName instance:$list");
        lambda_sort_by_int.setText("按index升序排序：$list")
        // list.forEach { Log.i("CppLambdaActivity", it.toString()); }

        //CppLambda.sortByInt(list)
        CppLambda.sortByItemNameFirstChar(list);
        Log.i("CppLambdaActivity", "after sortByItemNameFirstChar:$list");
        lambda_sort_by_name_first_char.setText("按name首字母降序排序：$list")

    }
}
