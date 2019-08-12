package wx.cyan.testnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * onSupportNavigateUp()方法的重写，意味着Activity将它的 back键点击事件的委托出去，
     * 如果当前并非栈中顶部的Fragment, 那么点击back键，返回上一个Fragment。
     */
    override fun onSupportNavigateUp() = Navigation.findNavController(this, R.id.my_nav_host_fragment)
        .navigateUp()


}
