package dev.abhikhm.storeadmin.model

data class AllOrderModel(
    val name : String?="",
    val orderId : String?="",
    val userId : String?="",
    val status : String?="",
    val productId : String?="",
    val price : String?=""
)
