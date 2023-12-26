package com.example.justicechain

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateNewCaseActivity : AppCompatActivity() {
    private lateinit var etCaseType: EditText
    private lateinit var etJudgeName: EditText
    private lateinit var etLawyerName: EditText
    private lateinit var etClientName: EditText
    private lateinit var etCaseNum: EditText
    private lateinit var etFileNum: EditText
    private lateinit var etFileDate: EditText
    private lateinit var etPhone: EditText
    private lateinit var etDesc: EditText

    private lateinit var caseType: String
    private lateinit var judgeName: String
    private lateinit var lawyerName: String
    private lateinit var clientName: String
    private lateinit var caseNum: String
    private lateinit var fileNum: String
    private lateinit var fileDate: String
    private lateinit var phone: String
    private lateinit var desc: String

    private lateinit var refusers: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var btnProceed: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_case)

        etCaseType = findViewById(R.id.inputName)
        etJudgeName = findViewById(R.id.inputJudgeName)
        etLawyerName = findViewById(R.id.inputLawyerName)
        etClientName = findViewById(R.id.inputClientName)
        etCaseNum = findViewById(R.id.inputCaseNumber)
        etFileNum = findViewById(R.id.inputFilingNumber)
        etFileDate = findViewById(R.id.inputFilingDate)
        etPhone = findViewById(R.id.inputPhone)
        etDesc = findViewById(R.id.inputDesc)
        btnProceed = findViewById(R.id.btnProceed)

        auth = FirebaseAuth.getInstance()

        btnProceed.setOnClickListener {
            caseType = etCaseType.text.toString().trim { it <= ' ' }
            judgeName = etJudgeName.text.toString().trim { it <= ' ' }
            lawyerName = etLawyerName.text.toString().trim { it <= ' ' }
            clientName = etClientName.text.toString().trim { it <= ' ' }
            caseNum = etCaseNum.text.toString().trim { it <= ' ' }
            fileNum = etFileNum.text.toString().trim { it <= ' ' }
            fileDate = etFileDate.text.toString().trim { it <= ' ' }
            phone = etPhone.text.toString().trim { it <= ' ' }
            desc = etDesc.text.toString().trim { it <= ' ' }

            if (caseType.isEmpty()) {
                Toast.makeText(this, "Please enter caseType", Toast.LENGTH_SHORT).show()
            } else if (judgeName.isEmpty()) {
                Toast.makeText(this, "Please Judge name", Toast.LENGTH_SHORT).show()
            } else if (lawyerName.isEmpty()) {
                Toast.makeText(this, "Please Lawyer name", Toast.LENGTH_SHORT).show()
            } else if (clientName.isEmpty()) {
                Toast.makeText(this, "Please Client name", Toast.LENGTH_SHORT).show()
            } else if (caseNum.isEmpty()) {
                Toast.makeText(this, "Please Case Number", Toast.LENGTH_SHORT).show()
            } else if (fileNum.isEmpty()) {
                Toast.makeText(this, "Please File Number", Toast.LENGTH_SHORT).show()
            } else if (fileDate.isEmpty()) {
                Toast.makeText(this, "Please File Date", Toast.LENGTH_SHORT).show()
            } else if (phone.isEmpty()) {
                Toast.makeText(this, "Please Phone Number", Toast.LENGTH_SHORT).show()
            } else if (desc.isEmpty()) {
                Toast.makeText(this, "Please Description", Toast.LENGTH_SHORT).show()
            } else {
                regNext()
            }
        }


    }

    private fun regNext() {
        toJudge()
        toLawyer()
        toClient()
    }

    private fun toClient() {
        refusers =
            FirebaseDatabase.getInstance().reference.child("Cases").child(clientName).child(caseNum)
        val userHashMap = HashMap<String, Any>()
        userHashMap["caseType"] = caseType
        userHashMap["judgeName"] = judgeName
        userHashMap["lawyerName"] = lawyerName
        userHashMap["clientName"] = clientName
        userHashMap["caseNum"] = caseNum
        userHashMap["fileNum"] = fileNum
        userHashMap["fileDate"] = fileDate
        userHashMap["phone"] = phone
        userHashMap["desc"] = desc
        refusers.updateChildren(userHashMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this@CreateNewCaseActivity,
                    "New Case is Created",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toLawyer() {
        refusers =
            FirebaseDatabase.getInstance().reference.child("Cases").child(lawyerName).child(caseNum)
        val userHashMap = HashMap<String, Any>()
        userHashMap["caseType"] = caseType
        userHashMap["judgeName"] = judgeName
        userHashMap["lawyerName"] = lawyerName
        userHashMap["clientName"] = clientName
        userHashMap["caseNum"] = caseNum
        userHashMap["fileNum"] = fileNum
        userHashMap["fileDate"] = fileDate
        userHashMap["phone"] = phone
        userHashMap["desc"] = desc
        refusers.updateChildren(userHashMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this@CreateNewCaseActivity,
                    "New Case is Created",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toJudge() {
        refusers =
            FirebaseDatabase.getInstance().reference.child("Cases").child(judgeName).child(caseNum)
        val userHashMap = HashMap<String, Any>()
        userHashMap["caseType"] = caseType
        userHashMap["judgeName"] = judgeName
        userHashMap["lawyerName"] = lawyerName
        userHashMap["clientName"] = clientName
        userHashMap["caseNum"] = caseNum
        userHashMap["fileNum"] = fileNum
        userHashMap["fileDate"] = fileDate
        userHashMap["phone"] = phone
        userHashMap["desc"] = desc
        refusers.updateChildren(userHashMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this@CreateNewCaseActivity,
                    "New Case is Created",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}