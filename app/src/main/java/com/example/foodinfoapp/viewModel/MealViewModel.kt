package com.example.foodinfoapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfoapp.db.MealDatabase
import com.example.foodinfoapp.models.Meal
import com.example.foodinfoapp.models.MealList
import com.example.foodinfoapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Mingaleev D. 16.07.2022
 **/
class MealViewModel(
    val mealDatabase: MealDatabase
):ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()!=null){
                    mealDetailsLiveData.value = response.body()!!.meals?.get(0)
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity", t.message.toString())
            }

        })
    }
    fun observerMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }

    fun  insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
}