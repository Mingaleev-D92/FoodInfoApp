package com.example.foodinfoapp.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodinfoapp.R
import com.example.foodinfoapp.databinding.ActivityMealBinding
import com.example.foodinfoapp.models.Meal
import com.example.foodinfoapp.ui.fragments.HomeFragment
import com.example.foodinfoapp.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String

    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)

        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        setContentView(binding.root)
        // получение информации через интент
        getMealInformationFromIntent()
        // передача информации изображения
        setInformationInViews()
        // загрузка
        loadingCase()

        // получение подробной информации о еде
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()
        // клик перехода загрузки Ютую
        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal = t
                binding.tvCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvInstructionsSt.text = meal.strInstructions

                youtubeLink = meal.strYoutube!!
            }

        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.teal_200))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.apply {
            btnAddToFav.visibility = View.INVISIBLE
            tvInstructions.visibility = View.INVISIBLE
            tvCategory.visibility = View.INVISIBLE
            tvArea.visibility = View.INVISIBLE
            imgYoutube.visibility = View.INVISIBLE
        }
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.apply {
            btnAddToFav.visibility = View.VISIBLE
            tvInstructions.visibility = View.VISIBLE
            tvCategory.visibility = View.VISIBLE
            tvArea.visibility = View.VISIBLE
            imgYoutube.visibility = View.VISIBLE
        }

    }
}
