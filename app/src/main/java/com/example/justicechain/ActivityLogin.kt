package com.example.justicechain

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.justicechain.utils.LawyerPreferences
import com.google.firebase.auth.FirebaseAuth

class ActivityLogin : AppCompatActivity() {

    private lateinit var signup: TextView
    private lateinit var btnLogin: CardView

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var auth: FirebaseAuth
    lateinit var username: String
    lateinit var userPassword: String
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LawyerPreferences.init(this)

        val role = LawyerPreferences.userRole.toString()


        email = findViewById(R.id.inputEmail)
        password = findViewById(R.id.inputPassword)
        signup = findViewById(R.id.signUpPage)
        btnLogin = findViewById(R.id.btnLogin)
        auth = FirebaseAuth.getInstance()

        val spannable1 = SpannableStringBuilder("Don't have an account?Sign up")
        spannable1.setSpan(
            ForegroundColorSpan(getColor(R.color.primary)),
            22,
            29,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        signup.text = spannable1

        signup.setOnClickListener {
            showChooseRoleDialog()
        }

        btnLogin.setOnClickListener {
            username = email.text.toString().trim { it <= ' ' }
            userPassword = password.text.toString().trim { it <= ' ' }
            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else if (userPassword.isEmpty()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            } else {
                login(role)
            }
        }

    }
    private fun login(role: String) {
        auth.signInWithEmailAndPassword(username, userPassword).addOnCompleteListener() { task ->
            if (task.isSuccessful){
                if (LawyerPreferences.userRole == "lawyer"){
                    startActivity(Intent(this, DashboardActivity::class.java))
                    LawyerPreferences.isLogin = true
                    Toast.makeText(this, "Logged in successfully !", Toast.LENGTH_SHORT).show()
                    finishAffinity()
                }else if (LawyerPreferences.userRole == "clerk"){
                    startActivity(Intent(this, ClerkDashboardActivity::class.java))
                    LawyerPreferences.isLogin = true
                    Toast.makeText(this, "Logged in successfully !", Toast.LENGTH_SHORT).show()
                    finishAffinity()
                } else{
                    Toast.makeText(this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Incorrect Credentials!!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun showChooseRoleDialog() {
        val roleDialog = Dialog(this)
        roleDialog.setContentView(R.layout.choose_role_dialog)
        roleDialog.setCancelable(false)
        roleDialog.setCanceledOnTouchOutside(true)
        roleDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        roleDialog.findViewById<CardView>(R.id.btnCourtClerk).setOnClickListener {
            val i = Intent(this@ActivityLogin, RegisterActivity::class.java)
            LawyerPreferences.userRole = "clerk"
            startActivity(i)
            finishAffinity()
            // startActivity(Intent(this@ActivityLogin,RegisterActivity::class.java))
            roleDialog.dismiss()
        }

        roleDialog.findViewById<CardView>(R.id.btnJudge).setOnClickListener {
            val i = Intent(this@ActivityLogin, RegisterActivity::class.java)
            LawyerPreferences.userRole = "judge"
            startActivity(i)
            finishAffinity()
            // startActivity(Intent(this@ActivityLogin,RegisterActivity::class.java))
            roleDialog.dismiss()
        }

        roleDialog.findViewById<CardView>(R.id.btnLawyer).setOnClickListener {
            val i = Intent(this@ActivityLogin, RegisterActivity::class.java)
            LawyerPreferences.userRole = "lawyer"
            startActivity(i)
            finishAffinity()
            // startActivity(Intent(this@ActivityLogin,RegisterActivity::class.java))
            roleDialog.dismiss()
        }

        roleDialog.findViewById<CardView>(R.id.btnClient).setOnClickListener {
            val i = Intent(this@ActivityLogin, RegisterActivity::class.java)
            LawyerPreferences.userRole = "client"
            startActivity(i)
            finishAffinity()

            roleDialog.dismiss()
        }

        roleDialog.show()
    }
}