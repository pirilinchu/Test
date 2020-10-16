package com.example.test2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import com.example.test2.Model.DataManager
import com.example.test2.Model.ProductInfo
import com.example.test2.Model.StoreInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var productPosition = POSITION_NOT_SET
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapterStore = ArrayAdapter<StoreInfo>(
            this,
            android.R.layout.simple_spinner_item,
            DataManager.stores.values.toList()
        )
        adapterStore.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNewProduct.adapter = adapterStore

        productPosition = savedInstanceState?.getInt(EXTRA_PRODUCT_POSITION, POSITION_NOT_SET)
            ?: intent.getIntExtra(EXTRA_PRODUCT_POSITION, POSITION_NOT_SET)


        if (productPosition != POSITION_NOT_SET) {
            displayProduct()
            titleNewProduct.setText(R.string.edit_product)
        }
        else{
            createProduct()
            titleNewProduct.setText(R.string.new_product)

        }

    }

    private fun createProduct() {
        DataManager.products.add(ProductInfo())
        productPosition = DataManager.products.lastIndex
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(EXTRA_PRODUCT_POSITION, productPosition)
    }

    override fun onPause() {
        super.onPause()
        saveProduct()
    }

    private fun saveProduct() {
        val product = DataManager.products[productPosition]
        product.Name = nameNewProduct.text.toString()
        //product.Price = Integer.parseInt(nameNewProduct.text.toString())
        var priceAux = priceNewProduct.text.toString()
        product.Price = priceAux.toInt()
        product.store = spinnerNewProduct.selectedItem as StoreInfo
    }

    private fun displayProduct() {
        val product = DataManager.products[productPosition]
        priceNewProduct.setText(product.Price.toString())
        nameNewProduct.setText(product.Name)
        val storePosition = DataManager.stores.values.indexOf(product.store)
        spinnerNewProduct.setSelection(storePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_next -> {
                moveNext()
            }
            R.id.action_settings -> true
            R.id.action_back -> {
                moveBack()
            }
            else -> super.onOptionsItemSelected(item)

        }
        return super.onOptionsItemSelected(item)
    }



    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (productPosition >= DataManager.products.lastIndex) {
            var item = menu?.findItem(R.id.action_next)
            if (item != null) {
                item.icon = getDrawable(R.drawable.ic_baseline_block_24)
                item.isEnabled = false
            }
        }
        else if (productPosition == 0) {
            var item = menu?.findItem(R.id.action_back)
            if (item != null) {
                item.icon = getDrawable(R.drawable.ic_baseline_block_24)
                item.isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun invalidateOptionsMenu() {
        super.invalidateOptionsMenu()
    }

    private fun moveNext() {
        productPosition++
        displayProduct()
        invalidateOptionsMenu()
    }

    private fun moveBack() {
        productPosition--
        displayProduct()
        invalidateOptionsMenu()
    }
}