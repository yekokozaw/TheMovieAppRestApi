package com.padc.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.themovieapp.data.vos.ProductionCompanyVO

class ProductionCompanyTypeConverter {
    @TypeConverter
    fun toString(ProductionCompanyList: List<ProductionCompanyVO>?): String{
        return Gson().toJson(ProductionCompanyList)
    }

    @TypeConverter
    fun toProductionCompany(productionCompanyJsonString: String) : List<ProductionCompanyVO>?{
        val productionCompanyList = object : TypeToken<List<ProductionCompanyVO>?>() {}.type
        return Gson().fromJson(productionCompanyJsonString,productionCompanyList)
    }
}