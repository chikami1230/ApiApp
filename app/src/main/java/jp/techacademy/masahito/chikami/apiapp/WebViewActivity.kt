package jp.techacademy.masahito.chikami.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.i
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.realm.Realm
import jp.techacademy.masahito.chikami.apiapp.FavoriteShop.Companion.findBy
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.activity_web_view.favoriteImageView
import kotlinx.android.synthetic.main.recycler_favorite.*
import jp.techacademy.masahito.chikami.apiapp.FavoriteAdapter.FavoriteItemViewHolder as shop

class WebViewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        //開いた時にお気に入りか否か表示必要

        var shopUrl = intent.getSerializableExtra(KEY_URL).toString()
        var shopId = intent.getSerializableExtra(KEY_ID).toString()
        Log.d("test",shopId + "←←←表示されればshopIdはid取得している")
        webView.loadUrl(shopUrl)

        val isFavorite =FavoriteShop.findBy(shopId)

        if(isFavorite == null){      //idが登録されているなら
            favoriteImageView.setImageResource(R.drawable.ic_star_border)  //白い星マークへ変更
        }else {  //お気に入り登録されてないなら
            favoriteImageView.setImageResource(R.drawable.ic_star)  //黄色星マークへ変更
        }

        favoriteImageView.setOnClickListener{  //リスナーは反応する
            Log.d("test","1")
            if(isFavorite != null){      //idが登録されているなら
                favoriteImageView.setImageResource(R.drawable.ic_star_border)  //白い星マークへ変更
                FavoriteShop.delete(shopId)
                Log.d("test","2")

            }else{  //お気に入り登録されてないなら
                favoriteImageView.setImageResource(R.drawable.ic_star)  //黄色星マークへ変更
                Log.d("test","3")
                FavoriteShop.insert(FavoriteShop())
                //Realmへの登録処理
                //一覧とお気に入り画面への通知
                Log.d("test",shopId +"←id表示(WebView)")
                Log.d("test",shopUrl +"←url表示(WebView)")
            }
        }
    }

    companion object {
        private const val KEY_URL = "key_url"
        private const val KEY_ID = "key_id"

        fun start(activity: Activity, url: String, id: String) {
            activity.startActivity(
                Intent(
                    activity,
                    WebViewActivity::class.java
                )
                    .putExtra(KEY_URL, url)
                    .putExtra(KEY_ID, id))
        }
    }
}
/*
        }
        fun start(activity: Activity, id:String ) {
            activity.startActivity(Intent(activity,
                WebViewActivity::class.java)
                .putExtra(KEY_ID, id))
        }

    }

/*

 companion object{
        private const val KEY_SHOP = "key_shop"
        fun start(activity: Activity,shop: Shop){
            val favoriteShop = FavoriteShop().apply {
                id = shop.id
                name = shop.name
                imageUrl = shop.logoImage
                url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
            }
            activity.startActivity(
                Intent(activity,WebViewActivity::class.java)
                    .putExtra(KEY_SHOP,favoriteShop))
        }

.putExtra(KEY_ADDRESS,address)
                    .putExtra(KEY_NAME,name)
                    .putExtra(KEY_IMAGEURl,imageUrl)


,address:String ,name:String, imageUrl:String

var shopAddress = intent.getSerializableExtra(KEY_ADDRESS).toString()
        var shopImageUrl = intent.getSerializableExtra(KEY_IMAGEURl).toString()
        var shopName = intent.getSerializableExtra(KEY_NAME).toString()


 */