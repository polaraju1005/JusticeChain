package com.example.justicechain.models

class UserData {
    private var uid: String = ""
    private var fullName: String = ""
    private var email: String = ""
    private var phone: String = ""

    constructor()

    constructor(
        uid: String,
        fullName:String,
        email:String,
        phone:String
    ){
        this.uid = uid
        this.fullName = fullName
        this.email = email
        this.phone = phone
    }

    fun getUID(): String? {
        return uid
    }

    fun setUID(uid: String) {
        this.uid = uid
    }

    fun getFullName(): String? {
        return fullName
    }

    fun setFullName(fullName: String) {
        this.fullName = fullName
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }

}