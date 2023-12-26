package com.example.justicechain

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.justicechain.firebase.Constants.Companion.TOPIC
import com.example.justicechain.firebase.NotificationData
import com.example.justicechain.firebase.PushNotification
import com.example.justicechain.firebase.RetrofitInstance
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class notificationPushActivity : AppCompatActivity() {

    lateinit var backButton: ImageView

    val TAG = "PushNotification"

    lateinit var editTitle: TextInputEditText
    lateinit var editMessage: TextInputEditText
    lateinit var btnSend: CardView

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_push)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        backButton = findViewById(R.id.backButtonIcon)
        btnSend = findViewById(R.id.btnSend)
        editMessage = findViewById(R.id.editMessage)
        editTitle = findViewById(R.id.editTitle)

        btnSend.setOnClickListener {
            val title = editTitle.text.toString().trim { it <= ' ' }
            val message = editMessage.text.toString().trim { it <= ' ' }

            if (title.isEmpty()) {
                Toast.makeText(this, "Enter the title", Toast.LENGTH_SHORT).show()
            } else if (message.isEmpty()) {
                Toast.makeText(this, "Type a message to send", Toast.LENGTH_SHORT).show()
            } else {
                PushNotification(
                    NotificationData(title, message),
                    TOPIC
                ).also {
                    sendNotification(it)
                }
//                goNotification(title, message)
//                sendDataToNotificationDatabase(
//                    message, title
//                )

            }
        }
    }

    private fun goNotification(title: String, message: String) {
        if (title.isNotEmpty() && message.isNotEmpty()) {
            PushNotification(
                NotificationData(title, message),
                TOPIC
            ).also {
                sendNotification(it)
             //   Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
//                    Log.d(TAG, "Response: ${Gson().toJson(response)}")
                    Log.d(TAG, "Message Sent")
                } else {
                    Log.e(TAG, response.errorBody().toString())
                }

            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }

}