package com.odc.dataapp.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/*
    "postId": 1,
    "id": 1,
    "name": "id labore ex et quam laborum",
    "email": "Eliseo@gardner.biz",
    "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"


 */
@Entity(tableName = "tb_comments")
data class Comments(
    @PrimaryKey(autoGenerate = true) var idDB : Int = 0,
    var postId : Int? = null,
    var id : Int? = null,
    var name : String? = null,
    var email : String? = null,
    var body : String? = null) {

}
@Dao
interface CommentsDAO{
    @Insert
    fun insert(data: Comments): Long

    @Query("SELECT * FROM tb_comments order by idDB desc")
    fun select(): Flow<List<Comments>>

    @Update
    fun update(data: Comments)

    @Delete
    fun delete(data: Comments)
}