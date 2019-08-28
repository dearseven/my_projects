package chat.sh.orz.cyan.appinstall

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_install_main.*
import java.io.File
import java.nio.file.Files.exists
import android.content.DialogInterface
import android.os.Build
import android.support.v7.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES
import android.support.annotation.RequiresApi
import android.support.v4.content.FileProvider
import android.content.res.AssetManager
import java.io.FileOutputStream
import java.io.IOException


class InstallMain : AppCompatActivity() {
    companion object {
        var APP_PACKAGE_NAME = "chat.sh.orz.cyan.orz"
        val auth = "chat.sh.orz.cyan.orz.file.provider"
        val REQUEST_CODE_INTALL_APP_PERMISTION = 10086
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_install_main)
        //将apk从assets读出来放到外存
        readAssertResource(this, "app-debug.apk");
        //点击安装
        btn_install.setOnClickListener { prepareInstall() }
    }

    /**
     * 准备安装app
     */
    private fun prepareInstall() {
        val apkPath = externalCacheDir!!.absolutePath
        val apk = File(apkPath, "apk.apk")
        if (!apk.exists()) {
            //如果文件不存在,直接返回()其实这个判断如果是实际使用中可以改成下载
            return
        }
        //
        val haveInstallPermission: Boolean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//O是Android 8
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = packageManager.canRequestPackageInstalls()
            if (!haveInstallPermission) {//没有权限
                val builder = AlertDialog.Builder(this)
                builder.setTitle("安装应用需要打开未知来源权限，请去设置中开启权限")
                builder.setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startInstallPermissionSettingActivity()
                    }
                })
                builder.setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, which -> })
                builder.create().show()
            } else {
                //O(8)有权限，开始安装应用程序
                installApk(apk)
            }
        } else {
            //低于8 直接安装
            installApk(apk)
        }
    }

    /**
     * 针对O(8)及以上,请求安装未知应用
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun startInstallPermissionSettingActivity() {
        val packageURI = Uri.parse("package:$packageName")
        //注意这个是8.0新API
        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
        startActivityForResult(intent, REQUEST_CODE_INTALL_APP_PERMISTION)
    }

    /**
     * 安装应用
     */
    private fun installApk(apk: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {//小于Android7
            intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive")
        } else {//Android7.0之后获取uri要用contentProvider
            val uri = getUriFromFile(baseContext, apk)
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        baseContext.startActivity(intent)
    }

    /**
     * 当>=N(7.0)要用FileProvider来创建Uri
     */
    fun getUriFromFile(context: Context, file: File): Uri {
        val uri: Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context,
                    auth, file)//通过FileProvider创建一个content类型的Uri,在清单文件中创建
        } else {
            uri = Uri.fromFile(file)
        }
        return uri
    }

    /**
     * 把apk从assets写到外面~~~这个是因为我这个demo的更新包放在了assets中
     */
    fun readAssertResource(context: Context, strAssertFileName: String) {
        val apkPath = externalCacheDir!!.absolutePath
        val apk = File(apkPath, "apk.apk")
        if (apk.exists()) {
            return
        }
        val assetManager = context.assets
        try {
            val ims = assetManager.open(strAssertFileName)
            val newPath = externalCacheDir!!.absolutePath
            val fos = FileOutputStream(File(newPath, "apk.apk"))
            val buffer = ByteArray(1024)
            var byteCount = 0
            do {
                byteCount = ims.read(buffer)
                if (byteCount == -1) {
                    break
                }
                fos.write(buffer, 0, byteCount)
            } while (true)
//            while ((byteCount = ims.read(buffer)) != -1) {//循环从输入流读取 buffer字节
//                fos.write(buffer, 0, byteCount)//将读取的输入流写入到输出流
//            }
            fos.flush()//刷新缓冲区
            ims.close()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 接受安装未知应用的设定页面返回的权限结果
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_INTALL_APP_PERMISTION) {
            if (resultCode == Activity.RESULT_OK) {
                //已有权限,重新调用准备安装
                prepareInstall()
            }
            else{
                //未打卡权限
            }
        }
    }
}
