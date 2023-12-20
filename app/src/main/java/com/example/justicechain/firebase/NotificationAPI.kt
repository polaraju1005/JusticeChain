package com.example.justicechain.firebase

import com.example.justicechain.firebase.Constants.Companion.SERVER_KEY
import com.example.justicechain.firebase.Constants.Companion.CONTENT_TYPE
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Response

interface NotificationAPI {
    @Headers("Authorization: key =$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}