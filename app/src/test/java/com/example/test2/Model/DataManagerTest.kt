package com.example.test2.Model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DataManagerTest{
    @Before
    fun setUp()
    {
        DataManager.products.clear()
        DataManager.initializeProducts()
    }
    @Test
    fun addProduct(){
        val store = DataManager.stores.get("str1")!!
        val productName = "This is the name"
        val productPrice = 1

        var index = DataManager.addProduct(store, productName, productPrice)
        val product = DataManager.products[index]

        assertEquals(store, product.store)
        assertEquals(productName, product.Name)
        assertEquals(productPrice, product.Price)
    }
}