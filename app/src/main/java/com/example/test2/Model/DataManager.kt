package com.example.test2.Model

object DataManager {

    var stores = HashMap<String, StoreInfo>()
    var products = ArrayList<ProductInfo>()
    init{
        //initialize variables
        initializeStores()
        initializeProducts()
    }

    fun initializeProducts() {
        var store = stores["str1"]!!
        var product = ProductInfo(store, "product one", 10)
        products.add(product)
        store = stores["str1"]!!
        product = ProductInfo(store, "product two", 20)
        products.add(product)
        store = stores["str2"]!!
        product = ProductInfo(store, "product three", 30)
        products.add(product)
        store = stores["str2"]!!
        product = ProductInfo(store, "product four", 40)
        products.add(product)
        store = stores["str3"]!!
        product = ProductInfo(store, "product five", 50)
        products.add(product)
        store = stores["str3"]!!
        product = ProductInfo(store, "product six", 60)
        products.add(product)
        store = stores["str4"]!!
        product = ProductInfo(store, "product seven", 70)
        products.add(product)
        store = stores["str5"]!!
        product = ProductInfo(store, "product eight", 80)
        products.add(product)
    }

    fun addProduct(store: StoreInfo, productName: String, productPrice: Int): Int{
        var product = ProductInfo(store, productName, productPrice)
        products.add(product)
        return products.lastIndex
    }

    fun initializeStores(){
        var store = StoreInfo("str1", "Store One")
        stores.set(store.IdStore, store)
        store = StoreInfo("str2", "Store Two")
        stores.set(store.IdStore, store)
        store = StoreInfo("str3", "Store Three")
        stores.set(store.IdStore, store)
        store = StoreInfo("str4", "Store Four")
        stores.set(store.IdStore, store)
        store = StoreInfo("str5", "Store Five")
        stores.set(store.IdStore, store)
    }
}