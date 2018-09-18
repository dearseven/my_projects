package chat.sh.orz.cyan.orz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {
    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
//        fullscreen_content.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LOW_PROFILE or
//                View.SYSTEM_UI_FLAG_FULLSCREEN or
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
//                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
//                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private var mVisible: Boolean = false
    var _url = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mVisible = true

        hide()
        browser.setCWebViewEventHandler(object : CWebView.CWebViewEventHandler {
            override fun onPageFinshed(cWebView: CWebView?, loadError: Boolean) {
            }

            override fun onStartLoad(cWebView: CWebView?, url: String?) {
            }

            override fun loadProgress(view: CWebView?, newProgress: Int) {
            }
        })
        okButton.setOnClickListener {
            val ip = ip_input.text.toString()
            val url = "http://${ip}:8020/OrzWeb/launcher.html"
            _url = url
            input_parent.visibility = View.GONE

            browser_parent.visibility = View.VISIBLE
            browser.visibility = View.VISIBLE
            browser.loadUrl(url)
            refreshButton.setOnClickListener {
                browser.reload()
            }
        }
    }


    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()

        mVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        //mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 0

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 0
    }
}
