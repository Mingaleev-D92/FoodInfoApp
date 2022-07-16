package com.example.foodinfoapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodinfoapp.models.MealsByCategory
import com.example.foodinfoapp.models.MealsByCategoryList
import com.example.foodinfoapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Mingaleev D. 16.07.2022
 **/
class CategoryMealsViewModel : ViewModel() {

    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.api.getMealsByCategory(categoryName)
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    response.body().let { mealsList ->
                        mealsLiveData.postValue(mealsList!!.meals)

                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    //TODO t.message
                }
            })
    }

    fun observeMealsLiveData(): LiveData<List<MealsByCategory>> {
        return mealsLiveData
    }
}