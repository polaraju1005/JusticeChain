package com.example.justicechain.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

object LawyerPreferences {
    private const val NAME = "Justice Chain"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val IS_LOGIN = Pair("is_login", false)
    private val LAWYER_NAME = Pair("lawyerName","")
    private val LAWYER_MAIL = Pair("lawyerEmail","")
    private val LAWYER_PHONE = Pair("lawyerPhone","")
    private val USER_ROLE = Pair("userRole", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun showToast(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    var isLogin: Boolean?
        get() = preferences.getBoolean(IS_LOGIN.first, IS_LOGIN.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_LOGIN.first, value!!)
        }

    var lawyerName: String?
        get() = preferences.getString(LAWYER_NAME.first, LAWYER_NAME.second)
        set(value) = preferences.edit {
            it.putString(LAWYER_NAME.first, value)
        }

    var lawyerEmail: String?
        get() = preferences.getString(LAWYER_MAIL.first, LAWYER_MAIL.second)
        set(value) = preferences.edit {
            it.putString(LAWYER_MAIL.first, value)
        }

    var lawyerPhone: String?
        get() = preferences.getString(LAWYER_PHONE.first, LAWYER_PHONE.second)
        set(value) = preferences.edit {
            it.putString(LAWYER_PHONE.first, value)
        }

    var userRole: String?
        get() = preferences.getString(USER_ROLE.first, USER_ROLE.second)
        set(value) = preferences.edit {
            it.putString(USER_ROLE.first, value)
        }
}