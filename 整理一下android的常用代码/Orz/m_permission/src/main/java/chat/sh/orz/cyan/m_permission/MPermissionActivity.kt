package chat.sh.orz.cyan.m_permission

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import android.widget.Toast

class MPermissionActivity : AppCompatActivity() {

    companion object {
        val RECORD_PEMISSION = Manifest.permission.RECORD_AUDIO
        val REQUEST_PEMISSION_CODE = 0x21
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mpermission)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            initPermission()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initPermission() {
        //不管怎么样 还是记得再manifest里注册    <uses-permission android:name="android.permission.RECORD_AUDIO" />
        //不然的话,感觉就相当于是用户点了不再提醒并且拒绝了授权
        if (checkSelfPermission(RECORD_PEMISSION) != PackageManager.PERMISSION_GRANTED) {//如果没有授权
            requestPermissions(arrayOf(RECORD_PEMISSION), REQUEST_PEMISSION_CODE)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PEMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) run {
                Toast.makeText(this@MPermissionActivity, "granted!!!", Toast.LENGTH_SHORT).show()
            } else {//如果用户点了不再提醒并且拒绝了授权
                if (!shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                    //我们就弹出对话框告诉用户需要授权才能用,然后打开应用的权限页面,因为这个时候requestPermissions已经直接返回不成功了
                    showMessageOKCancel("需要提供录音权限方可录制提醒",
                            DialogInterface.OnClickListener { dialog, which ->
                                //
                                val intent = Intent()
                                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                //设置去向意图
                                val uri = Uri.fromParts("package", packageName, null)
                                intent.data = uri            //发起跳转
                                startActivity(intent)
                            })
                }
            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("去授权", okListener)
                .setNegativeButton("取消", null)
                .create()
                .show()
    }

}
