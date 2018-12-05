package com.teetaa.fengling.activity.bedfriend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.teetaa.fengling.APIsUrl;
import com.teetaa.fengling.DebugLog;
import com.teetaa.fengling.R;
import com.teetaa.fengling.task.BedFriendUserInfoTask;
import com.teetaa.fengling.task.BedFriendUserInfoTask.WorkedOutListener;
import com.teetaa.fengling.util.Configure;
import com.teetaa.fengling.util.JsonToMap;
import com.teetaa.fengling.util.NetWorkTester;
import com.teetaa.fengling.util.ResourceGetter;
import com.teetaa.fengling.util.Tools;
import com.teetaa.fengling.util.UserInfoUtil;
import com.teetaa.fengling.widget.LoadingDiaglog;
import com.teetaa.fengling.widget.LoadingView;
import com.teetaa.fengling.widget.view.CircleImageView;
import com.teetaa.phototaker.main.v.SelectPhotoActivity;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * version9,见{@link Configure}的APP_INNER_VERSION，对应版本7.1<br/>
 * 个人中心点击头像进入个人详情信息
 *
 * @author wangxu
 */
public class BedFriendV9UserInfoDetailActivity extends Activity implements
        OnClickListener, WorkedOutListener {
//    private static final UMSocialService mController = UMServiceFactory
//            .getUMSocialService("com.umeng.login");
    private LoadingDiaglog loading=null;
    /**
     * 请求编码，进入年轻编辑
     */
    private static final int REQUEST_EDIT_AGE = 10;
    private static final int REQUEST_PERMISSION_CODE = 1111;
    private static final int REQUEST_TAKE_PHOTO = 1112;
    private Map<String, String> user = null;
    /**
     * 返回按钮
     */
    private View mBack;
    /**
     * 显示用户的头像
     */
    private CircleImageView mIvHeadPortrait;
    /**
     * 头像的布局的大按钮
     */
    private Button mBtnHeadPortrait;
    /**
     * 显示用户的昵称
     */
    private Button mBtnNick;
    /**
     * 显示用户的密码
     */
    private Button mBtnPwd;
    /**
     * 显示用户的邮箱
     */
    private Button mBtnEmail;
    /**
     * 显示用户的微博绑定状态
     */
    private Button mBtnWeibo;
    /**
     * 显示用户的qq绑定状态
     */
    private Button mBtnQq;
    /**
     * 显示用户的微信绑定状态
     */
    private Button mBtnWx;
    /**
     * 是否可以设置邮箱
     */
    private boolean canSettingEmail = false;
    // ----------------7.2----------------
    /**
     * 显示用户的性别
     */
    private Button mBtnGenger;
    /**
     * 显示用户的年龄
     */
    private Button mBtnAge;
    /**
     * 存储用户年龄的原始值
     */
    private int userAge0riginal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_friend_v9_user_info_detail);
        mHandler = new MHandler(this);
        // user = UserInfoUtil.getUserInfo(this);
        // 初始化控件
        mBack = findViewById(R.id.bed_friend_v9_userinfo_back);
        mBack.setOnClickListener(this);
        mIvHeadPortrait = (CircleImageView) findViewById(R.id.bed_friend_v9_userinfo_avatar);
        mIvHeadPortrait.setOnClickListener(this);
        mBtnHeadPortrait = (Button) findViewById(R.id.bed_friend_user_info_main_v9_avatar_btn);
        mBtnHeadPortrait.setOnClickListener(this);
        mBtnNick = (Button) findViewById(R.id.bed_friend_user_info_main_v9_nike);
        mBtnNick.setOnClickListener(this);
        mBtnPwd = (Button) findViewById(R.id.bed_friend_user_info_main_v9_pwd);
        mBtnPwd.setOnClickListener(this);
        mBtnEmail = (Button) findViewById(R.id.bed_friend_user_info_main_v9_email);
        mBtnEmail.setOnClickListener(this);
        mBtnWeibo = (Button) findViewById(R.id.bed_friend_user_info_main_v9_weibo);
        mBtnWeibo.setOnClickListener(this);
        mBtnQq = (Button) findViewById(R.id.bed_friend_user_info_main_v9_qq);
        mBtnQq.setOnClickListener(this);
        mBtnWx = (Button) findViewById(R.id.bed_friend_user_info_main_v9_wx);
        mBtnWx.setOnClickListener(this);
        //
        // initShow();
        // --------7.2------
        mBtnGenger = (Button) findViewById(R.id.bed_friend_user_info_main_v9_gender);
        mBtnGenger.setOnClickListener(this);
        mBtnAge = (Button) findViewById(R.id.bed_friend_user_info_main_v9_age);
        mBtnAge.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initShow();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    // 初始化显示
    private void initShow() {
        user = UserInfoUtil.getUserInfo(this);
        // 头像
        mIvHeadPortrait.boardColr = "#FFFFFF";
        mIvHeadPortrait.invalidate();
        String path = user.get(UserInfoUtil.AVATAR);
        if (path != null) {
            File f = new File(path);
            if (f.exists()) {
                mIvHeadPortrait.setImageBitmap(BitmapFactory.decodeFile(f
                        .getAbsolutePath()));
            }
        }

        if (user.get(UserInfoUtil.DISPLAY_NAME) != null) {
            mBtnNick.setText(user.get(UserInfoUtil.DISPLAY_NAME));
        }
        // 密码
        if (user.get(UserInfoUtil.PWD_STATUS) != null
                && user.get(UserInfoUtil.PWD_STATUS).equals("Y")) {
            mBtnPwd.setText(getString(R.string.click_here_to_modify));
        } else {
            mBtnPwd.setText(getString(R.string.click_2_set_email_n_pwd));
        }
        // 邮箱
        if (user.get(UserInfoUtil.ACCOUNT) != null
                && !user.get(UserInfoUtil.ACCOUNT).equals("")) {
            mBtnEmail.setText(user.get(UserInfoUtil.ACCOUNT));
            canSettingEmail = false;
        } else {
            canSettingEmail = true;
            // mTvFmid.setText("");
            mBtnEmail.setText(getString(R.string.click_2_set_email_n_pwd));
        }
        // 微博
        if (user.get(UserInfoUtil.WEIBO) != null) {
            mBtnWeibo.setText(getText(R.string.fmid_third_party_have_bound));
        } else {
            mBtnWeibo.setText(getText(R.string.fmid_third_party_not_bound));
        }
        // QQ
        if (user.get(UserInfoUtil.QQ) != null) {
            mBtnQq.setText(getText(R.string.fmid_third_party_have_bound));
        } else {
            mBtnQq.setText(getText(R.string.fmid_third_party_not_bound));
        }
        // 微信
        if (user.get(UserInfoUtil.WEIXIN) != null) {
            mBtnWx.setText(getText(R.string.fmid_third_party_have_bound));
        } else {
            mBtnWx.setText(getText(R.string.fmid_third_party_not_bound));
        }

        // 性别
        int stringId = ResourceGetter.getStringID(
                this,
                "user_gender_show_in_user_info_"
                        + user.get(UserInfoUtil.GENDER));
        mBtnGenger.setText(stringId);
        // 年龄
        userAge0riginal = Integer.parseInt(user.get(UserInfoUtil.AGE));
        stringId = ResourceGetter.getStringID(this,
                "user_age_show_in_user_info_" + user.get(UserInfoUtil.AGE));
        mBtnAge.setText(stringId);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mBack.getId()) {
            onBackPressed();
        } else if (v.getId() == mIvHeadPortrait.getId()
                || v.getId() == mBtnHeadPortrait.getId()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                showPickActivity();
            } else {
                requestCameraPemission();
            }
        } else if (v.getId() == mBtnNick.getId()) {
            Intent intt = new Intent(this, BedFriendUserInfoEditActivity.class);
            intt.setAction(BedFriendUserInfoEditActivity.EDIT_NICK_ACTION);
            startActivity(intt);
            // startActivityForResult(intt, 10);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } else if (v.getId() == mBtnPwd.getId()) {
            if (canSettingEmail) {
                Intent intt = new Intent();
                intt.setAction(BedFriendUserInfoEditActivity.EDIT_EMAIL_ACTION);
                startActivity(intt);
                // startActivityForResult(intt, 30);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else {
                Intent intt = new Intent();
                intt.setAction(BedFriendUserInfoEditActivity.EDIT_PWD_ACTION);
                startActivity(intt);
                // startActivity(intt, 20);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        } else if (v.getId() == mBtnEmail.getId()) {
            if (canSettingEmail) {
                Intent intt = new Intent();
                intt.setAction(BedFriendUserInfoEditActivity.EDIT_EMAIL_ACTION);
                startActivity(intt);
                // startActivityForResult(intt, 30);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        } else if (v.getId() == mBtnWeibo.getId()) {
//            if (user.get(UserInfoUtil.WEIBO) == null) {
//                // mController.getConfig().setSsoHandler(new SinaSsoHandler());
//                mController.doOauthVerify(this, SHARE_MEDIA.SINA,
//                        new UMAuthListenerImpl());
//            }
        } else if (v.getId() == mBtnQq.getId()) {
//            if (user.get(UserInfoUtil.QQ) == null) {
//                try {
//                    UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(
//                            BedFriendV9UserInfoDetailActivity.this,
//                            "100520828", "78bb4a90cdef10608722ecbfba779995");
//                    qqSsoHandler.addToSocialSDK();
//
//                    mController.doOauthVerify(
//                            BedFriendV9UserInfoDetailActivity.this,
//                            SHARE_MEDIA.QQ, new UMAuthListenerImpl());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        } else if (v.getId() == mBtnWx.getId()) {
//            if (!UmengShareUtil
//                    .isWXAppInstalledAndSupported(BedFriendV9UserInfoDetailActivity.this)) {
//                Toast.makeText(BedFriendV9UserInfoDetailActivity.this,
//                        R.string.not_wechat_conn_fail, Toast.LENGTH_SHORT)
//                        .show();
//                return;
//            }
//            if (user.get(UserInfoUtil.WEIXIN) == null) {
//                try {
//                    UMWXHandler umwxHandler = new UMWXHandler(
//                            BedFriendV9UserInfoDetailActivity.this,
//                            "wx538f8158ebf96e4d",
//                            "9e20d341bce3d19f52deaa5f6c648f3d");
//                    umwxHandler.addToSocialSDK();
//
//                    mController.doOauthVerify(
//                            BedFriendV9UserInfoDetailActivity.this,
//                            SHARE_MEDIA.WEIXIN, new UMAuthListenerImpl());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        } else if (v.getId() == mBtnAge.getId()) {
            Intent itnt = new Intent(this, BedFriendUserAgeSelectActivity.class);
            itnt.putExtra(BedFriendUserAgeSelectActivity.USER_SELECTED,
                    Integer.parseInt(user.get(UserInfoUtil.AGE)));
            startActivityForResult(itnt, REQUEST_EDIT_AGE);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } else if (v.getId() == mBtnGenger.getId()) {
            Intent intt = new Intent(this, BedFriendUserInfoEditActivity.class);
            intt.setAction(BedFriendUserInfoEditActivity.EDIT_GENDER_ACTION);
            startActivity(intt);
            // startActivityForResult(intt, 10);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPemission() {
        ArrayList<String> permissionList = new ArrayList<String>();
        int result = checkSelfPermission(Manifest.permission.CAMERA);
        if (result != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        result = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionList.size() == 0) {
            showPickActivity();
        } else {
            requestPermissions(permissionList.toArray(new String[permissionList.size()]), REQUEST_PERMISSION_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            int storagePermissionFlag = 0, cameraPermissionFlag = 0;
            for (int i = 0; i < permissions.length; i++) {
                String p = permissions[i];
                if (p.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    storagePermissionFlag = grantResults[i];
                } else {
                    cameraPermissionFlag = grantResults[i];
                }
            }
            //双双授权成功
            if (storagePermissionFlag == PackageManager.PERMISSION_GRANTED && cameraPermissionFlag == PackageManager.PERMISSION_GRANTED) {
                showPickActivity();
            } else {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) || !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    showPermissionRequestWhy();
                }
            }
        }
    }

    private void showPermissionRequestWhy() {
        showMessageOKCancel("风铃需要您提供访问您的存储和相机才能继续下一步", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                //设置去向意图
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);           //发起跳转
                startActivity(intent);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("去授权", okListener)
                .setNegativeButton("取消", cancelListener)
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        // Intent intt=new Intent(this,BedFriendUserInfoActivity6.class);
        // startActivity(intt);
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    // ---------------start设置用户头像------------------
//    // 关于头像修改的东西
    /**
     * 临时放头像的地址,用于上传的
     **/
    private static final String BF_USER_AVATAR = Environment
            .getExternalStorageDirectory() + "/fmclock/bf_user_avatar/real/";

//

    /**
     * 选择提示对话框,拍照或者相册图片
     */
    private void showPickActivity() {
        Intent intt = new Intent(this, SelectPhotoActivity.class);
        startActivityForResult(intt, REQUEST_TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_EDIT_AGE == requestCode) {
            if (NetWorkTester.getNetState(this) == NetWorkTester.NO_INTERNET) {
                Toast.makeText(this, getString(R.string.no_network_warning),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            final int age = data.getIntExtra(
                    BedFriendUserAgeSelectActivity.USER_SELECTED, 10);
            if (age != 10 && userAge0riginal != age) {// 如果不是出现未知或者等于原始值，则修改数据
                final LoadingView lv = new LoadingView(this);
                lv.show();
                final StringBuilder param = new StringBuilder();
                param.append("{\"parameter\":{\"fmnumber\":\"")
                        .append(UserInfoUtil.getUserInfo(this).get(
                                UserInfoUtil.FM_ID)).append("\",");
                param.append("\"age_segment\":\"")
                        .append(age)
                        .append("\"},\"interfaceName\":\"EditUserAgeSegment\"}");
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {
                            String ret = Tools.postToServer(APIsUrl.API,
                                    param.toString());
                            DebugLog.log(null, "param:" + param.toString()
                                    + "\nEditUserAgeSegment:" + ret, getClass());
                            JsonToMap jtm = new JsonToMap();
                            Map<String, String> retMap = new HashMap<String, String>();
                            final Map<String, String> map = jtm
                                    .jsonToMapWithNoArray(ret, retMap);
                            if (retMap.get("status").equals("success")) {
                                // 将用户信息写入
                                SharedPreferences pref = getSharedPreferences(
                                        /*
                                         * BedFriendUserInfoTask.
                                         * BED_FRIEND_USER_INFO_FILE
                                         */"bed_friend_user_info_file",
                                        Context.MODE_PRIVATE);
                                Editor editor = pref.edit();
                                editor.putString(UserInfoUtil.AGE, age + "");
                                editor.commit();
                                mHandler.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        lv.cancel();
                                        initShow();
                                    }
                                });
                            } else {
                                mHandler.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        lv.cancel();
                                        Toast.makeText(
                                                BedFriendV9UserInfoDetailActivity.this,
                                                map.get("errmsg") + "",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            // 失败
                            e.printStackTrace();
                            mHandler.post(new Runnable() {

                                @Override
                                public void run() {
                                    lv.cancel();
                                    Toast.makeText(
                                            BedFriendV9UserInfoDetailActivity.this,
                                            "修改失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();

            }
        } else if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK && data != null) {
                String path = data.getExtras().getString("path");
                String uriStr = data.getExtras().getString("uriStr");
                DebugLog.logFl(null, "filePath is:" + path, getClass());
                try {
                    startPhotoZoom(Uri.parse(uriStr));
                } catch (Exception e) {
                    e.printStackTrace();
                    DebugLog.logFl(null, "e:" + e.getMessage(), getClass());
                }
            }
        } else {
            switch (requestCode) {
                case 3:
                    if (data != null) {
                        setPicToViewAndUpload(data);
                    }
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }
//
    /**
     * 用户保存图片信息
     */
    private Bundle extras = null;

    /**
     * 用于上传的头像图片
     */
    public static File readyForUpload = null;

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToViewAndUpload(Intent picdata) {
        extras = picdata.getExtras();
        if (extras != null) {
            if(loading==null){
                loading=new LoadingDiaglog();
            }
            loading.showDialog(this);
            Bitmap photo = extras.getParcelable("data");
            try {
                File real = new File(BF_USER_AVATAR);
                if (!real.exists())
                    real.mkdirs();
                readyForUpload = new File(BF_USER_AVATAR + "/"
                        + System.currentTimeMillis() + ".jpg");
                readyForUpload.createNewFile();
                photo.compress(Bitmap.CompressFormat.JPEG, 100,
                        new FileOutputStream(readyForUpload));
            } catch (Exception e) {
                e.printStackTrace();
            }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        loading.hideDialog();
                        mIvHeadPortrait.boardColr = "#FFFFFF";
                        mIvHeadPortrait.invalidate();
                        if (readyForUpload != null) {
                            //File f = new File(path);
                            if (readyForUpload.exists()) {
                                DebugLog.logFl(null, "show image readyForUpload:" + readyForUpload.getAbsolutePath(), getClass());
                                mIvHeadPortrait.setImageBitmap(BitmapFactory.decodeFile(readyForUpload
                                        .getAbsolutePath()));
                                mIvHeadPortrait.invalidate();
                            }
                        }
                        BedFriendUserInfoTask bfuTask = new BedFriendUserInfoTask(BedFriendV9UserInfoDetailActivity.this,
                                BedFriendV9UserInfoDetailActivity.this);
                        bfuTask.execute(BedFriendUserInfoTask.OPT_UPLOAD_USER_AVATAR, BedFriendV9UserInfoDetailActivity.this,
                                readyForUpload.getAbsolutePath(),
                                UserInfoUtil.getUserInfo(BedFriendV9UserInfoDetailActivity.this).get(UserInfoUtil.FM_ID));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 2000);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loading!=null){
            loading.destroy();
            loading=null;
        }
    }

    // ---------------end设置用户头像--------------------

    // ----------------start用户绑定第三方----------------
//    private class UMAuthListenerImpl implements UMAuthListener {
//
//        @Override
//        public void onCancel(SHARE_MEDIA arg0) {
//            // loadingView.cancel();
//
//        }
//
//        @Override
//        public void onComplete(Bundle arg0, SHARE_MEDIA arg1) {
//            // loadingView.cancel();
//            DebugLog.log(null, "222222222", getClass());
//            String uid = arg0.getString("uid");
//            String unionid = "";
//
//            DebugLog.log(null, "uid:" + uid, getClass());
//            if (uid != null && !TextUtils.isEmpty(uid)) {
//                int platformType = 0;
//                if (arg1 == SHARE_MEDIA.SINA)
//                    platformType = BedFriendUserInfoTask.PLATFORM_SINA;
//                else if (arg1 == SHARE_MEDIA.QQ)
//                    platformType = BedFriendUserInfoTask.PLATFORM_QQ;
//                else {
//                    unionid = arg0.getString("unionid");
//                    if (unionid == null) {
//                        String at = arg0.getString("access_token");
//                        SimpleHttp.Result r = SimpleHttp.get("https://api.weixin.qq.com/sns/userinfo", "access_token=" + at + "&openid=" + arg0.get("uid"));
//                        if (r.returnCode == SimpleHttp.Result.CODE_SUCCESS) {
//                            DebugLog.log(null, "#####:" + r.retString, getClass());
//                            Map<String, Object> m = new HashMap<String, Object>();
//                            JsonToMap jtm = new JsonToMap();
//                            m = jtm.jsonToMapWithArray(r.retString, m);
//                            DebugLog.log(null, "unionid:" + ((String) m.get("unionid")), getClass());
//                            unionid = (String) m.get("unionid");
//                        }
//                    }
//
//                    platformType = BedFriendUserInfoTask.PLATFORM_WEIXIN;
//
//                }
//                // 把绑定的结果返回到服务器
//                BedFriendUserInfoTask bfuTask = new BedFriendUserInfoTask(
//                        BedFriendV9UserInfoDetailActivity.this,
//                        BedFriendV9UserInfoDetailActivity.this);
//
//                try {
//                    bfuTask.execute(BedFriendUserInfoTask.OPT_ACCOUT_BIND_PLATFORM,
//                            BedFriendV9UserInfoDetailActivity.this,
//                            user.get(UserInfoUtil.FM_ID), uid + "," + unionid, platformType);
//
//                } catch (IllegalStateException e) {
//                    DebugLog.log(null, "报错了呀", BedFriendV9UserInfoDetailActivity.class);
//                    Toast.makeText(BedFriendV9UserInfoDetailActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        }
//
//        @Override
//        public void onError(SocializeException arg0, SHARE_MEDIA arg1) {
//            Toast.makeText(BedFriendV9UserInfoDetailActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void onStart(SHARE_MEDIA arg0) {
//
//        }
//
//    }
//
//    // ----------------end用户绑定第三方------------------
    public void onWorkedOut(Object[] result) {
        if ((Integer) result[0] == BedFriendUserInfoTask.OPT_UPLOAD_USER_AVATAR) {// 用户设置头像
            Object obj = result[1];
            if (obj != null) {
                if ((Boolean) result[1]) {

                    File folder = getDir(Configure.USER_AVATAR_FOLDER,
                            Context.MODE_PRIVATE);
                    if (!folder.exists())
                        folder.mkdirs();

                    try {
                        SharedPreferences pref = getSharedPreferences(
                                BedFriendUserInfoTask.BED_FRIEND_USER_INFO_FILE,
                                Context.MODE_PRIVATE);
                        String avatar_path = pref.getString("AVATAR", null);
                        File avatar = new File(folder,
                                avatar_path.substring(avatar_path
                                        .lastIndexOf("/") + 1));
                        avatar.createNewFile();

                        FileInputStream fis = new FileInputStream(
                                readyForUpload);
                        FileOutputStream fos = new FileOutputStream(avatar);
                        byte buf[] = new byte[1024];
                        int count = -1;
                        while ((count = fis.read(buf, 0, buf.length)) > -1) {
                            fos.write(buf, 0, count);
                        }
                        fos.flush();
                        fos.close();
                        fis.close();
                        // 更改显示的图片
                        Bitmap photo = BitmapFactory.decodeFile(avatar
                                .getAbsolutePath());
                        Drawable drawable = new BitmapDrawable(getResources(),
                                photo);
                        mIvHeadPortrait.setImageDrawable(drawable);
                        // BitmapDrawable bd = (BitmapDrawable) drawable;
                        // Bitmap bm = bd.getBitmap();
                        // showBg(bm);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //else {
//                Toast.makeText(this,
//                        getString(R.string.update_user_avatar_failed), 0)
//                        .show();
//            }
//        } else if ((Integer) result[0] == BedFriendUserInfoTask.OPT_ACCOUT_BIND_PLATFORM) {// 用户绑定第三方
//            Map<String, String> m = (Map<String, String>) result[1];
//            if (m.get("status") != null & m.get("status").equals("success")) {
//                user = new UserInfoUtil().getUserInfo(this);
//                // 重新刷新一下显示
//                initShow();
//            } else {
//                Toast.makeText(this, m.get("errmsg"), 0).show();
//            }
//        }
    }

    private MHandler mHandler = null;

    private static class MHandler extends Handler {
        private WeakReference<BedFriendV9UserInfoDetailActivity> wk = null;

        public MHandler(BedFriendV9UserInfoDetailActivity activity) {
            wk = new WeakReference<BedFriendV9UserInfoDetailActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
