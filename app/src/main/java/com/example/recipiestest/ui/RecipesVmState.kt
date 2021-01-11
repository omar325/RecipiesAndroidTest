package com.example.recipiestest.ui

import com.example.recipiestest.infrastructure.models.Meal

sealed class RecipesVmState {
    object Initial: RecipesVmState()
    data class Searching(val recipes: List<Meal>): RecipesVmState()
    data class SearchSuccess(val recipes: List<Meal>): RecipesVmState()
    data class Error(val message: String): RecipesVmState()
}