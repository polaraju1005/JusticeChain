package com.example.justicechain

import android.adservices.topics.Topic
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import com.example.justicechain.adapters.PostersAdapter
import com.example.justicechain.firebase.Constants.Companion.TOPIC
import com.example.justicechain.models.LawyerViewCasesActivity
import com.example.justicechain.models.PostersData
import com.example.justicechain.utils.LawyerPreferences
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.util.Timer

class DashboardActivity : AppCompatActivity() {

    private lateinit var userName:TextView
    private lateinit var btnViewCases:CardView

    lateinit var swipeTimer: Timer

    lateinit var postersViewPager2: ViewPager2
    lateinit var postersAdapter: PostersAdapter
    lateinit var postersDataList: ArrayList<PostersData>
    lateinit var dotsIndicator: WormDotsIndicator

    lateinit var carousel: ImageCarousel

    lateinit var firebaseStorage: FirebaseStorage
    lateinit var storageRef: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        LawyerPreferences.init(this)

        userName = findViewById(R.id.txtLawyerName)
        btnViewCases = findViewById(R.id.btnViewCases)

        postersDataList = ArrayList<PostersData>()
        swipeTimer = Timer()

        firebaseStorage = Firebase.storage
        storageRef = firebaseStorage.reference

        dotsIndicator = findViewById<WormDotsIndicator>(R.id.dots_indicator)
        carousel = findViewById(R.id.carousel)
        carousel.registerLifecycle(lifecycle)

        if (LawyerPreferences.isLogin == true) {
            userName.text = LawyerPreferences.lawyerName
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
//            val msg = getString(R.string.msg_token_fmt, token)
            Log.d("TAG", token.toString())
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        btnViewCases.setOnClickListener {
            startActivity(Intent(this@DashboardActivity,LawyerViewCasesActivity::class.java))
        }

    }
    override fun onStart() {
        super.onStart()

        // Loading the Banners Data
        loadBannersData()
    }

    private fun loadBannersData() {
        val list = mutableListOf<CarouselItem>()

        val imagesRef: StorageReference = storageRef.child("posters")
        val imageRef: StorageReference = storageRef

        imagesRef.listAll().addOnSuccessListener {

            list.clear()
            for (item in it.items) {
                imageRef.child(item.path).downloadUrl.addOnSuccessListener { it ->
                    // For Banners ViewPager
                    Log.d("TAG", it.toString())
                    list.add(CarouselItem(it.toString()))

                    carousel.setData(list)
                }
            }

        }.addOnFailureListener {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        }
    }

}