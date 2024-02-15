package com.example.twitterclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twitterclone.R
import com.example.twitterclone.data.suggestedAccount
import de.hdodenhof.circleimageview.CircleImageView

class suggestAccountAdapter(
    private val listofaccounts : List<suggestedAccount>,
    private val context: Context,
    private val clicklistner: Clicklistner
) : RecyclerView.Adapter<suggestAccountAdapter.viewHolder>() {

    class viewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val image : CircleImageView = itemView.findViewById(R.id.suggest_account_dp)
        var user_email = itemView.findViewById<TextView>(R.id.suggest_account_username)
        val btnfollow : Button = itemView.findViewById(R.id.btn_follow_suggest_account)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_suggest_account,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return listofaccounts.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var currentAccount = listofaccounts[position]
        holder.user_email.text = currentAccount.userEmail
        Glide.with(context)
            .load(currentAccount.profileImage)
            .into(holder.image)

        holder.btnfollow.setOnClickListener(){
            clicklistner.onFollowClicked(currentAccount.uid)
        }
    }

    interface Clicklistner{
        fun onFollowClicked(uid : String)
    }
}
