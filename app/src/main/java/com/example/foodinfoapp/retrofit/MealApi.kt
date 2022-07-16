package com.example.foodinfoapp.retrofit

import com.example.foodinfoapp.models.MealList
import retrofit2.Call
import retrofit2.http.GET

/**
 * @author Mingaleev D. 16.07.2022
 **/
interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
}