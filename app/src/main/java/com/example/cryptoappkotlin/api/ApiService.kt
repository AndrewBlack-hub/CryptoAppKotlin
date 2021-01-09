package com.example.cryptoappkotlin.api

import com.example.cryptoappkotlin.model.CoinInfoListOfData
import com.example.cryptoappkotlin.model.CoinPriceInfoRawData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "6f51df007ba2c5c36e471760f9171c37cea9b842f79633b3e5a884a724a67f03",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): Single<CoinInfoListOfData>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "6f51df007ba2c5c36e471760f9171c37cea9b842f79633b3e5a884a724a67f03",
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY,
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String?
    ): Single<CoinPriceInfoRawData>

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"

        private const val CURRENCY = "USD"
    }
}