package com.example.twitterclone.data

data class User (
    var userEmail : String = "",
    var userName : String = "",
    var userProfileImage : String = "",
    val listOffollowings : List<String> = listOf(),
    val listoftweets : List<String> = listOf(),
    val uid : String = ""
)