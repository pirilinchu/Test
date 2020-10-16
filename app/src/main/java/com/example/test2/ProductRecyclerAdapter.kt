package com.example.test2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.Model.DataManager
import com.example.test2.Model.ProductInfo
import com.google.android.material.snackbar.Snackbar

class ProductRecyclerAdapter(
    private val context: Context,
    private val products: List<ProductInfo>
) : RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    private var removedPosition: Int = 0
    private var removedItem: ProductInfo = ProductInfo()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textStoreName = itemView.findViewById<TextView>(R.id.textViewProductStoreName)
        val textProductName = itemView.findViewById<TextView>(R.id.textViewProductName2)
        val textProductPrice = itemView.findViewById<TextView>(R.id.textViewProductPrice)
        var productPosition = 0
        init {
            itemView.setOnClickListener {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra(EXTRA_PRODUCT_POSITION, productPosition)
                context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_product_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.textStoreName.text = product.store?.Name
        holder.textProductName.text = product.Name
        holder.textProductPrice.text = product.Price.toString()
        holder.productPosition = position

    }

    override fun getItemCount(): Int {
        return products.size
    }

//    fun removeItem(position: Int){
//        DataManager.products.removeAt(position)
//        notifyItemRemoved(position)
//    }

    fun restore(product: ProductInfo, position: Int){
        DataManager.products.add(position, product)
        notifyItemInserted(position)
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        removedPosition = viewHolder.adapterPosition
        removedItem = DataManager.products[viewHolder.adapterPosition]

        DataManager.products.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar.make(viewHolder.itemView, "${removedItem.Name} deleted.", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                DataManager.products.add(removedPosition, removedItem)
                notifyItemInserted(removedPosition)
            }.show()
    }
}