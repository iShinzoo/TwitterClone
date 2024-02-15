package com.example.twitterclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twitterclone.R
import com.example.twitterclone.data.TweetAccount

import de.hdodenhof.circleimageview.CircleImageView

class TweetAccountAdapter(
    private val listofTweets : List<TweetAccount>,
    private val context: Context,
) : RecyclerView.Adapter<TweetAccountAdapter.viewHolder>() {

    class viewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tweetimage : CircleImageView = itemView.findViewById(R.id.suggest_account_dp)
        val tweetContent : TextView = itemView.findViewById(R.id.tweetContent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_tweet_account,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return listofTweets.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentTweet = listofTweets[position]
        holder.tweetContent.text = currentTweet.TweetContent
        Glide.with(context)
            .load(currentTweet.TweetProfileImage)
            .into(holder.tweetimage)
    }
}
