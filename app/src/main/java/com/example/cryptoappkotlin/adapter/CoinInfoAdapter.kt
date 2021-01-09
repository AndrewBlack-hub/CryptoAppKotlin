package com.example.cryptoappkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoappkotlin.R
import com.example.cryptoappkotlin.model.CoinPriceInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_coin_info.view.*

class CoinInfoAdapter(private val context: Context): RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList = listOf<CoinPriceInfo>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onCoinInfoClickListener: OnCoinInfoClickListener? = null

    interface OnCoinInfoClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        return CoinInfoViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_coin_info, parent, false))
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        val symbolsTemplates = context.resources.getString(R.string.symbols_template)
        val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
        with(holder) {
            tvSymbols.text = String.format(symbolsTemplates, coin.fromSymbol, coin.toSymbol)
            tvPrice.text = coin.price.toString()
            tvLastUpdate.text = String.format(lastUpdateTemplate, coin.getFormattedTime())
            Picasso.get().load(coin.getFullImageUrl()).into(ivLogoCoin)
            itemView.setOnClickListener {
                onCoinInfoClickListener?.onCoinClick(coin)
            }
        }
    }

    override fun getItemCount() = coinInfoList.size

    inner class CoinInfoViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivLogoCoin: ImageView = view.ivLogoCoin
        val tvSymbols: TextView = view.tvSymbols
        val tvPrice: TextView = view.tvPriceLabel
        val tvLastUpdate: TextView = view.tvLastUpdateLabel
    }
}