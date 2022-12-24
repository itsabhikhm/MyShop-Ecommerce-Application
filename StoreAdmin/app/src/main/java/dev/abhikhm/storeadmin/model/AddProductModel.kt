package dev.abhikhm.storeadmin.model

data class AddProductModel(
    val ProductName : String? = "",
    val ProductDescription : String? = "",
    val ProductCoverImg : String? = "",
    val ProductCategory : String? = "",
    val ProductId : String? = "",
    val ProductMrp : String? = "",
    val ProductSp : String? = "",
    val ProductImages : ArrayList<String>
)
