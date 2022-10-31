package com.example.birthdaynotifier.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaynotifier.R
import com.example.birthdaynotifier.model.FriendData

class FriendAdapter(val c: Context, val userList: ArrayList<FriendData>) : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {
    inner class FriendViewHolder(val v : View) : RecyclerView.ViewHolder(v) {
        var firstName: TextView
        var lastName: TextView
        var nickname: TextView
        var birthday: TextView
        var message: TextView
        var mMenus: ImageView

        init {
            firstName = v.findViewById<TextView>(R.id.mFirstName)
            lastName = v.findViewById<TextView>(R.id.mLastName)
            nickname = v.findViewById<TextView>(R.id.mNickname)
            birthday = v.findViewById<TextView>(R.id.mBirthday)
            message = v.findViewById<TextView>(R.id.mMessage)
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener { popupMenus(it) }
        }

        private fun popupMenus(v:View) {
            var position = userList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editFriend -> {
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)
                        val firstName = v.findViewById<EditText>(R.id.firstName)
                        val lastName = v.findViewById<EditText>(R.id.lastName)
                        val nickname = v.findViewById<EditText>(R.id.nickname)
                        val birthday = v.findViewById<EditText>(R.id.birthday)
                        val message = v.findViewById<EditText>(R.id.message)

                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                    dialog,_->
                                position.firstName = firstName.text.toString()
                                position.lastName = lastName.text.toString()
                                position.nickname = nickname.text.toString()
                                position.birthday = birthday.text.toString()
                                position.message = message.text.toString()

                                notifyDataSetChanged()
                                Toast.makeText(c,"User Information is Edited",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.deleteFriend->{
                        /**set delete*/
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Deleted this Information",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
        return FriendViewHolder(v)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val newList = userList[position]
        holder.firstName.text = newList.firstName
        holder.lastName.text = newList.lastName
        holder.nickname.text = newList.nickname
        holder.birthday.text = newList.birthday
        holder.message.text = newList.message
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}