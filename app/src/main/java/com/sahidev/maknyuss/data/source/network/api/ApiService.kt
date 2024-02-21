package com.sahidev.maknyuss.data.source.network.api

import com.sahidev.maknyuss.BuildConfig
import com.sahidev.maknyuss.Secrets
import com.sahidev.maknyuss.data.source.network.response.PriceBreakDownResponse
import com.sahidev.maknyuss.data.source.network.response.RecipeInfoResponse
import com.sahidev.maknyuss.data.source.network.response.RecipesResponse
import com.sahidev.maknyuss.data.source.network.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val API_KEY = Secrets().getApiKey(BuildConfig.APPLICATION_ID)
interface ApiService {
    @GET("recipes/complexSearch")
    suspend fun searchRecipe(
        @Query("query") query: String,
        @Query("offset") offset: Int = 0,
        @Query("number") number: Int = 10,
        @Query("addRecipeInformation") addRecipeInfo: Boolean = true,
        @Query("apiKey") apiKey: String = API_KEY,
    ): SearchResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeInfo(
        @Path("id") id: Int,
        @Query("includeNutrition") includeNutrition: Boolean = false,
        @Query("apiKey") apiKey: String = API_KEY,
    ): RecipeInfoResponse

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int = 10,
        @Query("apiKey") apiKey: String = API_KEY
    ): RecipesResponse

    @GET("recipes/{id}/priceBreakdownWidget.json")
    suspend fun getPriceBreakDownById(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): PriceBreakDownResponse
}