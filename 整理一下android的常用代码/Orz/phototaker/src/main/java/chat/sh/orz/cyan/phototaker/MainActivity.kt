package chat.sh.orz.cyan.phototaker

import android.Manifest
import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import chat.sh.orz.cyan.phototaker.utils.Glide4Engine
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.util.Log


/**
 * zhihu图片库Matisse
 */
class MainActivity : AppCompatActivity() {

    companion object {
        val REQUEST_CODE_CHOOSE = 0x0111;
        val REQUEST_CODE_CHECK_PERMISSION = 0x0112;
        val auth = "chat.sh.orz.cyan.orz.file.provider"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pick_photo_btn.setOnClickListener { v ->
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                openMatisse()
            } else {
                requestReadExternalPermission()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestReadExternalPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_CHECK_PERMISSION)
            //            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            //            } else {
            //                // 0 是自己定义的请求coude
            //                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            //            }
        } else {
            openMatisse()
        }
    }

    fun openMatisse(): Unit {
        Matisse.from(this@MainActivity)
                .choose(MimeType.ofAll())//图片类型
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(1)//可选的最大数
                .capture(true)//选择照片时，是否显示拍照
                .captureStrategy(CaptureStrategy(true, "chat.sh.orz.cyan.orz.file.provider"))
                //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .imageEngine(Glide4Engine())//图片加载引擎,***!!!!!这里很重要 因为我用的glide4 要用自己改过的引擎
                .forResult(REQUEST_CODE_CHOOSE)//
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_CHECK_PERMISSION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openMatisse()
                } else {
                }
                // return
            }
            else -> {
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            val result = Matisse.obtainResult(data)
            pick_photo_text.setText(result.toString())
            result.forEach {
                Log.d("ORZ", "返回值:" + it.toString());
                //注意下面这个root_path是在filespath.xml定义的,auth这个变量的值是在AndroidManifest.xml定义的权限
                if (it.toString().contains(auth))//拍照
                    Log.d("ORZ", "图片物理路径:${it.toString().replace("content://${auth}/root_path","")}");
                else
                    Log.d("ORZ", "图片物理路径:${getRealPathFromUri(this, it)}");
                //为了测试 我每次直选一张图,可以设置的,直接就用这个返回uri就可以显示了~
                pick_photo_img.setImageURI(it)
            }
        }
    }


    /**
     * 将返回的content的路径编程真实路径
     */
    fun getRealPathFromUri(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            return cursor!!.getString(column_index)
        } catch (e: Exception) {
            e.printStackTrace()
            return "";
        } finally {
            if (cursor != null) {
                cursor!!.close()
            }
        }
    }
}
