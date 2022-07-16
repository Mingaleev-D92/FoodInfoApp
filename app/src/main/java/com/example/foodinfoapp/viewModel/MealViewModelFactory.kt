package com.example.foodinfoapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodinfoapp.db.MealDatabase

/**
 * @author Mingaleev D. 16.07.2022
 **/
class MealViewModelFactory(
    val mealDatabase: MealDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }
}