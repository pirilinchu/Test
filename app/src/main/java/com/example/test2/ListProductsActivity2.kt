package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.Model.DataManager
import com.example.test2.Model.ProductInfo
import kotlinx.android.synthetic.main.content_list_products2.*

class ListProductsActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_products2)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            var intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
        }

        listViewProducts.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, DataManager.products)
        listViewProducts.setOnItemClickListener { parent, view, position, id ->
            val activityMainIntent = Intent(this, MainActivity::class.java)
            activityMainIntent.putExtra(EXTRA_PRODUCT_POSITION, position)
            startActivity(activityMainIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        (listViewProducts.adapter as ArrayAdapter<ProductInfo>).notifyDataSetChanged()

    }
}