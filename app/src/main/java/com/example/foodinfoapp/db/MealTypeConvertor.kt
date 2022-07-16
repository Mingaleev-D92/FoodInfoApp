package com.example.foodinfoapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

/**
 * @author Mingaleev D. 16.07.2022
 **/
@TypeConverters
class MealTypeConvertor {

    @TypeConverter
    fun fromAnyToString(attribute:Any?):String{
        if (attribute == null)
            return ""
        return attribute as String
    }

    @TypeConverter
    fun fromStringToAny(attribute: String?):Any{
        if (attribute == null)
            return ""
        return attribute
    }
}