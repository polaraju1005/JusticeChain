package com.example.justicechain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import com.example.justicechain.adapters.PostersAdapter
import com.example.justicechain.models.PostersData
import com.example.justicechain.utils.LawyerPreferences
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.util.Timer

class ClerkDashboardActivity : AppCompatActivity() {

    private lateinit var clerkText:TextView
    private lateinit var btnCreateNewCase:CardView
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
        setContentView(R.layout.activity_clerk_dashboard)

        LawyerPreferences.init(this)

        btnCreateNewCase = findViewById(R.id.btnCreate)
        clerkText = findViewById(R.id.txtClerkName)

        postersDataList = ArrayList<PostersData>()
        swipeTimer = Timer()

        firebaseStorage = Firebase.storage
        storageRef = firebaseStorage.reference

        dotsIndicator = findViewById<WormDotsIndicator>(R.id.dots_indicator)
        carousel = findViewById(R.id.carousel)
        carousel.registerLifecycle(lifecycle)

        if (LawyerPreferences.isLogin == true) {
            clerkText.text = LawyerPreferences.lawyerName
        }

        btnCreateNewCase.setOnClickListener {
            startActivity(Intent(this@ClerkDashboardActivity,CreateNewCaseActivity::class.java))
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