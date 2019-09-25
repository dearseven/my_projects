package cyan.cyan_todo.plugins;

import android.content.Context;
import android.widget.Toast;

public class ToastPlugin {
    public static String FLUTTER_CALL_NAME="FLUTTER_CALL_NAME_showToast";

    public static void showToast(Context ctx, String str){
        Toast.makeText(ctx,str,Toast.LENGTH_SHORT).show();
    }
}
