package chat.sh.orz.cyan.orz.goflutter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import chat.sh.orz.cyan.orz.R
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.renderer.FlutterUiDisplayListener
import io.flutter.plugins.GeneratedPluginRegistrant
import kotlinx.android.synthetic.main.activity_go_flutter.*;

class GoFlutterActivity : FlutterActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_flutter)

        flutterEngine!!.navigationChannel.setInitialRoute("/index")
        flutterEngine!!.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        )
        flutterEngine!!.also {
            GeneratedPluginRegistrant.registerWith(it)
            createMethodHandler(it)
        }
        //
        //val flutterView = FlutterView(this)
        //
        // 关键代码，将Flutter页面显示到FlutterView
        MyFlutterView.addOnFirstFrameRenderedListener(object : FlutterUiDisplayListener {
            override fun onFlutterUiDisplayed() {
                MyFlutterView.visibility = View.VISIBLE
            }

            override fun onFlutterUiNoLongerDisplayed() {
            }

        })
        MyFlutterView.attachToFlutterEngine(flutterEngine!!)
    }

    private fun createMethodHandler(it: FlutterEngine) {
    }
}