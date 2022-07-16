package com.example.foodinfoapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodinfoapp.models.*
import com.example.foodinfoapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Mingaleev D. 16.07.2022
 **/
class HomeViewModel : ViewModel() {

    // получение данных в реальном времени
    private var randomMealLiveData = MutableLiveData<Meal>()

    // получения списка для категории популярных товаров в реальном времени
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()

    // получение данных для категорий
    private var categoriesLiveData = MutableLiveData<List<Category>>()

    // запрос для получения еды
    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })

    }
    // прослушиватель данных в реальном времени во фрагменте
    fun observeRandomMealLivedata(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() != null) {
                        popularItemsLiveData.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    //TODO log
                }

            })
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body().let { categoryList ->
                    categoriesLiveData.postValue(categoryList?.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                // todo log message
            }

        })
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }
}

