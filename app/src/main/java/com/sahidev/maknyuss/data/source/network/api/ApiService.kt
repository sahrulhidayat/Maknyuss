package com.sahidev.maknyuss.data.source.network.api

import com.sahidev.maknyuss.BuildConfig
import com.sahidev.maknyuss.data.source.network.response.RecipeInfoResponse
import com.sahidev.maknyuss.data.source.network.response.RecipesResponse
import com.sahidev.maknyuss.data.source.network.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/complexSearch")
    suspend fun searchRecipe(
        @Query("query") query: String,
        @Query("number") offset: Int = 0,
        @Query("number") number: Int = 5,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): SearchResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeInfo(
        @Path("id") id: Int,
        @Query("includeNutrition") includeNutrition: Boolean = false,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): RecipeInfoResponse

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int = 5,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): RecipesResponse
}