package com.example.twitterclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.adapters.TweetAccountAdapter
import com.example.twitterclone.data.TweetAccount
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class TweetFragment : Fragment() {

    private lateinit var rvtweet : RecyclerView
    private lateinit var tweetadapter : TweetAccountAdapter
    private var listOftweet = mutableListOf<TweetAccount>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tweet_fragment,container,false)
        return view
    }

    var new =
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvtweet = view.findViewById(R.id.rvTweet)
        tweetadapter = TweetAccountAdapter(listOftweet,requireContext())
        rvtweet.layoutManager = LinearLayoutManager(requireContext())
        rvtweet.adapter = tweetadapter

        FirebaseDatabase.getInstance().getReference().child("users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object  : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfFollowingsUids = snapshot.child("listOffollowings").value as MutableList<String>
                    listOfFollowingsUids.forEach(){
                        getTweetfromUID(it)
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
                }

            })

    }

    private fun getTweetfromUID(uid : String){
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
            .addListenerForSingleValueEvent(object  : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var tweetlist = mutableListOf<String>()
                    snapshot.child("listoftweets").value?.let {
                        tweetlist =  it as MutableList<String>
                    }
                    tweetlist.forEach {
                        if (it.isNullOrBlank()){
                            listOftweet.add(TweetAccount(it))
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}
