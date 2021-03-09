package jp.techacademy.masahito.chikami.apiapp

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.realm.Realm
import jp.techacademy.masahito.chikami.apiapp.FavoriteShop.Companion.findBy
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.activity_web_view.favoriteImageView

class WebViewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        var shopUrl = intent.getSerializableExtra(KEY_URL).toString()
        webView.loadUrl(shopUrl)
        var shopId = intent.getSerializableExtra(KEY_ID).toString()
        Log.d("test",shopId + "←←←shopId(WebView)")
        var shopName = intent.getSerializableExtra(KEY_NAME).toString()
        Log.d("test",shopName + "←←←shopName(WebView)")
        var shopImageUrl = intent.getSerializableExtra(KEY_IMAGEURl).toString()
        Log.d("test",shopImageUrl+"←←←shopImageUrl(WebView)")
        var shopAddress = intent.getSerializableExtra(KEY_ADDRESS).toString()
        Log.d("test",shopAddress+"←←←shopAddress(WebView)")




        val isFavorite =FavoriteShop.findBy(shopId)
        Log.d("test",isFavorite.toString() +"←isFavoriteの中身")

        if(isFavorite != null){      //idが登録されているなら
            favoriteImageView.setImageResource(R.drawable.ic_star)  //白い星マークへ変更
        }else {  //お気に入り登録されてないなら
            favoriteImageView.setImageResource(R.drawable.ic_star_border)  //黄色星マークへ変更
        }

        favoriteImageView.setOnClickListener{  //リスナーは反応する
            if(isFavorite != null){      //idが登録されているなら
                favoriteImageView.setImageResource(R.drawable.ic_star_border)  //白い星マークへ変更
                FavoriteShop.delete(shopId)
                Log.d("test","2")


            }else{  //お気に入り登録されてないなら
                favoriteImageView.setImageResource(R.drawable.ic_star)  //黄色星マークへ変更
                FavoriteShop.insert(FavoriteShop().apply {
                    id = shopId
                    name = shopName
                    address = shopAddress
                    imageUrl = shopImageUrl
                    url = shopUrl
                })
                //Realmへの登録処理
                //一覧とお気に入り画面への通知
                Log.d("test","3")
                Log.d("test",shopId +"←id表示(WebViewで白→黄のとき)")
                Log.d("test",shopUrl +"←url表示(WebViewで白→黄のとき)")
            }
        }
    }

    companion object {
        private const val KEY_URL = "key_url"
        private const val KEY_ID = "key_id"
        private const val KEY_NAME = "key_name"
        private const val KEY_IMAGEURl = "key_imageurl"
        private const val KEY_ADDRESS = "key_address"


        fun start(activity: Activity, url: String, id: String, name:String, imageUrl:String, address:String) {
            activity.startActivity(
                Intent(activity, WebViewActivity::class.java)
                    .putExtra(KEY_URL, url) //urlとidだけだとお気に入りする情報として不十分
                    .putExtra(KEY_ID,id)
                    .putExtra(KEY_NAME,name)
                    .putExtra(KEY_IMAGEURl,imageUrl)
                    .putExtra(KEY_ADDRESS,address)

                    )
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