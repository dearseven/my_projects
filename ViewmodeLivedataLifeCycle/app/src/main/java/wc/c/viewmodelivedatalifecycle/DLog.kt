package wc.c.viewmodelivedatalifecycle

import android.os.Debug
import android.util.Log

class DLog {
    companion object {
        fun i(className: String, tag: String, log: String) {
            Log.i(tag, "\n-----------${className}\n${log}\n===============")
        }

        fun i(className: String, log: String) {
            Log.i("VLL", "\n-----------${className}\n${log}\n===============")
        }
    }
}