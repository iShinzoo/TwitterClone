package com.example.twitterclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.adapters.suggestAccountAdapter
import com.example.twitterclone.data.User
import com.example.twitterclone.data.suggestedAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class SuggestedAccoutFragment : Fragment() , suggestAccountAdapter.Clicklistner {

    private lateinit var rv : RecyclerView
    private lateinit var suggestedaccountAdapter: suggestAccountAdapter
    private val listofaccount = mutableListOf<suggestedAccount>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.suggested_account_fragment,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = view.findViewById(R.id.rvSuggestAccount)
        suggestedaccountAdapter = suggestAccountAdapter(listofaccount,requireContext(),this@SuggestedAccoutFragment)
        rv.adapter = suggestedaccountAdapter
        rv.layoutManager = GridLayoutManager(requireContext(),2)

        FirebaseDatabase.getInstance().getReference().child("users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var listOfFollwings = snapshot.child("listOffollowings").value as MutableList<String>


                    FirebaseDatabase.getInstance().getReference().child("users")
                        .addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for ( datasnapshot in snapshot.children){
                                    var user = datasnapshot.getValue(User::class.java)

                                    if (user?.uid.toString() != FirebaseAuth.getInstance().uid.toString() && !listOfFollwings.contains(user?.uid.toString())){
                                        var suggestedaccount = suggestedAccount(user?.userProfileImage.toString(),
                                            user?.userEmail.toString(),
                                            user?.uid.toString())
                                        listofaccount.add(suggestedaccount)

                                        suggestedaccountAdapter.notifyDataSetChanged()
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                //TODO("Not yet implemented")
                            }

                        })
                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
                }

            })
    }

    private fun followUser(uid: String){
        FirebaseDatabase.getInstance().getReference().child("users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfFollowings = snapshot.child("listOffollowings").value as MutableList<String>
                    listOfFollowings.add(uid)

                    FirebaseDatabase.getInstance().getReference().child("users").child(Firebase.auth.uid.toString())
                        .child("listOffollowings").setValue(listOfFollowings)

                    Toast.makeText(requireContext(), "Followed Successfully", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
                }

            })
    }

    override fun onFollowClicked(uid : String) {
        followUser(uid)
    }
}








