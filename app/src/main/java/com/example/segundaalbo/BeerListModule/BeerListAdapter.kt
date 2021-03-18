package com.example.segundaalbo.BeerListModule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.segundaalbo.R
import com.example.segundaalbo.databinding.ItemBeerBinding
import com.example.segundaalbo.common.db.entities.BeerEntity

class BeerListAdapter(private val listener: BeerItemListener): RecyclerView.Adapter<BeerListAdapter.ViewHolder>() {

    interface BeerItemListener {
        fun onClickedBeerItem(v:View, beer:BeerEntity)
    }

    private val beers = mutableListOf<BeerEntity>()

    fun addItems(items: List<BeerEntity>) {
       // this.beers.clear()
        this.beers.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        this.beers.clear()
        notifyDataSetChanged()
    }

    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_beer, parent, false)

        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(beers.get(position))

    override fun getItemCount(): Int = beers.size

    inner class ViewHolder(view:View, private val listener: BeerListAdapter.BeerItemListener) : RecyclerView.ViewHolder(view),View.OnClickListener {
        val mBinding = ItemBeerBinding.bind(view)

        private lateinit var beer: BeerEntity

        init {
            mBinding.root.setOnClickListener(this)
        }

        fun bind(item: BeerEntity) {
            this.beer = item
            mBinding.tvBeerName.text = beer.name
            mBinding.tvBeerTagline.text = beer.tagline

            Glide.with(context)
                .load(beer.image_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(mBinding.imgBeerPhoto)

        }

        override fun onClick(v: View?) {
            listener.onClickedBeerItem(v!!, this.beer)
        }

    }
}