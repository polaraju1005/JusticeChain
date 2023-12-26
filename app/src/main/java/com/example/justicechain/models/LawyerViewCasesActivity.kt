package com.example.justicechain.models

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.justicechain.R
import com.example.justicechain.adapters.CasesListRecyclerAdapter
import com.example.justicechain.utils.LawyerPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LawyerViewCasesActivity : AppCompatActivity() {
    private lateinit var casesList: ArrayList<CaseDetailsData>
    lateinit var itemList: RecyclerView
    private lateinit var adapter: CasesListRecyclerAdapter
    lateinit var auth: FirebaseAuth
    lateinit var mDbRef: DatabaseReference

    private lateinit var name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lawyer_view_cases)

        LawyerPreferences.init(this)

        auth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        casesList = ArrayList()

        adapter = CasesListRecyclerAdapter(this, casesList)

        name = LawyerPreferences.lawyerName.toString()

        itemList = findViewById(R.id.casesRecyclerView)
        itemList.layoutManager = LinearLayoutManager(this)
        itemList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        itemList.adapter = adapter
        mDbRef.child("Cases").child(name).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                casesList.clear()
                for (postSnapShot in snapshot.children) {
                    val currentUser = postSnapShot.getValue(CaseDetailsData::class.java)

                        casesList.add(currentUser!!)

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}