package com.example.androidpasswordmanager

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {
    private var layouts : MutableList<LinearLayout> = mutableListOf()
    private var editTexts : MutableList<EditText> = mutableListOf()
    private var signupeditTexts : MutableList<EditText> = mutableListOf()
    private var viewTexts : MutableList<TextView> = mutableListOf()
    private lateinit var user : String
    private lateinit var pass : String
    private lateinit var verify : String
    private lateinit var SignUpEmail : String
    private lateinit var SignUpUser : String
    private lateinit var SignUpPass : String
    private lateinit var reEnterPass : String
    //private lateinit var SignUpPhone : String

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            Log.i("Auth", "Initialized Amplify")
        } catch (failure: AmplifyException) {
            Log.e("Auth", "Could not initialize Amplify", failure)
        }

        Amplify.Auth.fetchAuthSession(
            { Log.i("AmplifyQuickstart", "Auth session = $it") },
            { Log.e("AmplifyQuickstart", "Failed to fetch auth session", it) }
        )

        //show the login/create username password page
        verticalLayout{
            layouts.add(this)
            textView("Password Manager"){
                textSize = 35.0F
                Typeface.BOLD
                //setTextColor(Color.parseColor("#D5DDEF"))
                //setBackgroundColor(Color.parseColor("#4C394F"))
                gravity = Gravity.CENTER
                setPadding(0, 20, 0, 20)
            }
            linearLayout{
                textView("Username") {
                    gravity = Gravity.CENTER
                }
                editText{
                    setHint("Enter Username")
                    editTexts.add(this)
                }
            }
            linearLayout{
                textView("Password"){
                    gravity = Gravity.CENTER
                }
                editText{
                    setHint("Enter Password")
                    inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                    editTexts.add(this)
                }
            }
            /*
            linearLayout {
                textView("SMS Confirm") {
                    gravity = Gravity.CENTER
                }
                editText {
                    setHint("Enter Confirmation")
                    inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                    editTexts.add(this)
                }
            }*/
            linearLayout {
                button("Login") {
                    textSize = 20.0F
                    //setTextColor(Color.parseColor("#2E1A1E"))
                    //setBackgroundColor(Color.parseColor("#917898"))
                    setOnClickListener {
                        user = editTexts.get(0).text.toString()
                        pass = editTexts.get(1).text.toString()
                        Amplify.Auth.signIn("$user", "$pass",
                            { result ->
                                if (result.isSignInComplete) {
                                    Log.i("Test", "$user")
                                    Log.i("AuthQuickStart", "Sign in Success")
                                    val intent = Intent(this@MainActivity,ShowUserPasswords::class.java)
                                    intent.putExtra("username","$user")
                                    startActivity(intent)
                                } else {
                                    Log.i("AuthQuickStart", "Sign in Fail")
                                }
                            },
                            { Log.e("AuthQuickStart", "Fucking error", it) }
                        )
                    }
                }
                button("Sign Up"){
                    textSize = 20.0F

                    setOnClickListener{
                        layouts.get(0).setVisibility(View.INVISIBLE)
                        createUser()
                    }
                }
            }
            textView("Username/Password Incorrect"){
                viewTexts.add(this)
                setVisibility(View.INVISIBLE)
            }
        }
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
    }

    fun createUser(){
        verticalLayout{
            layouts.add(this)
            textView("Create Account"){
                textSize = 35.0F
                Typeface.BOLD
                //setTextColor(Color.parseColor("#D5DDEF"))
                //setBackgroundColor(Color.parseColor("#4C394F"))
                gravity = Gravity.CENTER
                setPadding(0, 20, 0, 20)
            }
            linearLayout{
                textView("Email") {
                    gravity = Gravity.CENTER
                }
                editText{
                    setHint("Enter Email")
                    signupeditTexts.add(this)
                }
            }
            linearLayout{
                textView("Username") {
                    gravity = Gravity.CENTER
                }
                editText{
                    setHint("Enter Username")
                    signupeditTexts.add(this)
                }
            }
            linearLayout{
                textView("Password"){
                    gravity = Gravity.CENTER
                }
                editText{
                    setHint("Enter Password")
                    inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                    signupeditTexts.add(this)
                }
            }
            linearLayout{
                textView("Re-Enter Password"){
                    gravity = Gravity.CENTER
                }
                editText{
                    setHint("Re-Enter Password")
                    inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                    signupeditTexts.add(this)

                }
            }
            /*linearLayout{
                textView("Phone Number"){
                    gravity = Gravity.CENTER
                }
                editText{
                    setHint("Enter Phone Number")
                    signupeditTexts.add(this)
                }
            }*/
            linearLayout {
                button("Sign Up") {
                    textSize = 20.0F
                    //setTextColor(Color.parseColor("#2E1A1E"))
                    //setBackgroundColor(Color.parseColor("#917898"))
                    setOnClickListener {
                        SignUpEmail = signupeditTexts.get(0).text.toString()
                        SignUpUser = signupeditTexts.get(1).text.toString()
                        SignUpPass = signupeditTexts.get(2).text.toString()
                        reEnterPass = signupeditTexts.get(3).text.toString()
                        //SignUpPhone = "+1" + signupeditTexts.get(3).text.toString()
                        /*val attrs = mapOf(
                            AuthUserAttributeKey.email() to "$SignUpEmail",
                            AuthUserAttributeKey.phoneNumber() to "$SignUpPhone"
                        )
                        val options = AuthSignUpOptions.builder()
                            .userAttributes(attrs.map{ AuthUserAttribute(it.key,it.value) })
                            .build()
                        */
                        if(SignUpPass == reEnterPass) {
                            val options = AuthSignUpOptions.builder()
                                    .userAttribute(AuthUserAttributeKey.email(), "$SignUpEmail")
                                    //.userAttribute(AuthUserAttributeKey.phoneNumber(), "$SignUpPhone")
                                    .build()
                            Amplify.Auth.signUp("$SignUpUser", "$SignUpPass", options,
                                    { Log.i("Auth", "Signup Success: $it") },
                                    { Log.e("Auth", "Signup Failed", it) })
                            //if success, go back
                            //startActivity<MainActivity>("id" to 2)
                            //if fail, say why
                        }
                        else{
                            Log.i("Test","Passwords not the same")
                        }
                    }
                }
                button("Cancel"){
                    textSize = 20.0F

                    setOnClickListener{
                        startActivity<MainActivity>("id" to 1)
                    }
                }
            }

            linearLayout{
                editText{
                    setHint("                              ")
                    signupeditTexts.add(this)
                }
                button("Verify") {
                    textSize = 15.0F
                    setOnClickListener {
                        val verifyUser = signupeditTexts.get(4).text.toString()
                        Amplify.Auth.confirmSignUp(
                            "$SignUpUser", "$verifyUser",
                            { result ->
                                if (result.isSignUpComplete) {
                                    Log.i("Auth", "Confirm signup")
                                    startActivity<MainActivity>("id" to 1)

                                } else {
                                    Log.i("Auth", "Confirm signup not complete")
                                }
                            },
                            { Log.e("Auth", "Failed to confirm signup", it) }
                        )
                    }
                }
            }
        }
    }
}