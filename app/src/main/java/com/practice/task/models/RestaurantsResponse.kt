package com.practice.task.models

data class RestaurantsResponse(
    val status: Int,
    val message: String,
    val categories: List<Categories>,
    val restaurants: List<Restaurants>,
    val popular_restaurants:List<Restaurants>
)

data class Categories(
    val id: Int,
    val name: String,
    val is_active: String,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?
)

data class Restaurants(
    val id: Int,
    val category_id: Int,
    val name: String,
    val address: String,
    val rating: String,
    val rating_count: String,
    val distance: String,
    val delivery_time: String,
    val type: String,
    val cover_image: String,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?
)
