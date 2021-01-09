package com.example.cryptoappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoappkotlin.adapter.CoinInfoAdapter
import com.example.cryptoappkotlin.model.CoinPriceInfo
import com.example.cryptoappkotlin.viewModel.CoinViewModel
import com.example.cryptoappkotlin.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.activity_coin_price_list.*

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var detailViewModel: DetailViewModel
    private var adapter: CoinInfoAdapter = CoinInfoAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        rvCoinPriceList.adapter = adapter
        adapter.onCoinInfoClickListener = object : CoinInfoAdapter.OnCoinInfoClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent = detailViewModel
                    .newIntent(this@CoinPriceListActivity, coinPriceInfo.fromSymbol)
                startActivity(intent)
            }
        }
        viewModel.priceList?.observe(this, {
            Log.d("TEST", "Success in activity: $it")
            refreshLayout.isRefreshing = false
            adapter.coinInfoList = it
        })
        refreshLayout.setOnRefreshListener {
            viewModel.loadData()
        }
    }
}