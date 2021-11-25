package com.example.androidpasswordmanager

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.UserInfo
import kotlinx.coroutines.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class ShowUserPasswords:AppCompatActivity() {
    private var viewTexts : MutableList<TextView> = mutableListOf()
    private var linearList : MutableList<LinearLayout> = mutableListOf()
    //private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        //get username from login page
        val intent = intent.getStringExtra("username")
        val id = intent.toString()

        try {
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            //Amplify.configure(applicationContext)
            Log.i("Auth", "Initialized Amplify")
        } catch (failure: AmplifyException) {
            Log.e("Auth", "Could not initialize Amplify", failure)
        }

        val userList = getFromCloud("$id")

        verticalLayout {
            linearList.add(this)
            //button to add new data
            linearLayout {
                gravity = Gravity.CENTER
                button("Add") {
                    textSize = 25.0F
                    setOnClickListener {
                        linearList.get(0).setVisibility(View.INVISIBLE)
                        createData("$id")
                    }
                }
                button("Log Out") {
                    textSize = 25.0F
                    setOnClickListener {
                        Amplify.Auth.signOut(
                            {Log.i("Auth","Signed out successfully")},
                            {Log.e("Auth", "Sign out failed", it)}
                        )
                        val intent = Intent(this@ShowUserPasswords,MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            textView("Content, Username, Password"){
                gravity=Gravity.CENTER
                textSize = 20.0F
                Typeface.BOLD
            }
            for(items in userList){
                linearLayout{
                    gravity = Gravity.CENTER
                    textView("${items.elementAt(0)}, ${items.elementAt(1)}    ") {
                        gravity = Gravity.CENTER
                        textSize = 20.0F
                    }
                    button("******"){
                        gravity = Gravity.CENTER
                        textSize = 20.0F
                        backgroundColor = ContextCompat.getColor(context,R.color.white)
                        setOnClickListener{
                            if(this.text == "******") {
                                var tempEle = items.elementAt(2)
                                text = "$tempEle"
                            }
                            else{
                                text = "******"
                            }
                        }
                    }
                }
            }
        }
        //create gridview (recyclerview in grid form)
        super.onCreate(savedInstanceState)
    }

    //get all user data from the cloud and output to recyclerview
    fun getFromCloud(myID: String): MutableList<MutableList<String>> {
        var userContent : MutableList<MutableList<String>> = arrayListOf()
        runBlocking {
            val job : Job = launch(context = Dispatchers.Default){
                delay(3000)
            }
            Amplify.DataStore.query(UserInfo::class.java,
                Where.matches(UserInfo.USER.eq("$myID")),
                {
                        posts ->
                    while(posts.hasNext()){
                        val post = posts.next()
                        userContent.add(mutableListOf(post.content, post.username, post.password))
                    }
                    //outputList(userContent)
                },
                {Log.e("Auth","Error",it)}
            )
            job.join()
        }
        return userContent
    }

    fun createData(myID : String){
        verticalLayout{
            linearList.add(this)
            textView("Add New Account"){
                gravity = Gravity.CENTER
            }
            linearLayout{
                textView("content"){
                    gravity = Gravity.CENTER
                }
                editText{
                    setHint("Set Content")
                    viewTexts.add(this)
                }
            }
            linearLayout{
                textView("Username"){

                }
                editText{
                    setHint("Set Username")
                    viewTexts.add(this)
                }
            }
            linearLayout{
                textView("Password"){

                }
                editText{
                    setHint("Set Password")
                    viewTexts.add(this)
                }
            }
            linearLayout{
                button("Confirm"){
                    setOnClickListener{
                        val username = viewTexts.get(1).text.toString()
                        val password = viewTexts.get(2).text.toString()
                        val content = viewTexts.get(0).text.toString()
                        //add to database
                        val addContent = UserInfo.builder()
                            .user(myID)
                            .content(content)
                            .username(username)
                            .password(password)
                            .build()
                        Amplify.DataStore.save(addContent,
                            {Log.i("Amplify","Saved Post $username , $password , $content")},
                            {Log.e("Amplify", "save failed" , it)})
                        val intent = Intent(this@ShowUserPasswords,ShowUserPasswords::class.java)
                        intent.putExtra("username",myID)
                        startActivity(intent)
                    }
                }
                button("Cancel"){
                    setOnClickListener {
                        val intent = Intent(this@ShowUserPasswords,ShowUserPasswords::class.java)
                        intent.putExtra("username",myID)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}