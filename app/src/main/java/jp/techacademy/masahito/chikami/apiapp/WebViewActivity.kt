package jp.techacademy.masahito.chikami.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.loadUrl(intent.getStringExtra(KEY_URL).toString())
    }

    companion object {
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, url: String) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_URL, url))
        }
    }
}