package jp.techacademy.masahito.chikami.apiapp

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FavoriteShop: RealmObject() {
    @PrimaryKey
    var id: String = ""
    var imageUrl: String = ""
    var name: String = ""
    var url: String = ""

    companion object {
        fun findAll(): List<FavoriteShop> = // お気に入りのShopを全件取得
            Realm.getDefaultInstance().use { realm ->
                realm.where(FavoriteShop::class.java)
                    .findAll().let {
                        realm.copyFromRealm(it)
                    }
            }

        fun findBy(id: String): FavoriteShop? = // お気に入りされているShopをidで検索して返す。お気に入りに登録されていなければnullで返す
            Realm.getDefaultInstance().use { realm ->
                realm.where(FavoriteShop::class.java)
                    .equalTo(FavoriteShop::id.name, id)
                    .findFirst()?.let {
                        realm.copyFromRealm(it)
                    }
            }

        fun insert(favoriteShop: FavoriteShop) = // お気に入り追加
            Realm.getDefaultInstance().executeTransaction {
                it.insertOrUpdate(favoriteShop)
            }

        fun delete(id: String) = // idでお気に入りから削除する
            Realm.getDefaultInstance().use { realm ->
                realm.where(FavoriteShop::class.java)
                    .equalTo(FavoriteShop::id.name, id)
                    .findFirst()?.also { deleteShop ->
                        realm.executeTransaction {
                            deleteShop.deleteFromRealm()
                        }
                    }
            }
    }
}