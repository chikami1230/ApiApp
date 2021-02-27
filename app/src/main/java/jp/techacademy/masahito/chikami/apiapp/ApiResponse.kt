package jp.techacademy.masahito.chikami.apiapp

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("results")
    val results: Results
)

data class Results(
    @SerializedName("shop")
    val shop: List<Shop>
)

data class Shop(
    @SerializedName("coupon_urls")
    val couponUrls: CouponUrls,
    @SerializedName("id")
    val id: String,
    @SerializedName("logo_image")
    val logoImage: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String


)

data class CouponUrls(
    @SerializedName("pc")
    val pc: String,
    @SerializedName("sp")
    val sp: String
)
