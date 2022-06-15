package com.example.hw7aberishvili

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.hw7aberishvili.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var userData: TextView
    private lateinit var pResponse: TextView
    private lateinit var createName: EditText
    private lateinit var createJob: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val reqResApi: ReqAPI = retrofit.create(ReqAPI::class.java)

        userData = findViewById(R.id.getUserData)
        val getBtn: Button = findViewById<View>(R.id.getBtn) as Button
        val closeGetBtn: Button = findViewById<View>(R.id.closeGetBtn) as Button

        pResponse = findViewById(R.id.postCreateUserResponseTv)
        createName = findViewById((R.id.nameCrtEt))
        createJob = findViewById(R.id.createJob)
        val postBtn: Button = findViewById<View>(R.id.postBtn) as Button



        closeGetBtn.setOnClickListener {
            userData.text = ""
            getBtn.visibility = View.VISIBLE
            closeGetBtn.visibility = View.INVISIBLE
        }

        getBtn.setOnClickListener {
            getBtn.visibility = View.INVISIBLE
            closeGetBtn.visibility = View.VISIBLE


            val call: Call<PageModel> = reqResApi.getUsers()
            call.enqueue(object : Callback<PageModel> {
                override fun onFailure(call: Call<PageModel>, t: Throwable) {
                    userData.text = t.message
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<PageModel>, response: Response<PageModel>) {
                    if (!response.isSuccessful) {
                        userData.text = "Code: " + response.code()
                        return
                    }
                    println(response.body())
                    val users: PageModel? = response.body()

                    var i = 0
                    if (users != null) {
                        for (user: UserModel? in users.data) {
                            i++
                            var content: String = ""
                            content += "user: " + user?.id + "\n"
                            content += "Email: " + user?.email + "\n"
                            content += "FName: " + user?.firstName + "\n"
                            content += "lname: " + user?.lastName + "\n"

                            userData.append(content)
                        }

                    }
                }

            })
        }
        postBtn.setOnClickListener {
            val userToBeAdded = CreateUserModel()
            userToBeAdded.name = createName.text.toString()
            userToBeAdded.job = createJob.text.toString()

            val call: Call<CreatedUserModel> = reqResApi.createUser(userToBeAdded)
            call.enqueue(object : Callback<CreatedUserModel> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<CreatedUserModel>, response: Response<CreatedUserModel>) {
                    if (!response.isSuccessful) {
                        pResponse.text = "Code: " + response.code()
                        return
                    }
                    val createdUser: CreatedUserModel? = response.body()
                    pResponse.append(createdUser?.name + "\n")
                    pResponse.append(createdUser?.job + "\n")
                    pResponse.append(createdUser?.createdAt + "\n")

                }

                override fun onFailure(call: Call<CreatedUserModel>, t: Throwable) {
                    userData.text = t.message
                }

            })
        }


    }
}