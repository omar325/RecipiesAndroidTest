package com.example.recipiestest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipiestest.infrastructure.RecipesApi
import com.example.recipiestest.infrastructure.models.Meal
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RecipesViewModel(
    private val api: RecipesApi
): ViewModel() {
    private val _state = MutableLiveData<RecipesVmState>(RecipesVmState.Initial)
    val state: LiveData<RecipesVmState> = _state

    private var currentRecipes: List<Meal> = ArrayList()

    private var queryRecipesJob: Job? = null

    fun queryRecipes(query: String) {
        queryRecipesJob?.cancel()
        _state.postValue(RecipesVmState.Searching(currentRecipes))
        queryRecipesJob = viewModelScope.launch {
            currentRecipes = api.queryRecipes(query).meals
            _state.postValue(RecipesVmState.SearchSuccess(currentRecipes))
        }
    }
}