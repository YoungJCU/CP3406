package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApi {
    @GET("coins/markets")
    suspend fun getMarkets(
        @Query("vs_currency") vsCurrency: String,
        @Query("per_page") perPage: Int = 10
    ): List<CoinPrice>
}

class CryptoRepository {

    private val api: CoinGeckoApi = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CoinGeckoApi::class.java)

    suspend fun getTopCoins(currency: String): List<CoinPrice> {
        return api.getMarkets(vsCurrency = currency)
    }
}