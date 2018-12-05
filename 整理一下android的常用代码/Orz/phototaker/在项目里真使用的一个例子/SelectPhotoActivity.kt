package com.teetaa.phototaker.main.v

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.teetaa.phototaker.R
import com.teetaa.phototaker.main.glideutil.Glide4Engine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy

/**
 * 通过这个类来访问matisee
 */
class SelectPhotoActivity : SelectPhotoView, AppCompatActivity() {
    companion object {
        val REQUEST_CODE_CHOOSE = 0x0111;
        //在进入这个Activity之前就已经检测过permission
        //val REQUEST_CODE_CHECK_PERMISSION = 0x0112;
        val auth = "com.teetaa.fengling.file.provider"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_photo)
        openMatisse()
    }

    fun openMatisse(): Unit {
        Matisse.from(this@SelectPhotoActivity)
                .choose(MimeType.ofAll())//图片类型
                .theme(R.style.Matisse_Fengling)
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(1)//可选的最大数
                .capture(true)//选择照片时，是否显示拍照
                .captureStrategy(CaptureStrategy(true, auth))
                //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .imageEngine(Glide4Engine())//图片加载引擎,***!!!!!这里很重要 因为我用的glide4 要用自己改过的引擎
                //
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                //
                .forResult(REQUEST_CODE_CHOOSE)//
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Toast.makeText(this@SelectPhotoActivity, "Result back", Toast.LENGTH_SHORT).show()
        Log.d("logFl", "(resultCode == Activity.RESULT_OK):${(resultCode == Activity.RESULT_OK)}");

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            val result = Matisse.obtainResult(data)
            //pick_photo_text.setText(result.toString())
            var path: String? = null
            var uriStr:String?=null
            result.forEach {
                //path = it.toString()
                uriStr=it.toString()
                Log.i("logFl", "phototake返回值:" + it.toString());
                //注意下面这个root_path是在filespath.xml定义的,auth这个变量的值是在AndroidManifest.xml定义的权限
                if (it.toString().contains(auth)) {//拍照
//                    path = it.toString().replace("content://${auth}/root_path", "")
                    //Log.i("logFl", "图片物理路径1:${it.toString().replace("content://${auth}/root_path", "")}");
                    Log.i("logFl", "图片物理路径1:${path}");
                } else {
                    path = getRealPathFromUri(this, it)
                    Log.i("logFl", "图片物理路径2:${path}");
                }
                //为了测试 我每次直选一张图,可以设置的,直接就用这个返回uri就可以显示了~
                // pick_photo_img.setImageURI(it)
            }
            val data = Intent()
            val bundle = Bundle()
            bundle.putString("path", path ?: "")
            bundle.putString("uriStr",uriStr)
            data.putExtras(bundle)
            setResult(Activity.RESULT_OK, data)
            finish()
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
