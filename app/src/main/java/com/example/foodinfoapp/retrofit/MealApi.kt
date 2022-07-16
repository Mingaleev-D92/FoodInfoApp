package com.example.foodinfoapp.retrofit

import com.example.foodinfoapp.models.CategoryList
import com.example.foodinfoapp.models.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Mingaleev D. 16.07.2022
 **/
interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    // получении ифнрмации о ебе
    @GET("lookup.php?")
    fun getMealDetails(@Query("i")id:String):Call<MealList>

    // список товаров
    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName:String):Call<CategoryList>
}