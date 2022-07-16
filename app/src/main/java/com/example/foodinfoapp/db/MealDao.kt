package com.example.foodinfoapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodinfoapp.models.Meal

/**
 * @author Mingaleev D. 16.07.2022
 **/
@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal) {
    }

    @Delete
    suspend fun delete(meal: Meal) {
    }

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<Meal>>
}