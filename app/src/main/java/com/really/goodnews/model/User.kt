package com.really.goodnews.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class UserRemote(
    var id: Int,
    var username: String?,
    var name: String?,
    var email: String?,
    var address: Address?,
    var phone: String?,
    var website: String?,
    var company: Company?){
}

@Entity
data class User(
    @PrimaryKey(autoGenerate = false) var id: Int?,
    @ColumnInfo(name = "username") var username: String?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "company_name") var companyName: String?,
    @ColumnInfo(name = "email") var email: String?
)

data class Address(var street: String?, var suite: String?, var city: String?, var zipcode: String?, var geo: Geo?)

data class Geo(var lat: Double?, var lng: Double?)

data class Company(var name: String?, var catchPhrase: String?, var bs: String?)