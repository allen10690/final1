package com.example.final1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final1.Extensions.toast
import com.example.final1.FirebaseUtils.firebaseAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chatroom.*
import kotlinx.android.synthetic.main.activity_group.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class ChatroomActivity : AppCompatActivity() {
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        returnmenu_btn_chatroom.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }


        //val adapter = GroupieAdapter()
        //recyclerview_chatroom.adapter = adapter
    }

    private fun notEmpty(): Boolean = et_message.text.toString().isNotEmpty()

    private fun sendmsg() {
        database = FirebaseDatabase.getInstance("https://project-test-8dbf1-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users")
        val userid = FirebaseAuth.getInstance().uid
        val msg = et_message.text.toString()
        val username = database.child("Users").child(userid!!).child("userName").get()
        var time = Date().getTime()
        val chatroomid = "future do"


        if(notEmpty()) {

        }

    }
}