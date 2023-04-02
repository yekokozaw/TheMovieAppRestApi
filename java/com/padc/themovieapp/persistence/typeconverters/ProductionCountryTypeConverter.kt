package com.padc.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.themovieapp.data.vos.ProductionCountryVO

class ProductionCountryTypeConverter {
    @TypeConverter
    fun toString(genreList: List<ProductionCountryVO>?): String{
        return Gson().toJson(genreList)
    }

    @TypeConverter
    fun toProductionCountries(productionCountryJsonString: String) : List<ProductionCountryVO>?{
        val productionCountryListType = object : TypeToken<List<ProductionCountryVO>?>() {}.type
        return Gson().fromJson(productionCountryJsonString,productionCountryListType)
    }
}