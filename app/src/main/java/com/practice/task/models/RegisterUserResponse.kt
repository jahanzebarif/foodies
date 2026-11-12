package com.practice.task.models

data class RegisterUserResponse(
    val status: Int,
    val message: String,
    val token: String,
    val user: User
)

data class User(
    val name: String,
    val email: String,
    val phone: String,
    val updated_at: String,
    val created_at: String,
    val id: Int,
    val photo: String
)
