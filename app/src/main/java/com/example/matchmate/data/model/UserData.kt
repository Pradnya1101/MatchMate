package com.example.matchmate.data.model

data class UserData(
    val name: Name,
    val email: String,
    val picture: Picture,
    val location: Location
)

data class Name(val first: String, val last: String)
data class Picture(val large: String)
data class Location(val city: String, val country: String)
