package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import com.google.gson.annotations.SerializedName

data class CoinPrice(
    val id: String,
    val name: String,
    @SerializedName("current_price") val currentPrice: Double
)