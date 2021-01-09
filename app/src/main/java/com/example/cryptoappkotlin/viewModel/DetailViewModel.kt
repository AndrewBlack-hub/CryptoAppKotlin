package com.example.cryptoappkotlin.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoappkotlin.CoinDetailInfoActivity
import com.example.cryptoappkotlin.data.App
import com.example.cryptoappkotlin.model.CoinPriceInfo

class DetailViewModel: ViewModel() {

    private var database = App.getInstance()?.getDatabase()?.coinPriceInfoDao()

    fun getDetailInfo(fSym: String?): LiveData<CoinPriceInfo>? {
        return database?.getPriceInfoAboutCoin(fSym)
    }

    fun newIntent(context: Context, fromSymbol: String): Intent {
        val intent = Intent(context, CoinDetailInfoActivity::class.java)
        intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
        return intent
    }
    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
    }
}