package ru.otus.basicarchitecture.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.otus.basicarchitecture.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val name:String,
    val surname:String,
    val age:Int,
    val country:String,
    val city:String,
    val address:String,
    val hobby:Set<String>
)
