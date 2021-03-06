package jp.techacademy.masahito.chikami.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.squareup.picasso.Picasso
import io.realm.Realm
import jp.techacademy.masahito.chikami.apiapp.FavoriteShop.Companion.findBy
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.activity_web_view.favoriteImageView
import kotlinx.android.synthetic.main.recycler_favorite.*
import jp.techacademy.masahito.chikami.apiapp.FavoriteAdapter.FavoriteItemViewHolder as shop

class WebViewActivity: AppCompatActivity() {

    var id: String = ""
    var imageUrl: String = ""
    var name: String = ""
    var url: String = ""
    var address: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.loadUrl(intent.getStringExtra(KEY_URL).toString())

        favoriteImageView.setOnClickListener{  //リスナーは反応する
            Log.d("test","1")
            if(){  //白星にはなるけど黄色に戻らない理由は,ここで白星になっても通知していなくて,Shopの中身は変わらず消えていないから,元々のお気に入り状態のまま?
                favoriteImageView.setImageResource(R.drawable.ic_star_border)  //白い星マークへ変更
                FavoriteShop.delete(id)
                Log.d("test","2")


                }else{  //お気に入り登録されてないなら
                favoriteImageView.setImageResource(R.drawable.ic_star)  //黄色星マークへ変更
                Log.d("test","3")
                FavoriteShop.insert(FavoriteShop())
                //Realmへの登録処理
                //一覧とお気に入り画面への通知

            }

        }
    }

    companion object {
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, url: String) {
            activity.startActivity(
                Intent(activity, WebViewActivity::class.java)
                    .putExtra(KEY_URL, url)
            )
        }
    }

}