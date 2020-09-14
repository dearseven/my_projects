package chat.sh.orz.cyan.wheel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.wheel_activity_main.*

class WheelMainActivity : AppCompatActivity() {

    private var hour: Int = 0
    private var minute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wheel_activity_main)

        Toast.makeText(this, "epv_h=${epv_h}", Toast.LENGTH_SHORT).show()

        // hours
        initHours()
        // minutes
        initMinutes()
        // btns
        initBtns()
    }

    private fun initHours() {
        val hDataList = ArrayList<String>()
        for (i in 0..23)
            hDataList.add("" + i)
        epv_h!!.setDataList(hDataList)
        epv_h!!.setOnScrollChangedListener(object : EasyPickerView.OnScrollChangedListener {
            override fun onScrollChanged(curIndex: Int) {
                hour = Integer.parseInt(hDataList.get(curIndex))
                tv_!!.text = hour.toString() + "h" + minute + "m"
            }

            override fun onScrollFinished(curIndex: Int) {
                hour = Integer.parseInt(hDataList.get(curIndex))
                tv_!!.text = hour.toString() + "h" + minute + "m"
            }
        })
    }

    private fun initMinutes() {
        val dataList2 = ArrayList<String>()
        for (i in 0..59)
            dataList2.add("" + i)

        epv_m!!.setDataList(dataList2)
        epv_m!!.setOnScrollChangedListener(object : EasyPickerView.OnScrollChangedListener {
            override fun onScrollChanged(curIndex: Int) {
                minute = Integer.parseInt(dataList2.get(curIndex))
                tv_!!.text = hour.toString() + "h" + minute + "m"
            }

            override fun onScrollFinished(curIndex: Int) {
                minute = Integer.parseInt(dataList2.get(curIndex))
                tv_!!.text = hour.toString() + "h" + minute + "m"
            }
        })
    }

    private fun initBtns() {
        // hours

        val btnTo = findViewById(R.id.btn_to_h) as Button
        btnTo.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (!TextUtils.isEmpty(et_h_.text)) {
                    val index = Integer.parseInt(et_h_.text.toString())
                    epv_h!!.moveTo(index)
                }
            }
        })

        btn_by_h.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (!TextUtils.isEmpty(et_h_.text)) {
                    val index = Integer.parseInt(et_h_.text.toString())
                    epv_h!!.moveBy(index)
                }
            }
        })

        // minutes
        //   val et_m = et_m_
        // val btnTo_m = findViewById(R.id.btn_to_m) as Button
        btn_to_m.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (!TextUtils.isEmpty(et_m_.text)) {
                    val index = Integer.parseInt(et_m_.text.toString())
                    epv_m!!.moveTo(index)
                }
            }
        })

        val btnBy_m = btn_by_m
        btnBy_m.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (!TextUtils.isEmpty(et_m_.text)) {
                    val index = Integer.parseInt(et_m_.text.toString())
                    epv_m!!.moveBy(index)
                }
            }
        })
    }
}
