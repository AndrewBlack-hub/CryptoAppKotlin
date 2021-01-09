package com.example.cryptoappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoappkotlin.api.ApiFactory.BASE_IMAGE_URL
import com.example.cryptoappkotlin.viewModel.DetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_coin_info.*

class CoinDetailInfoActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_coin_info)


        getDetailInfo()
    }

    private fun getDetailInfo() {
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
        }
        detailViewModel.getDetailInfo(fromSymbol)?.observe(this, {
            tvPrice.text = it.price.toString()
            tvLowDayPrice.text = it.lowDay.toString()
            tvHighDayPrice.text = it.highDay.toString()
            tvLastTrade.text = it.lastMarket
            tvLastUpdate.text = it.getFormattedTime()
            tvFromSymbol.text = it.fromSymbol
            tvToSymbol.text = it.toSymbol
            Picasso.get().load(it.getFullImageUrl()).into(ivLogoCoin)
        })
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
    }
}