package com.practice.task.sharedprefs

import android.content.Context
import com.google.gson.Gson
import com.practice.task.models.User

class SharedPrefManager {
    companion object {
        fun registerSuccess(token: String, context: Context) {
            context.getSharedPreferences("Data", Context.MODE_PRIVATE).edit()
                .putBoolean("isRegister", true).apply()
            context.getSharedPreferences("Data", Context.MODE_PRIVATE).edit()
                .putString("token", token).apply()
        }

        fun getToken(context: Context): String {
            return context.getSharedPreferences("Data", Context.MODE_PRIVATE).getString("token", "token")!!
        }

        fun saveUser(context: Context,user:User){
            context.getSharedPreferences("Data", Context.MODE_PRIVATE).edit()
                .putString("user", Gson().toJson(user)).apply()
        }

        fun getUser(context: Context) : User{
            val user = context.getSharedPreferences("Data", Context.MODE_PRIVATE).getString("user","")
            return Gson().fromJson(user,User::class.java)
        }
    }
}