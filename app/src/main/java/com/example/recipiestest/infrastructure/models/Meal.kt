package com.example.recipiestest.infrastructure.models

import com.google.gson.annotations.SerializedName

data class Meal(
    @field:SerializedName("idMeal") val idMeal: String,
    @field:SerializedName("strMeal") val strMeal: String,
    @field:SerializedName("strCategory") val strCategory: String,
    @field:SerializedName("strMealThumb") val strMealThumb: String,
) {
    fun isSameAs(newItem: Meal): Boolean {
        return idMeal == newItem.idMeal
    }

    fun hasSameContentAs(newItem: Meal): Boolean {
        return strMeal == newItem.strMeal
    }
}