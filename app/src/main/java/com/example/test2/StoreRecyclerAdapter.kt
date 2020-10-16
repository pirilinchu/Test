package com.example.test2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.Model.StoreInfo
import com.google.android.material.snackbar.Snackbar

class StoreRecyclerAdapter (
    private val context: Context,
    private val stores: List<StoreInfo>
) : RecyclerView.Adapter<StoreRecyclerAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val textStoreName = itemView?.findViewById<TextView>(R.id.textViewStoreId)
        val textStoreId = itemView?.findViewById<TextView>(R.id.textViewStoreName2)
        var storePosition = 0
        init {
            itemView?.setOnClickListener {
                Snackbar.make(it, stores[storePosition]?.Name, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_store_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var store= stores[position]
        holder.textStoreName?.text = store.Name
        holder.textStoreId?.text = store.IdStore
        holder.storePosition = position
    }

    override fun getItemCount(): Int {
        return stores.size
    }
}