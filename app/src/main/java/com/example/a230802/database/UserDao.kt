package com.example.a230802.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM user_entity")
    fun getUser() : UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInfo(entity: UserEntity)

    @Update
    fun updateInfo(entity: UserEntity)

    @Delete
    fun deleteInfo(entity: UserEntity)
    //() 안 블록에서 삭제하려는 특정 사용자를 전달
}