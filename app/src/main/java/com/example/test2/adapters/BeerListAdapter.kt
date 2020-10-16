package com.example.test2.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.Model.BeerModel
import com.example.test2.R
import com.squareup.picasso.Picasso

class BeerListAdapter (private val context: Context, private val beers: MutableList<BeerModel>):
    RecyclerView.Adapter<BeerListAdapter.ViewHolder>(){

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textBeerName = itemView?.findViewById<TextView?>(R.id.textViewBeerName)
        val textTagline = itemView?.findViewById<TextView?>(R.id.textViewTagline)
        val buttonImage = itemView?.findViewById<Button?>(R.id.buttonGetImage)
        val imageViewBeer = itemView?.findViewById<ImageView?>(R.id.imageViewBeer)
        var beerPosition = 0

        init {
            buttonImage?.setOnClickListener {
                var urlImage = beers[beerPosition].image_url
                Picasso.get().load(urlImage).into(imageViewBeer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_beer_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beer = beers[position]
        holder.textBeerName?.text = beer.name
        holder.textTagline?.text = beer.tagline
        holder.beerPosition = position
    }

    override fun getItemCount() = beers.size


}