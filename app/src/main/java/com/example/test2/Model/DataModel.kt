package com.example.test2.Model

data class StoreInfo(var IdStore: String, var Name: String) {

    override fun toString(): String {
        return Name
    }
}

data class ProductInfo(
    var store: StoreInfo? = null,
    var Name: String? = null,
    var Price: Int? = null
) {

}
