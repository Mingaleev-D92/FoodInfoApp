package com.example.foodinfoapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodinfoapp.R
import com.example.foodinfoapp.adapters.MostPopularAdapter
import com.example.foodinfoapp.databinding.FragmentHomeBinding
import com.example.foodinfoapp.models.CategoryMeals
import com.example.foodinfoapp.models.Meal
import com.example.foodinfoapp.models.MealList
import com.example.foodinfoapp.retrofit.RetrofitInstance
import com.example.foodinfoapp.ui.activity.MealActivity
import com.example.foodinfoapp.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    // экземпляр из ViewModel(HomeViewModel)
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal

    private lateinit var popularItemsAdapter: MostPopularAdapter

    // ключи для интента
    companion object{
        const val MEAL_ID = "com.example.foodapp.fragments.idMeal"
        const val MEAL_NAME = "com.example.foodapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.foodapp.fragments.thumbMeal"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // инициализация
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]

        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm.getRandomMeal()
        // наблюдатель рандом. пищи
        observerRandomMeal()

        // прослушиватель клика
        onRandomMealClick()

        // получения популярных товаров для листа
        homeMvvm.getPopularItems()
        // устанавливаем список
        observePopularItemsLiveData()
        // представление ресайклера
        preparePopularItemsRecyclerView()
        // нажатие на популярный элемент
        onPopularItemClick()

    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {meal ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(
                mealsList = mealList as ArrayList<CategoryMeals>)
        }
    }

    private fun onRandomMealClick() {
        // прослушиватель клика случайной пищи
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            // передача информации
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        // прослушиватель данных в реальном времени
        homeMvvm.observeRandomMealLivedata().observe(viewLifecycleOwner
        ) { t ->
            Glide.with(this@HomeFragment)
                .load(t!!.strMealThumb)
                .into(binding.imgRandomMeal)

            // передача всей информации
            this.randomMeal = t
        }
    }

}