package com.example.githubrepos.data

//import android.arch.persistence.room.ColumnInfo
//import android.arch.persistence.room.Entity
//
//@Entity(tableName = "repos")
//data class Repository @JvmOverloads constructor(
//    @ColumnInfo(name = "title") var title: String = "",
//    @ColumnInfo(name = "readmeURL") var readmeURL: String = ""
//) {
//
//    val titleForList: String
//        get() = if (title.isNotEmpty()) title else readmeURL
//
//}

data class Repository(var title:String, var url: String?, var readmeURL: String)