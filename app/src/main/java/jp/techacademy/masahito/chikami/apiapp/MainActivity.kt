package jp.techacademy.masahito.chikami.apiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentCallback {

    private val viewPagerAdapter by lazy { ViewPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ViewPager2の初期化
        viewPager2.apply {
            adapter = viewPagerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL // スワイプの向き横（ORIENTATION_VERTICAL を指定すれば縦スワイプで実装可能です）
            offscreenPageLimit = viewPagerAdapter.itemCount // ViewPager2で保持する画面数
        }

        // TabLayoutの初期化
        // TabLayoutとViewPager2を紐づける
        // TabLayoutのTextを指定する
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.setText(viewPagerAdapter.titleIds[position])
        }.attach()
    }


    override fun onClickItem(shop: Shop){
        val url = if(shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
        WebViewActivity.start(
            this,
            url,
            shop.id,
            shop.name,
            shop.logoImage,
            shop.address)
        Log.d("test",url+"←url(MainActivity)")
        Log.d("test",shop.id+"←shop.id")
        Log.d("test",shop.name+ "←shop.name")
        Log.d("test",shop.address+ "←shop.address")
        Log.d("test",shop.logoImage+"←shop.logoImage")
    }

    override fun onClickItem2(favoriteShop: FavoriteShop){
        WebViewActivity.start(
            this,
            favoriteShop.url,
            favoriteShop.id,
            favoriteShop.name,
            favoriteShop.imageUrl,
            favoriteShop.address)
        Log.d("test",favoriteShop.url+"←favoriteShop.url(MainActivity)")
        Log.d("test",favoriteShop.id+"←favoriteShop.id")
        Log.d("test",favoriteShop.name+ "←favoriteShop.name")
        Log.d("test",favoriteShop.address+ "←favoriteShop.address")
        Log.d("test",favoriteShop.imageUrl+"favoriteShop.imageUrl")

    }


/*
adress:String, name:String, imageUrl:String,
, name, imageUrl, address
 */

    override fun onAddFavorite(shop: Shop) { // Favoriteに追加するときのメソッド(Fragment -> Activity へ通知する)
        FavoriteShop.insert(FavoriteShop().apply {
            id = shop.id
            name = shop.name
            imageUrl = shop.logoImage
            url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
            address = shop.address
            Log.d("test","新着順のリストの星タップしてお気に入り追加した")
        }
        )
        (viewPagerAdapter.fragments[VIEW_PAGER_POSITION_FAVORITE] as FavoriteFragment).updateData()
    }

    override fun onDeleteFavorite(id: String) { // Favoriteから削除するときのメソッド(Fragment -> Activity へ通知する)
        showConfirmDeleteFavoriteDialog(id)
        Log.d("test","新着順のリストの星タップしてお気に入り削除のアラート呼び出した")
    }

    private fun showConfirmDeleteFavoriteDialog(id: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_favorite_dialog_title)
            .setMessage(R.string.delete_favorite_dialog_message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                deleteFavorite(id)
                Log.d("test","アラートではいを押して削除した")
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                Log.d("test","アラートでいいえ押して削除するのをキャンセルした")
            }
            .create()
            .show()


    }

    private fun deleteFavorite(id: String) {
        FavoriteShop.delete(id)
        Log.d("test","削除した時呼び出される")
        (viewPagerAdapter.fragments[VIEW_PAGER_POSITION_API] as ApiFragment).updateView()
        (viewPagerAdapter.fragments[VIEW_PAGER_POSITION_FAVORITE] as FavoriteFragment).updateData()

    }

    companion object {
        private const val VIEW_PAGER_POSITION_API = 0
        private const val VIEW_PAGER_POSITION_FAVORITE = 1
    }
}