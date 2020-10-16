package com.example.test2

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.Model.BeerModel
import com.example.test2.Model.DataManager
import kotlinx.android.synthetic.main.activity_list_store_products.*
import kotlinx.android.synthetic.main.app_bar_list_products.*

class ListStoreProductsActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var swipedBackground: ColorDrawable = ColorDrawable(Color.parseColor("#ED5E68"))
    private lateinit var deleteIcon: Drawable

    private val productLayoutManager by lazy {
        GridLayoutManager(this, resources.getInteger(R.integer.product_grid_span))
    }

    private val productRecyclerAdapter by lazy {
        ProductRecyclerAdapter(this, DataManager.products)
    }

    private val storeLayoutManager by lazy {
        GridLayoutManager(this, resources.getInteger(R.integer.store_grid_span))
    }

    private val storeRecyclerAdapter by lazy {
        StoreRecyclerAdapter(this, DataManager.stores.values.toList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_store_products)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            var intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        //val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*  appBarConfiguration = AppBarConfiguration(
              setOf(
                  R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
              ), drawerLayout
          )
          setupActionBarWithNavController(navController, appBarConfiguration)
          navView.setupWithNavController(navController)*/

        var toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        //this

        displayProducts()

        deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_delete_24)!!

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                productRecyclerAdapter.removeItem(viewHolder)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - deleteIcon.intrinsicHeight)/2
                swipedBackground.setBounds(itemView.right + dX.toInt(), itemView.top +40, itemView.right, itemView.bottom - 40)
                deleteIcon.setBounds(itemView.right - iconMargin - deleteIcon.intrinsicWidth, itemView.top + iconMargin, itemView.right - iconMargin, itemView.bottom - iconMargin )

                swipedBackground.draw(c)
                deleteIcon.draw(c)

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX/3,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewListProducts)



    }

    private fun displayProducts() {
        recyclerViewListProducts.layoutManager = productLayoutManager
        recyclerViewListProducts.adapter = productRecyclerAdapter
    }

    private fun displayStores() {
        recyclerViewListProducts.layoutManager = storeLayoutManager
        recyclerViewListProducts.adapter = storeRecyclerAdapter
    }


    override fun onResume() {
        super.onResume()
        recyclerViewListProducts.adapter?.notifyDataSetChanged()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_products -> {
                displayProducts()
            }
            R.id.nav_stores -> {
                displayStores()
            }
            R.id.nav_information -> {
                val m = getString(R.string.information_result, DataManager.stores.size, DataManager.products.size)
                Snackbar.make(recyclerViewListProducts, m, Snackbar.LENGTH_LONG).show()
            }
            R.id.nav_beers -> {
                var intentBeer = Intent(applicationContext, DisplayBeerActivity::class.java)
                startActivity(intentBeer)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun handleSelection(message: String) {
        Snackbar.make(recyclerViewListProducts, message, Snackbar.LENGTH_LONG).show()
    }



/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.list_store_products, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/
}
