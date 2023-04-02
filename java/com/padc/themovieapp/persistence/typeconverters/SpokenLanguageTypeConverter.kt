package com.padc.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.themovieapp.data.vos.SpokenLanguageVO

class SpokenLanguageTypeConverter {
    @TypeConverter
    fun toString(spokenLanguageList: List<SpokenLanguageVO>?): String{
        return Gson().toJson(spokenLanguageList)
    }

    @TypeConverter
    fun toSpokenLanguage(SpokenLanguageJsonString: String) : List<SpokenLanguageVO>?{
        val spokenLanguageListType = object : TypeToken<List<SpokenLanguageVO>?>() {}.type
        return Gson().fromJson(SpokenLanguageJsonString,spokenLanguageListType)
    }
}