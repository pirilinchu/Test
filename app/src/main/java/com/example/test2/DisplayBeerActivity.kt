package com.example.test2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2.Model.BeerModel
import com.example.test2.adapters.BeerListAdapter
import com.example.test2.services.BeerApi
import com.example.test2.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_display_beer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DisplayBeerActivity : AppCompatActivity() {

    var beerList: MutableList<BeerModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_beer)
        getListBeer()
    }

    private fun getListBeer() {
        var beerService = ServiceBuilder.buildService(BeerApi::class.java)
        var call = beerService.getBeerList()
        call.enqueue(object : Callback<List<BeerModel>>{
            override fun onResponse(
                call: Call<List<BeerModel>>,
                response: Response<List<BeerModel>>
            ) {
                Toast.makeText(applicationContext, "Ok", Toast.LENGTH_SHORT).show()
                for (i in response.body()!!){
                    beerList.add(i)
                }
                var adapterBeer = BeerListAdapter(applicationContext, beerList)
                RecyclerViewBeer.layoutManager = LinearLayoutManager(applicationContext)
                RecyclerViewBeer.adapter = adapterBeer
            }

            override fun onFailure(call: Call<List<BeerModel>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                Log.d("TAG", t.message.toString())
            }
        })
    }
}