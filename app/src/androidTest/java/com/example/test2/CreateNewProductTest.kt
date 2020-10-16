package com.example.test2

import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.test2.Model.DataManager
import com.example.test2.Model.ProductInfo
import com.example.test2.Model.StoreInfo

import org.hamcrest.Matchers.*

import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CreateNewProductTest {
    @Rule
    @JvmField
    val productListActivity = ActivityTestRule(ListStoreProductsActivity::class.java)

    @Test
    fun createNewProduct() {
        val store = DataManager.stores["str1"]
        val productName = "Test Product"
        val productPrice = "1"
        val product = ProductInfo(store, productName, productPrice.toInt())

        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.spinnerNewProduct)).perform(click())
        onData(allOf(instanceOf(StoreInfo::class.java), equalTo(store))).perform(click())

        onView(withId(R.id.nameNewProduct)).perform(typeText(productName))
        onView(withId(R.id.priceNewProduct)).perform(typeText(productPrice))

        closeSoftKeyboard()
        pressBack()

        val newProduct = DataManager.products.last()
        assertEquals(newProduct, product)
    }

    @Test
    fun deleteProduct() {
        val productsSize = DataManager.products.size - 1
        val productDeleted = DataManager.products[2]

        onView(withId(R.id.recyclerViewListProducts)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                2,
                swipeLeft()
            )
        )
        val isHere = DataManager.products.contains(productDeleted)
        val productSizeAfter = DataManager.products.size
        assertEquals(productsSize, productSizeAfter)
        assertEquals(isHere, false)
    }

    @Test
    fun editProduct() {
        val product = DataManager.products[0]
        val productName = "New Name"
        val productPrice = "123"
        product.Name = productName
        product.Price = productPrice.toInt()

        onView(withId(R.id.recyclerViewListProducts)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.nameNewProduct)).perform(replaceText(productName))
        onView(withId(R.id.priceNewProduct)).perform(replaceText(productPrice))

        closeSoftKeyboard()
        pressBack()

        val newProduct = DataManager.products[0]
        assertEquals(newProduct, product)
    }

    @Test
    fun navigationPrevious(){
        val productNameExpected = DataManager.products[0].Name
        val productPriceExpected = DataManager.products[0].Price
        onView(withId(R.id.recyclerViewListProducts)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )
        onView(withId(R.id.action_back)).perform(click())
        val productName = getText(onView(withId(R.id.nameNewProduct)))
        val productPrice = getText(onView(withId(R.id.priceNewProduct)))

        assertEquals(productNameExpected, productName)
        assertEquals(productPriceExpected.toString(), productPrice)
    }

    @Test
    fun navigationNext(){
        val productNameExpected = DataManager.products[2].Name
        val productPriceExpected = DataManager.products[2].Price
        onView(withId(R.id.recyclerViewListProducts)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )
        onView(withId(R.id.action_next)).perform(click())
        val productName = getText(onView(withId(R.id.nameNewProduct)))
        val productPrice = getText(onView(withId(R.id.priceNewProduct)))

        assertEquals(productNameExpected, productName)
        assertEquals(productPriceExpected.toString(), productPrice)
    }

    @Test
    fun navigationPreviousDisable(){
        onView(withId(R.id.recyclerViewListProducts)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.action_back)).check(matches(not(isEnabled())));
        onView(withId(R.id.action_next)).check(matches(isEnabled())).perform(click())
        onView(withId(R.id.action_back)).check(matches(isEnabled()));
    }

    @Test
    fun navigationNextDisable(){
        onView(withId(R.id.recyclerViewListProducts)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                DataManager.products.lastIndex,
                click()
            )
        )
        val x = onView(withId(R.id.action_next)).check(matches(not(isEnabled())));
        onView(withId(R.id.action_back)).check(matches(isEnabled())).perform(click())
        onView(withId(R.id.action_next)).check(matches(isEnabled()));
    }



}

