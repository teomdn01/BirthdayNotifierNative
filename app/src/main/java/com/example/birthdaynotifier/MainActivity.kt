package com.example.birthdaynotifier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaynotifier.model.FriendData
import com.example.birthdaynotifier.view.FriendAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var friendList: ArrayList<FriendData>
    private lateinit var friendAdapter: FriendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting list
        friendList = ArrayList()

        //setting find id
        btnAdd = findViewById(R.id.addingBtn)
        recyclerView = findViewById(R.id.mRecycler)

        //setting adapter & add item dialog
        friendAdapter = FriendAdapter(this, friendList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = friendAdapter
        btnAdd.setOnClickListener { addFriendToList() }
    }

    private fun addFriendToList() {
        val inflater = LayoutInflater.from(this)
        val addView = inflater.inflate(R.layout.add_item, null)
        val firstName =  addView.findViewById<EditText>(R.id.firstName)
        val lastName =  addView.findViewById<EditText>(R.id.lastName)
        val nickname =  addView.findViewById<EditText>(R.id.nickname)
        val birthday =  addView.findViewById<EditText>(R.id.birthday)
        val message =  addView.findViewById<EditText>(R.id.message)

        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(addView)
        addDialog.setPositiveButton("Ok") {
            dialog, _->
            friendList.add(
                FriendData(
                firstName.text.toString(),
                lastName.text.toString(),
                nickname.text.toString(),
                birthday.text.toString(),
                message.text.toString()
            )
            )
            friendAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Successfully added new friend!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        addDialog.setNegativeButton("Cancel") {
            dialog, _->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }

        addDialog.create()
        addDialog.show()
    }
}