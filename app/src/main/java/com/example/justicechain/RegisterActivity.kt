package com.example.justicechain

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.justicechain.utils.LawyerPreferences
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtLoginRedirect: TextView
    private lateinit var btnRegister: CardView
    private lateinit var backButton: ImageView

    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirm: TextInputEditText

    private lateinit var fullName: String
    private lateinit var userEmail: String
    private lateinit var userPhone: String
    private lateinit var userPassword: String
    private lateinit var userConfirm: String
    private lateinit var role:String

    private lateinit var refusers: DatabaseReference
    private var firebaseUserId: String = ""
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        LawyerPreferences.init(this)

        role = LawyerPreferences.userRole.toString()

        txtLoginRedirect = findViewById(R.id.txtSignInPageLink)
        btnRegister = findViewById(R.id.btnSignUp)
        backButton = findViewById(R.id.backButtonIcon)

        etName = findViewById(R.id.inputName)
        etEmail = findViewById(R.id.inputEmail)
        etPhone = findViewById(R.id.etPhn)
        etPassword = findViewById(R.id.inputPassword)
        etConfirm = findViewById(R.id.inputConfirmPassword)

        auth = FirebaseAuth.getInstance()

        val spannable1 = SpannableStringBuilder("Already have an account?Sign in")
        spannable1.setSpan(
            ForegroundColorSpan(getColor(R.color.primary)),
            24,
            31,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        txtLoginRedirect.text = spannable1

        backButton.setOnClickListener {
            onSupportNavigateUp()
        }



        btnRegister.setOnClickListener {
            fullName = etName.text.toString().trim { it <= ' ' }
            userEmail = etEmail.text.toString().trim { it <= ' ' }
            userPhone = etPhone.text.toString().trim { it <= ' ' }
            userPassword = etPassword.text.toString().trim { it <= ' ' }
            userConfirm = etConfirm.text.toString().trim { it <= ' ' }

            if (fullName.isEmpty()) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show()
            }else if (userEmail.isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity, "please enter Email", Toast.LENGTH_SHORT
                ).show()
            }else if (userPhone.isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity, "Enter mobile number", Toast.LENGTH_SHORT
                ).show()
            }else if (userPhone.length < 10) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Mobile number must contain 10 digits",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (userPassword.isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please enter a valid password",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (userPassword.length < 8) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Password length must be 8 characters",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (userConfirm.isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please Enter confirm password",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (userPassword != userConfirm) {
                Toast.makeText(
                    this@RegisterActivity,
                    "password and confirm password should same",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //regNext()
                sendOTP()
            }
        }

        txtLoginRedirect.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, ActivityLogin::class.java))
            finish()
        }
    }

    private fun sendOTP() {
        if (userPhone.isNotEmpty()) {
            if (userPhone.length == 10) {
                userPhone = "+91$userPhone"
               // showProgressBar()
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(userPhone)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(callbacks)
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please enter correct number",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(this@RegisterActivity, "Please enter number", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            }
           // hideProgressBar()
            Toast.makeText(
                this@RegisterActivity,
                "Verification failed please try again after some time",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            val intent = Intent(this@RegisterActivity, OTPActivity::class.java)
            intent.putExtra("OTP", verificationId)
            intent.putExtra("resendToken", token)
            intent.putExtra("phoneNumber", userPhone)
            Toast.makeText(this@RegisterActivity, "Redirecting....", Toast.LENGTH_SHORT)
                .show()

            //student registration details
            intent.putExtra("fullName", fullName)
            intent.putExtra("mail", userEmail)
            intent.putExtra("userPhone", userPhone)
            intent.putExtra("password", userPassword)

            startActivity(intent)
           // hideProgressBar()
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Mobile Number Verified Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                sendToMain()
            } else {
                //mobile number is not verified,displaying a message and updating the UI
                Log.d("TAG", "signInWithPhoneAuthCredential: ${it.exception.toString()}")
                if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "The Verification code entered was invalid",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
           // hideProgressBar()
        }
    }

    private fun sendToMain() {
        regNext()
    }
    private fun regNext() {
        //showProgressBar()
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this@RegisterActivity){ task ->
                if (task.isSuccessful) {
                    firebaseUserId = auth.currentUser!!.uid
                    refusers =
                        FirebaseDatabase.getInstance().reference.child("Users").child(role)
                            .child(firebaseUserId)
                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserId
                    userHashMap["email"] = etEmail.text.toString().trim { it <= ' ' }
                    userHashMap["fullName"] = etName.text.toString().trim { it <= ' ' }
                    userHashMap["phone"] = etPhone.text.toString().trim { it <= ' ' }

                    refusers.updateChildren(userHashMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this, "User registration successful!", Toast.LENGTH_SHORT
                            ).show()

                            //sharedPreferences
                            LawyerPreferences.isLogin = true
                            LawyerPreferences.lawyerName = fullName
                            LawyerPreferences.lawyerEmail = userEmail
                            LawyerPreferences.lawyerPhone = userPhone

                            val i = Intent(this@RegisterActivity, ActivityLogin::class.java)
                            LawyerPreferences.userRole = role
                            startActivity(i)
                            finishAffinity()
                        }
                    }
                } else {
                   // hideProgressBar()
                    Toast.makeText(
                        this@RegisterActivity, "Something went wrong", Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}