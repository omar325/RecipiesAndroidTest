package com.example.recipiestest.infrastructure.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @field:SerializedName("meals") val meals: List<Meal>
)