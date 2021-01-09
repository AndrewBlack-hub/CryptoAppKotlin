package com.example.cryptoappkotlin.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cryptoappkotlin.api.ApiFactory
import com.example.cryptoappkotlin.data.App
import com.example.cryptoappkotlin.data.CoinPriceInfoDao
import com.example.cryptoappkotlin.model.CoinPriceInfo
import com.example.cryptoappkotlin.model.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel : ViewModel() {

    private val database: CoinPriceInfoDao? = App.getInstance()?.getDatabase()?.coinPriceInfoDao()
    private val compositeDisposable = CompositeDisposable()

    val priceList = database?.getPriceList()

    init {
        loadData()
    }

    fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map { it -> it.data?.map { it.coinInfo?.name }?.joinToString(",") }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
//            .delaySubscription(10, TimeUnit.SECONDS)
//            .repeat()
//            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                database?.insertPriceList(it)
                Log.d("TEST", "Success: $it")
            }, {
                Log.d("TEST", "Failure: ${it.message}")
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(
            coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo> {
        val result = arrayListOf<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                        currencyJson.getAsJsonObject(currencyKey), CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}